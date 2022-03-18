package com.ujiuye.seckill.task;

import com.ujiuye.seckill.dao.TbSeckillGoodsMapper;
import com.ujiuye.seckill.pojo.SeckillStatus;
import com.ujiuye.seckill.pojo.TbSeckillGoods;
import com.ujiuye.seckill.pojo.TbSeckillOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ObservePayStatus {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    TbSeckillGoodsMapper seckillGoodsMapper;

    @Async
    public void observePayStatus(String username, TbSeckillOrder order) throws InterruptedException {
        int interval = 0;
        while (true){
            interval++;
            SeckillStatus status = (SeckillStatus) redisTemplate.opsForHash().get("UserStatusQueue", username);
            if (status.getStatus() == 2 ){
                Thread.sleep(1000);
                if (interval>200){
                    status.setStatus(3);
                    redisTemplate.opsForHash().put("UserStatusQueue",username,status);
                    //回复库存
                    Long goodsId = status.getGoodsId();
                    TbSeckillGoods goods = (TbSeckillGoods) redisTemplate.opsForHash().get("SeckillGoods_" + status.getTime(), goodsId);
                    if (goods == null)
                        goods = seckillGoodsMapper.selectById(goodsId);
                    goods.setStockCount(goods.getStockCount()+1);
                    redisTemplate.opsForHash().put("SeckillGoods_"+status.getTime(),goodsId,goods);
                    redisTemplate.opsForHash().delete("SeckillOrder",username);
                    break;
                }
                System.out.println(interval);
            }else  {
                redisTemplate.opsForHash().delete("UserStatusQueue",username);
                break;
            }
        }
    }
}
