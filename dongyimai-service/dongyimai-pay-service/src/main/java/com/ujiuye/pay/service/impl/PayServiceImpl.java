package com.ujiuye.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.ujiuye.pay.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PayServiceImpl implements PayService
{
    @Autowired
    AlipayClient alipayClient;

    @Override
    public Map createNative(String out_trade_no, double totalAmount) throws AlipayApiException {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        JSONObject object = new JSONObject();
        object.put("out_trade_no",out_trade_no);
        object.put("total_amount",totalAmount);
        object.put("qr_code_timeout_express","10m");
        object.put("subject","测试商品");

        request.setBizContent(object.toString());
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        Map map = new HashMap();
        if (response != null && response.isSuccess() && response.getCode().equals("10000")){
            map.put("qr_code",response.getQrCode());
            map.put("out_trade_no",out_trade_no);
            map.put("total_amount",totalAmount);
            map.put("code",response.getCode());
            map.put("msg",response.getMsg());
            System.out.println("预支付接口调用成功");
        }else{
            map.put("msg","调用失败");
        }
        return map;
    }

    @Override
    public Map queryPayStatus(String out_trade_no) throws AlipayApiException {
        Map map = new HashMap();
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();;
        bizContent.put("out_trade_no",out_trade_no);
        request.setBizContent(bizContent.toString());
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if (response != null && response.isSuccess() && response.getCode().equals("10000")){
            map.put("code",response.getCode());
            map.put("trade_no",response.getTradeNo());
            map.put("trade_status",response.getTradeStatus());
            System.out.println("调用成功");
        }else{
            System.out.println("调用失败");
            map.put("msg","failed");
        }
        return  map;
    }
}
