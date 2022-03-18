package com.ujiuye.userauth.controller;

import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.user.feign.UserFeign;
import com.ujiuye.userauth.service.AuthService;
import com.ujiuye.userauth.util.CookieUtil;
import com.ujiuye.userauth.util.TokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("user")
public class AuthCotroller {
    @Value("${auth.cookieDomain}")
    String cookieDomain;
    @Value("${auth.cookieMaxAge}")
    int cookieMaxAge;

    @Autowired
    AuthService authService;
    @Autowired
    UserFeign userFeign;

    @RequestMapping("login")
    public Result login(String username, String password, HttpServletResponse response,String clientId,String clientSecret){
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password))
            return new Result(false,20001,"用户名或者密码不能为空",null);
        TokenUtil tokenUtil = authService.login(username,password,clientId,clientSecret);
        String accessToken = tokenUtil.getAccessToken();
        CookieUtil.addCookie(response,cookieDomain,"/","Authorization",accessToken,-1,false);
        return new Result(true,20000,"登陆成功",accessToken);
    }
}
