package com.ujiuye.user.service;

import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.user.pojo.TbUser;

public interface UserService {
    void createCode(String phone);

    boolean checkSmsCode(String phone,String code);

    void add(TbUser user);

    TbUser findByUsername(String username);

    TbUser findById(int id);

    void addpoint(String user_name, int points);
}
