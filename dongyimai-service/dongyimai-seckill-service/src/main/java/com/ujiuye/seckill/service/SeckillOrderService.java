package com.ujiuye.seckill.service;

import com.ujiuye.seckill.pojo.SeckillStatus;

public interface SeckillOrderService {
    Boolean add(Long id, String time, String username) throws InterruptedException;

    SeckillStatus query(String user_name);
}
