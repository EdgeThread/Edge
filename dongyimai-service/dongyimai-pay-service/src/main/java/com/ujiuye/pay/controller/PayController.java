package com.ujiuye.pay.controller;

import com.alipay.api.AlipayApiException;
import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.dongyimaicommon.entity.StatusCode;
import com.ujiuye.order.feign.OrderFeign;
import com.ujiuye.pay.pojo.TbPayLog;
import com.ujiuye.pay.service.PayService;
import com.ujiuye.pay.util.TokenDecode;
import com.ujiuye.seckill.feign.SeckillFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("pay")
public class PayController {
    @Autowired
    PayService payService;

    @Autowired
    TokenDecode tokenDecode;

    @Autowired
    OrderFeign orderFeign;

    @Autowired
    SeckillFeign seckillFeign;

    @RequestMapping("createNative")
    public Result createNative() throws AlipayApiException {
        String user_name = tokenDecode.getUserInfo().get("user_name");
        TbPayLog payLog = orderFeign.getPaylog(user_name).getData();
        if (payLog == null )
            return new Result(false,20001,"交易出错",new HashMap<>().put("msg","交易出错"));
        String outTradeNo = payLog.getOutTradeNo();
        double amount = payLog.getTotalFee() / 100.00;
        return new Result(true,20000,"ok",payService.createNative(outTradeNo,amount));
    }

    @RequestMapping("queryPayStatus/{out_trade_no}")
    public Result queryPayStatus(@PathVariable String out_trade_no,
                                 @RequestParam(required = false,defaultValue = "n")String seckill,
                                 @RequestBody(required = false)Map seckillMap) throws InterruptedException, AlipayApiException {
        int i = 0;
        while (true){
            Map map = payService.queryPayStatus(out_trade_no);
            String trade_status = map.get("trade_status") + "";
            if(trade_status!=null && trade_status.equals("TRADE_SUCCESS")){
                if (seckill.equals("y")){
                    return seckillFeign.afterPaySeckill(seckillMap,map.get("trade_no")+"");
                }else{
                    orderFeign.updateOrderStatus(out_trade_no,map.get("trade_no")+"");
                    return new Result(true,StatusCode.OK,"支付成功",null);
                }
            }else if(trade_status!=null && trade_status.equals("TRADE_FINISHED")){
                return  new  Result(true,StatusCode.OK,"交易结束",null);
            }else if(trade_status!=null && trade_status.equals("TRADE_CLOSED")){
                return new  Result(true, StatusCode.OK,"未付款交易超时关闭",null);
            }
            Thread.sleep(3000);
            i++;
            if (i>=100)
                return new Result(false,20001,"支付超时",null);
        }
    }
}
