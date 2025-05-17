package com.ryan.lease.web.admin.service;

import com.ryan.lease.web.admin.vo.login.CaptchaVo;
import com.ryan.lease.web.admin.vo.login.LoginVo;
import com.ryan.lease.web.admin.vo.system.user.SystemUserInfoVo;

public interface LoginService {

    /**
     * 获取图形验证码
     * @return CaptchaVo
     */
    CaptchaVo getCaptcha();

    /**
     * 登录
     * @param loginVo 登录用户信息
     * @return jwt
     */
    String login(LoginVo loginVo);
}
