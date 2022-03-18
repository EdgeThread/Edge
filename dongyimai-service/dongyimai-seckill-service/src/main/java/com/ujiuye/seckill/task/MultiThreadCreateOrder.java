package com.ujiuye.seckill.task;

import com.ujiuye.dongyimaicommon.utils.IdWorker;
import com.ujiuye.pay.pojo.TbPayLog;
import com.ujiuye.seckill.dao.TbSeckillGoodsMapper;
import com.ujiuye.seckill.pojo.SeckillStatus;
import com.ujiuye.seckill.pojo.TbSeckillGoods;
import com.ujiuye.seckill.pojo.TbSeckillOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MultiThreadCreateOrder {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbSeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    ObservePayStatus observePayStatus;
    /***
     * 多线程下单操作
     */
    @Async
    public void createOrder() throws InterruptedException {
            SeckillStatus status = (SeckillStatus) redisTemplate.opsForList().rightPop("SeckillOrderQueue");
            if (status == null) return;
            String time = status.getTime();
            long id = status.getGoodsId();
            String username = status.getUsername();


            //获取商品数据
            TbSeckillGoods goods = (TbSeckillGoods) redisTemplate.boundHashOps("SeckillGoods_" + time).get(id);

            //如果没有库存，则直接抛出异常
            if(goods==null || goods.getStockCount()<=0){
                status.setStatus(4);
                redisTemplate.opsForHash().put("UserStatusQueue",username,status);
                throw new RuntimeException("已售罄!");
            }
            //枷锁
            Object o = redisTemplate.opsForValue().get(id);
            int count =0 ;
            while (o != null){
                count ++;
                Thread.sleep(1000);
                if (count>120){
                    throw new RuntimeException("秒杀超时");
                }

                o = redisTemplate.opsForValue().get(id);
            }

        try {
            redisTemplate.opsForValue().set(id,"lock");
            //如果有库存，则创建秒杀商品订单
            TbSeckillOrder seckillOrder = new TbSeckillOrder();
            seckillOrder.setId(idWorker.nextId());
            seckillOrder.setSeckillId(id);
            seckillOrder.setMoney(goods.getCostPrice());
            seckillOrder.setUserId(username);
            seckillOrder.setCreateTime(LocalDateTime.now());
            seckillOrder.setStatus("0");//未支付状态

            //将秒杀订单存入到Redis中
            redisTemplate.boundHashOps("SeckillOrder").put(username,seckillOrder);

            TbPayLog payLog = new TbPayLog();
            payLog.setOutTradeNo(new IdWorker().nextId()+"");//支付单号
            payLog.setCreateTime(LocalDateTime.now());
            payLog.setTotalFee((long)(seckillOrder.getMoney().doubleValue()*100));
            payLog.setUserId(username);
            payLog.setTradeState("0");
            payLog.setOrderList(seckillOrder.getId()+"");
            payLog.setPayType("2");


            redisTemplate.opsForHash().put("payLog",username,payLog);
                   //库存减少
            goods.setStockCount(goods.getStockCount()-1);

            //判断当前商品是否还有库存
            if(goods.getStockCount()<=0){
                //并且将商品数据同步到MySQL中
                seckillGoodsMapper.updateById(goods);
                //如果没有库存,则清空Redis缓存中该商品
                redisTemplate.boundHashOps("SeckillGoods_" + time).delete(id);
            }else{
                //如果有库存，则直数据重置到Reids中，更新redis中商品库存
                redisTemplate.boundHashOps("SeckillGoods_" + time).put(id,goods);
            }

            status.setStatus(2);
            status.setMoney(seckillOrder.getMoney().floatValue());
            status.setOrderId(seckillOrder.getId());
            redisTemplate.opsForHash().put("UserStatusQueue",username,status);
            observePayStatus.observePayStatus(username,seckillOrder);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            redisTemplate.delete(id);
        }

    }
}
