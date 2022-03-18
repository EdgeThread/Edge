package com.ujiuye.order.service.impl;

import com.ujiuye.dongyimaicommon.utils.IdWorker;
import com.ujiuye.feign.ItemFeign;
import com.ujiuye.order.dao.TbOrderItemMapper;
import com.ujiuye.order.dao.TbOrderMapper;
import com.ujiuye.order.dao.TbPayLogMapper;
import com.ujiuye.order.pojo.Cart;
import com.ujiuye.order.pojo.TbOrder;
import com.ujiuye.order.pojo.TbOrderItem;
import com.ujiuye.order.service.OrderService;
import com.ujiuye.order.util.TokenDecode;
import com.ujiuye.pay.pojo.TbPayLog;
import com.ujiuye.user.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    TbOrderMapper orderMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    IdWorker idWorker;

    @Autowired
    TbOrderItemMapper orderItemMapper;

    @Autowired
    ItemFeign itemFeign;

    @Autowired
    UserFeign userFeign;

    @Autowired
    TbPayLogMapper payLogMapper;

    @Autowired
    TokenDecode tokenDecode;
    @Override
    public void add(Map map) {
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("cartList").get(map.get("userId"));

        double amount = 0;
        String orderList = "";
        for (Cart cart : cartList) {
            long orderId = idWorker.nextId();
            TbOrder order = new TbOrder();
            order.setOrderId(orderId);
            order.setUserId(map.get("userId")+"");
            order.setStatus("1");
            order.setCreateTime(LocalDateTime.now());
            order.setUpdateTime(LocalDateTime.now());
            order.setSellerId(cart.getSellerId());

            for (TbOrderItem orderItem : cart.getOrderItemList()) {
                orderItem.setOrderId(orderId);
                orderItem.setId(idWorker.nextId());
                orderItem.setSellerId(cart.getSellerId());
                amount += orderItem.getTotalFee().doubleValue();
                orderList += orderItem.getOrderId()+",";
                orderItemMapper.insert(orderItem);
            }
            order.setPayment(new BigDecimal(amount));
            orderMapper.insert(order);
        }
        itemFeign.decrCount(map.get("userId")+"");

        userFeign.addPoints(10);

        TbPayLog payLog = new TbPayLog();
        payLog.setCreateTime(LocalDateTime.now());
        payLog.setOutTradeNo(new IdWorker().nextId()+"");
        payLog.setTotalFee((long)(amount*100));
        payLog.setUserId(map.get("userId")+"");
        payLog.setTradeState("0");
        payLog.setOrderList(orderList.substring(0,orderList.lastIndexOf(",")));
        payLog.setPayType("1");
        //想paylog表中插入数据
        payLogMapper.insert(payLog);
        //删除redis数据

        redisTemplate.opsForHash().put("payLog",map.get("userId"),payLog);
        redisTemplate.opsForHash().delete("cartList",map.get("userId"));
    }

    @Override
    public TbPayLog getPaylogFromRedis(String username) {
        return (TbPayLog) redisTemplate.opsForHash().get("payLog",username);
    }

    @Override
    public void updateOrderStatus(String out_trade_no, String trade_no) {
        TbPayLog payLog = payLogMapper.selectById(out_trade_no);
        if (payLog == null)
            payLog = (TbPayLog) redisTemplate.opsForHash().get("payLog", tokenDecode.getUserInfo().get("user_name")+"");
        payLog.setOutTradeNo(out_trade_no);
        payLog.setPayTime(LocalDateTime.now());
        payLog.setTradeState("1");
        payLog.setTransactionId(trade_no);
        payLogMapper.updateById(payLog);

        String[] orderIds = payLog.getOrderList().split(",");
        for (String orderId : orderIds) {
            TbOrder order = orderMapper.selectById(orderId);
            if (order != null){
                order.setStatus("2");//已付款
                order.setPaymentTime(LocalDateTime.now());
                orderMapper.updateById(order);
            }
        }
        redisTemplate.opsForHash().delete("payLog",payLog.getUserId());
    }


}
