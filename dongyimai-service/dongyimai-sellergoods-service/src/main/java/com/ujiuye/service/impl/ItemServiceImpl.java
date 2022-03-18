package com.ujiuye.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ujiuye.dao.TbItemMapper;
import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.order.pojo.Cart;
import com.ujiuye.order.pojo.TbOrderItem;
import com.ujiuye.pojo.TbItem;
import com.ujiuye.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    TbItemMapper itemMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public Result findByStatus(String status) {

        return new Result(true,20000,"查询成功",itemMapper.selectList(new QueryWrapper<TbItem>().eq("status",status)));
    }

    @Override
    public TbItem getById(long id) {
        return itemMapper.selectById(id);
    }

    @Override
    public void decrCount(String username) {
        List<Cart> cartList = (List<Cart>) redisTemplate.opsForHash().get("cartList", username);
        for (Cart cart : cartList) {
            for (TbOrderItem orderItem : cart.getOrderItemList()) {
                int count = itemMapper.decrCount(orderItem);
                if (count<=0)
                    throw new RuntimeException("库存不足");
            }
        }
    }
}
