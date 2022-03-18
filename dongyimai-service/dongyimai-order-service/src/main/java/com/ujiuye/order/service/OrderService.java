package com.ujiuye.order.service;

import com.ujiuye.pay.pojo.TbPayLog;

import java.util.Map;

public interface OrderService {
    void add(Map map);

    TbPayLog getPaylogFromRedis(String username);

    void updateOrderStatus(String out_trade_no, String trade_no);
}
