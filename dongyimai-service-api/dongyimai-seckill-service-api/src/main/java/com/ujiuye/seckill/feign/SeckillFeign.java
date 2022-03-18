package com.ujiuye.seckill.feign;

import com.ujiuye.dongyimaicommon.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "dym-seckill")
//@RequestMapping("/seckillOrder")
public interface SeckillFeign {
    @RequestMapping("/seckillOrder/afterPaySeckill")
    public Result afterPaySeckill(@RequestBody Map seckillMap, @RequestParam("tradeNo") String tradeNo);
}
