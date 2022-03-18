package com.ujiuye.seckill.service;

import com.ujiuye.seckill.pojo.TbSeckillGoods;

import java.util.List;

public interface SeckilGoodslService {
    List<TbSeckillGoods> list(String time);

    TbSeckillGoods one(String time, String id);


}
