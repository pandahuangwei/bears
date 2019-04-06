package com.bear.user.service;

import com.bear.common.entity.user.AppUser;
import com.bear.common.entity.user.WechatUser;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author panda.
 * @since 2018-11-26 1:07.
 */
public interface WechatService {

    /**
     * 获取微信授权url
     *
     * @param app     app
     * @param request req
     * @param toUrl   uri
     * @return String
     * @throws UnsupportedEncodingException
     */
    String getWechatAuthorizeUrl(String app, HttpServletRequest request, String toUrl)
            throws UnsupportedEncodingException;

    /**
     * 获取微信个人用户信息
     *
     * @param app     app
     * @param request req
     * @param code    code
     * @param state   state
     * @return WechatUser
     */
    WechatUser getWechatUserInfo(String app, HttpServletRequest request, String code, String state);

    /**
     * 获取用户
     *
     * @param toUrl          toUrl
     * @param wechatUserInfo user
     * @return String
     */
    String getToUrl(String toUrl, WechatUser wechatUserInfo);

    /**
     * 绑定用户
     *
     * @param appUser  user
     * @param tempCode code
     * @param openid   openId
     */
    void bindingUser(AppUser appUser, String tempCode, String openid);

    /**
     * 检测是否有效并获取用户.
     *
     * @param tempCode code
     * @param openid   id
     * @return WechatUser
     */
    WechatUser checkAndGetWechatUserInfo(String tempCode, String openid);
}
