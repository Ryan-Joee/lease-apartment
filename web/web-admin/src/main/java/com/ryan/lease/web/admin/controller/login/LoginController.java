package com.ryan.lease.web.admin.controller.login;


import com.ryan.lease.common.login.LoginUserHolder;
import com.ryan.lease.common.result.Result;
import com.ryan.lease.common.utils.JwtUtil;
import com.ryan.lease.web.admin.service.LoginService;
import com.ryan.lease.web.admin.vo.login.CaptchaVo;
import com.ryan.lease.web.admin.vo.login.LoginVo;
import com.ryan.lease.web.admin.vo.system.user.SystemUserInfoVo;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "后台管理系统登录管理")
@RestController
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private LoginService service;

    @Operation(summary = "获取图形验证码")
    @GetMapping("login/captcha")
    public Result<CaptchaVo> getCaptcha() {
        CaptchaVo result = service.getCaptcha();
        return Result.ok(result);
    }

    @Operation(summary = "登录")
    @PostMapping("login")
//    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result<String> login(@RequestBody LoginVo loginVo) {
        String jwt = service.login(loginVo);
        return Result.ok(jwt);
    }

    @Operation(summary = "获取登陆用户个人信息")
    @GetMapping("info")
    public Result<SystemUserInfoVo> info() {
        Long userId = LoginUserHolder.getLoginUser().getUserId();
        SystemUserInfoVo systemUserInfoVo = service.getLoginUserInfoById(userId);
        return Result.ok(systemUserInfoVo);
    }
}