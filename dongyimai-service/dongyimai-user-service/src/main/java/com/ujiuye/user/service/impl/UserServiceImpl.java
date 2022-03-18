package com.ujiuye.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.dongyimaicommon.utils.BCrypt;
import com.ujiuye.user.dao.TbUserMapper;
import com.ujiuye.user.pojo.TbUser;
import com.ujiuye.user.service.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    TbUserMapper userMapper;

    @Override
    public void createCode(String phone) {
        String code = (long) (Math.random() * 1000000) + "";
        stringRedisTemplate.boundHashOps("smsCode").put(phone,code);

        Map map = new HashMap<>();
        map.put("mobile",phone);
        map.put("code",code);
        rabbitTemplate.convertAndSend("dongyimai.sms.queue",map);
    }

    @Override
    public boolean checkSmsCode(String phone,String code) {
        String smsCode = (String)stringRedisTemplate.opsForHash().get("smsCode", phone);
        System.out.println(smsCode);
        System.out.println(phone);
        System.out.println(code);
        if (smsCode == null)
            return false;
        if (smsCode.equals(code))
            return true;
        return false;
    }

    @Override
    public void add(TbUser user) {
        String password = user.getPassword();
        String encode = new BCryptPasswordEncoder().encode(password);
        user.setPassword(encode);
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Override
    public TbUser findByUsername(String username) {
       return userMapper.selectOne(new QueryWrapper<TbUser>().eq("username",username));
    }

    @Override
    public TbUser findById(int id) {
        return userMapper.selectById(id);
    }

    @Override
    public void addpoint(String user_name, int points) {
        userMapper.addPoints(user_name,points);
    }
}
