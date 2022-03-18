package com.ujiuye.seckill.service.impl;

import com.ujiuye.seckill.pojo.SeckillStatus;
import com.ujiuye.seckill.service.SeckillOrderService;
import com.ujiuye.seckill.task.MultiThreadCreateOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Autowired
    MultiThreadCreateOrder multiThreadCreateOrder;

    @Autowired
    RedisTemplate redisTemplate;


    /****
     * 添加订单
     * @param id
     * @param time
     * @param username
     */
    @Override
    public Boolean add(Long id, String time, String username) throws InterruptedException {


        //排队信息封装
        SeckillStatus seckillStatus = new SeckillStatus(username, new Date(),1,id,0f,0l,time);

        redisTemplate.opsForList().leftPush("SeckillOrderQueue",seckillStatus);
        //将抢单状态存入redis中
        redisTemplate.opsForHash().put("UserStatusQueue",username,seckillStatus);

        multiThreadCreateOrder.createOrder();


        return true;
    }

    @Override
    public SeckillStatus query(String user_name) {
        return (SeckillStatus) redisTemplate.opsForHash().get("UserStatusQueue",user_name);
    }
}
