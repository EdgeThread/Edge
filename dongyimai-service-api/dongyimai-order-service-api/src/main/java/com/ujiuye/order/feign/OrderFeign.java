package com.ujiuye.order.feign;

import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.pay.pojo.TbPayLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "dym-order")
@RequestMapping("order")
public interface OrderFeign {
    @GetMapping("getPaylogFromRedis/{username}")
    public Result<TbPayLog> getPaylog(@PathVariable String username);

    @RequestMapping("updateOrderStatus")
    Result updateOrderStatus(@RequestParam String out_trade_no,@RequestParam String trade_no);
}
