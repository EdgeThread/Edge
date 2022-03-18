package com.ujiuye.seckill.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.dongyimaicommon.entity.StatusCode;
import com.ujiuye.pay.pojo.TbPayLog;
import com.ujiuye.seckill.dao.TbPayLogMapper;
import com.ujiuye.seckill.dao.TbSeckillGoodsMapper;
import com.ujiuye.seckill.dao.TbSeckillOrderMapper;
import com.ujiuye.seckill.pojo.SeckillStatus;
import com.ujiuye.seckill.pojo.TbSeckillGoods;
import com.ujiuye.seckill.pojo.TbSeckillOrder;
import com.ujiuye.seckill.service.SeckillOrderService;
import com.ujiuye.seckill.util.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-11-05
 */
@RestController
@RequestMapping("/seckillOrder")
public class TbSeckillOrderController {

    @Autowired
    SeckillOrderService seckillOrderService;

    @Autowired
    TokenDecode tokenDecode;

    @Autowired
    TbSeckillOrderMapper seckillOrderMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    TbPayLogMapper payLogMapper;

    @Autowired
    TbSeckillGoodsMapper goodsMapper;



    /****
     * URL:/seckillOrder/add
     * 添加订单
     * 调用Service增加订单
     * 匿名访问：anonymousUser
     * @param time
     * @param id
     */
    @RequestMapping(value = "/add")
    public Result add(String time, Long id){
        try {
            //用户登录名
            String username = tokenDecode.getUserInfo().get("user_name");

            //调用Service增加订单
            Boolean bo = seckillOrderService.add(id, time, username);

            if(bo){
                //抢单成功
                return new Result(true, StatusCode.OK,"抢单成功！",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,e.getMessage(),null);
        }
        return new Result(true,StatusCode.ERROR,"服务器繁忙，请稍后再试",null);
    }

    @RequestMapping("/afterPaySeckill")
    public Result afterPaySeckill(@RequestBody Map seckillMap, @RequestParam("tradeNo") String tradeNo){

        TbSeckillOrder seckillOrder = (TbSeckillOrder) redisTemplate.boundHashOps("SeckillOrder").get(tokenDecode.getUserInfo().get("user_name"));
        seckillOrder.setReceiver(seckillMap.get("receiver")+"");
        seckillOrder.setReceiverAddress(seckillMap.get("receiverAddress")+"");
        seckillOrder.setReceiverMobile(seckillMap.get("receiverMobile")+"");
        seckillOrder.setTransactionId(tradeNo);
        seckillOrder.setPayTime(LocalDateTime.now());
        seckillOrder.setStatus("1");

        SeckillStatus seckillStatus = (SeckillStatus) redisTemplate.boundHashOps("UserStatusQueue").get(tokenDecode.getUserInfo().get("user_name"));
        seckillStatus.setStatus(5);
        redisTemplate.boundHashOps("UserStatusQueue").put(tokenDecode.getUserInfo().get("user_name")+"",seckillStatus);


        seckillOrderMapper.insert(seckillOrder);

        redisTemplate.boundHashOps("SeckillOrder").delete(tokenDecode.getUserInfo().get("user_name"));

        TbPayLog payLog = (TbPayLog) redisTemplate.opsForHash().get("payLog", tokenDecode.getUserInfo().get("user_name") + "");
        payLog.setPayTime(LocalDateTime.now());
        payLog.setTransactionId(tradeNo);
        payLog.setPayType("2");
        payLogMapper.insert(payLog);

        redisTemplate.opsForHash().delete("payLog",tokenDecode.getUserInfo().get("user_name"));
        TbSeckillGoods goods = goodsMapper.selectOne(new QueryWrapper<TbSeckillGoods>().eq("goods_id",seckillStatus.getGoodsId()));
        goods.setStockCount(goods.getStockCount()-1);
        goodsMapper.update(goods,new QueryWrapper<TbSeckillGoods>().eq("goods_id",goods.getGoodsId()));
//        redisTemplate.opsForHash().delete("UserStatusQueue",tokenDecode.getUserInfo().get("user_name"));

        return new Result(true,20000,"ok",null);
    }
    @RequestMapping("query")
    public Result queryStatus(){
        SeckillStatus seckillStatus = seckillOrderService.query(tokenDecode.getUserInfo().get("user_name"));
        if (seckillStatus != null)
            return new Result(true,20000,"ok", seckillStatus);
        return new Result(false,20001,"failed",null);
    }
}

