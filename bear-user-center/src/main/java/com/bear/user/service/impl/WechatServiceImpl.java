package com.bear.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bear.common.constants.CredentialType;
import com.bear.common.entity.user.*;
import com.bear.user.config.WechatConfig;
import com.bear.user.mapper.UserCredentialsMapper;
import com.bear.user.mapper.WechatMapper;
import com.bear.user.service.AppUserService;
import com.bear.user.service.WechatService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author panda.
 * @since 2018-11-26 1:15.
 */
@Slf4j
@Service
public class WechatServiceImpl implements WechatService {

    @Autowired
    private WechatConfig wechatConfig;
    @Autowired
    private WechatMapper wechatMapper;
    @Autowired
    private UserCredentialsMapper userCredentialsMapper;


    private static final String WECHAT_AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=%s#wechat_redirect";
    private static final String STATE_WECHAT = "state_wechat";

    private WechatInfo getWechatInfo(String app) {
        WechatInfo wechatInfo = wechatConfig.getInfos().get(app);
        if (wechatInfo == null) {
            throw new IllegalArgumentException("未找到，" + app);
        }

        return wechatInfo;
    }

    @Override
    public String getWechatAuthorizeUrl(String app, HttpServletRequest request, String toUrl)
            throws UnsupportedEncodingException {
        log.info("引导到授权页:{},{}", app, toUrl);
        WechatInfo wechatInfo = getWechatInfo(app);

        // 网关域名(外网)加路由到用户系统的规则 https://xxx.xxx.xxx/api-u
        String domain = wechatConfig.getDomain();
        StringBuilder redirectUri = new StringBuilder(domain + "/wechat/" + app + "/back");
        if (StringUtils.isNoneBlank(toUrl)) {
            toUrl = URLEncoder.encode(toUrl, "utf-8");
            redirectUri.append("?toUrl=").append(toUrl);
        }
        String uri = URLEncoder.encode(redirectUri.toString(), "utf-8");

        // 生成一个随机串，微信再跳回来的时候，会原封不动给我们带过来，到时候做一下校验
        String state = UUID.randomUUID().toString();
        request.getSession().setAttribute(STATE_WECHAT, state);

        return String.format(WECHAT_AUTHORIZE_URL, wechatInfo.getAppid(), uri, state);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public WechatUser getWechatUserInfo(String app, HttpServletRequest request, String code, String state) {
        log.info("code:{}, state:{}", code, state);
        checkStateLegal(state, request);

        WechatAccess wechatAccess = getWechatAccess(app, code);
        WechatUser wechatUserInfo = wechatMapper.findByOpenid(wechatAccess.getOpenid());

        if (wechatUserInfo == null) {
            wechatUserInfo = saveWechatUserInfo(app, wechatAccess);
        } else {
            updateWechatUserInfo(wechatAccess, wechatUserInfo);
        }

        return wechatUserInfo;
    }


    private WechatUser saveWechatUserInfo(String app, WechatAccess wechatAccess) {
        WechatUser wechatUserInfo = getWechatUserInfo(wechatAccess);

        // 多公众号支持
        String unionid = wechatUserInfo.getUnionid();
        if (StringUtils.isNoneBlank(unionid)) {
            // 根据unionid查询，看是否有同源公众号已绑定用户
            Set<WechatUser> set = wechatMapper.findByUniond(unionid);
            if (!CollectionUtils.isEmpty(set)) {
                WechatUser userInfo = set.parallelStream().filter(w -> w.getUserId() != null).findFirst().orElse(null);
                if (userInfo != null) {
                    wechatUserInfo.setUserId(userInfo.getUserId());
                    log.info("具有相同的unionid,视为同一用户：{}", userInfo);

                    // 将新公众号的openid也存入登陆凭证表
                    userCredentialsMapper.save(new UserCredential(wechatUserInfo.getOpenid(), CredentialType.WECHAT_OPENID.name(), userInfo.getUserId()));
                }
            }
        }

        wechatUserInfo.setApp(app);
        wechatUserInfo.setCreateTime(new Date());
        wechatUserInfo.setUpdateTime(wechatUserInfo.getCreateTime());

        wechatMapper.save(wechatUserInfo);
        log.info("保存微信个人用户信息:{}", wechatUserInfo);

        return wechatUserInfo;
    }

    @Autowired
    private TaskExecutor taskExecutor;

    /**
     * 异步更新微信个人用户信息
     *
     * @param wechatAccess
     * @param wechatUserInfo
     */
    private void updateWechatUserInfo(WechatAccess wechatAccess, WechatUser wechatUserInfo) {
        taskExecutor.execute(() -> {
            WechatUser userInfo = getWechatUserInfo(wechatAccess);
            BeanUtils.copyProperties(userInfo, wechatUserInfo, new String[]{"id", "userId"});
            wechatUserInfo.setUpdateTime(new Date());
            wechatMapper.update(wechatUserInfo);

            log.info("更新微信个人用户信息:{}", wechatUserInfo);
        });
    }

    /**
     * 校验state是否合法
     *
     * @param state
     * @param request
     */
    private void checkStateLegal(String state, HttpServletRequest request) {
        HttpSession httpSession = request.getSession();
        String sessionState = (String) httpSession.getAttribute(STATE_WECHAT);
        if (sessionState == null) {
            throw new IllegalArgumentException("缺失session state");
        }

        if (!state.equals(sessionState)) {
            throw new IllegalArgumentException("非法state");
        }

        // 校验通过，将session中的state移除
        httpSession.removeAttribute(STATE_WECHAT);
    }

    @Autowired
    private RestTemplate restTemplate;

    private static final String WECHAT_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    private WechatAccess getWechatAccess(String app, String code) {
        WechatInfo wechatInfo = getWechatInfo(app);

        String accessTokenUrl = String.format(WECHAT_ACCESS_TOKEN_URL, wechatInfo.getAppid(), wechatInfo.getSecret(),
                code);

        String string = restTemplate.getForObject(accessTokenUrl, String.class);
        WechatAccess wechatAccess = JSONObject.parseObject(string, WechatAccess.class);
        log.info("wechatAccess:{}", wechatAccess);

        return wechatAccess;
    }

    private static final String WECHAT_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

    /**
     * 获取微信个人用户信息
     *
     * @param wechatAccess
     * @return
     */
    private WechatUser getWechatUserInfo(WechatAccess wechatAccess) {
        String userInfoUrl = String.format(WECHAT_USERINFO_URL, wechatAccess.getAccess_token(),
                wechatAccess.getOpenid());

        String string = restTemplate.getForObject(userInfoUrl, String.class);

        try {
            string = new String(string.getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        WechatUser userInfo = JSONObject.parseObject(string, WechatUser.class);
        log.info("userInfo:{}", userInfo);

        return userInfo;
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String getToUrl(String toUrl, WechatUser wechatUserInfo) {
        StringBuilder builder = new StringBuilder(toUrl);
        String flag ="?" ;
        if (!toUrl.contains(flag)) {
            builder.append(flag);
        }

        if (wechatUserInfo.getUserId() != null) {
            builder.append("&hasUser=1");
        }
        builder.append("&openid=").append(wechatUserInfo.getOpenid());

        String tempCode = cacheWechatUserInfo(wechatUserInfo);
        builder.append("&tempCode=").append(tempCode);

        builder.append("&nickname=").append(wechatUserInfo.getNickname());
        builder.append("&headimgurl=").append(wechatUserInfo.getHeadimgurl());

        return builder.toString();
    }

    private String cacheWechatUserInfo(WechatUser wechatUserInfo) {
        String tempCode = UUID.randomUUID().toString();
        String key = prefixKey(tempCode);

        // 用tempCode和微信信息做个临时关系，后续的微信和账号绑定、微信登陆将会校验这个tempCode
        stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(wechatUserInfo), 4, TimeUnit.HOURS);
        log.info("缓存微信信息:{},{}", tempCode, wechatUserInfo);

        return tempCode;
    }

    private String prefixKey(String key) {
        return "wechat:temp:" + key;
    }

    @Autowired
    private AppUserService appUserService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void bindingUser(AppUser appUser, String tempCode, String openid) {
        WechatUser wechatUserInfo = checkAndGetWechatUserInfo(tempCode, openid);

        UserCredential userCredential = new UserCredential(openid, CredentialType.WECHAT_OPENID.name(), appUser.getId());
        userCredentialsMapper.save(userCredential);
        log.info("保存微信登陆凭证，{}", userCredential);

        if (StringUtils.isBlank(appUser.getHeadImgUrl())) {
            appUser.setHeadImgUrl(wechatUserInfo.getHeadimgurl());
            appUserService.updateAppUser(appUser);
        }

        wechatUserInfo.setUserId(appUser.getId());
        wechatMapper.update(wechatUserInfo);
        log.info("{}，绑定微信成功，给微信设置用户id，{}", appUser, wechatUserInfo);
    }

    @Override
    public WechatUser checkAndGetWechatUserInfo(String tempCode, String openid) {
        String key = prefixKey(tempCode);
        String string = stringRedisTemplate.opsForValue().get(key);
        if (string == null) {
            throw new IllegalArgumentException("无效的code");
        }

        WechatUser wechatUserInfo = JSONObject.parseObject(string, WechatUser.class);
        if (!wechatUserInfo.getOpenid().equals(openid)) {
            throw new IllegalArgumentException("无效的openid");
        }

        // 删除临时tempCode
        stringRedisTemplate.delete(tempCode);

        return wechatUserInfo;
    }

}
