package com.ujiuye.user.controller;

import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.user.util.TokenDecode;
import com.ujiuye.user.pojo.TbAddress;
import com.ujiuye.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("address")
@CrossOrigin
public class AddressController {
    @Autowired
    TokenDecode tokenDecode;
    @Autowired
    AddressService addressServide;

    @RequestMapping("/user/list")
    public Result<List<TbAddress>> getListByName(){
        String userName = tokenDecode.getUserInfo().get("user_name");
        return new Result<List<TbAddress>>(true,20000,"query OK",addressServide.getListByName(userName));
    }

}
