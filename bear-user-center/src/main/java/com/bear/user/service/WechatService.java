package com.bear.user.service;

import com.bear.common.entity.user.AppUser;
import com.bear.common.entity.user.WechatUser;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * @author panda.huangwei.
 * @since 2018-11-26 1:07.
 */
public interface WechatService {
    /**
     * 获取微信授权url
     *
     * @param app
     * @param request
     * @param toUrl
     * @return
     */
    String getWechatAuthorizeUrl(String app, HttpServletRequest request, String toUrl)
            throws UnsupportedEncodingException;

    /**
     * 获取微信个人用户信息
     *
     * @param app
     * @param request
     * @param code
     * @param state
     * @return
     */
    WechatUser getWechatUserInfo(String app, HttpServletRequest request, String code, String state);

    String getToUrl(String toUrl, WechatUser wechatUserInfo);

    void bindingUser(AppUser appUser, String tempCode, String openid);

    WechatUser checkAndGetWechatUserInfo(String tempCode, String openid);
}
