package com.ujiuye.user.controller;

import com.alibaba.fastjson.JSON;
import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.dongyimaicommon.utils.BCrypt;
import com.ujiuye.dongyimaicommon.utils.JwtUtil;
import com.ujiuye.dongyimaicommon.utils.PhoneFormatCheckUtils;
import com.ujiuye.user.util.TokenDecode;
import com.ujiuye.user.pojo.TbUser;
import com.ujiuye.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    TokenDecode tokenDecode;


    @RequestMapping("/load/{username}")
    public  Result<TbUser> findUser (@PathVariable String username){
        return new Result<>(true,20000,"query ok",userService.findByUsername(username));
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @RequestMapping("/{id}")
    public Result findById(@PathVariable int id){
        return new Result(true,20000,"query ok!",userService.findById(id));
    }

    @RequestMapping("sendCode")
    public Result sendCode(String phone){
        if (!PhoneFormatCheckUtils.isPhoneLegal(phone))
            return new Result(false,20001,"电话格式不正确",null);
        try {
            userService.createCode(phone);
            return new Result(true,20000,"创建成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,20001,"创建失败",null);
        }
    }

    @PostMapping("add")
    public Result add(@RequestBody TbUser user,String code){

        if(userService.checkSmsCode(user.getPhone(),code)){
            try {

                userService.add(user);
                return new Result(true,20000,"添加成功",null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else    {
            return new Result(false,20001,"验证码错误",null);
        }
        return null;
    }

    @RequestMapping("login")
    public Result login (String username, String password, HttpServletResponse response){
        TbUser user = userService.findByUsername(username);
        if (user != null && BCrypt.checkpw(password,user.getPassword())){
            Map<String,String> map = new HashMap<>();
            map.put("role","admin");
            map.put("success","success");
            map.put("username",user.getUsername());
            String token = JwtUtil.createJWT(UUID.randomUUID().toString(), JSON.toJSONString(map), null);
            Cookie cookie = new Cookie("Authorization", token);
            cookie.setPath("/");
            response.addCookie(cookie);
            return new Result(true,20000,"登陆成功",token);
        }

        return new Result(false,20001,"账户不存在或者密码不正确",null);

    }

    @RequestMapping("/points/add")
    public Result  addPoints(@RequestParam("points") Integer points){
        try {
            String user_name = tokenDecode.getUserInfo().get("user_name");
            userService.addpoint(user_name,points);
            return  new Result(true,20000,"operation OK",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,20001,"failed",null);
        }
    }

}
