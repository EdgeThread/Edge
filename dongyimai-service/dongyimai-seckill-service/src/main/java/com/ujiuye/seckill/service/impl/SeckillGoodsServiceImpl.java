package com.ujiuye.seckill.service.impl;

import com.ujiuye.seckill.dao.TbSeckillGoodsMapper;
import com.ujiuye.seckill.pojo.TbSeckillGoods;
import com.ujiuye.seckill.service.SeckilGoodslService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeckillGoodsServiceImpl implements SeckilGoodslService {
    @Autowired
    TbSeckillGoodsMapper goodsMapper;

    @Autowired
    RedisTemplate redisTemplate;


    @Override
    public List<TbSeckillGoods> list(String time) {
        return redisTemplate.opsForHash().values("SeckillGoods_"+time);
    }

    @Override
    public TbSeckillGoods one(String time, String id) {
        TbSeckillGoods tbSeckillGoods = (TbSeckillGoods) redisTemplate.opsForHash().get("SeckillGoods_" + time, id);
        return tbSeckillGoods;
    }
}
