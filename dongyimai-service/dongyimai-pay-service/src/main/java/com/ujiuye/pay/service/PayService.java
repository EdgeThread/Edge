package com.ujiuye.pay.service;

import com.alipay.api.AlipayApiException;

import java.util.Map;

public interface PayService {
    Map createNative(String out_trade_no, double totalAmount) throws AlipayApiException;

    Map queryPayStatus(String out_trade_no) throws AlipayApiException;
}
