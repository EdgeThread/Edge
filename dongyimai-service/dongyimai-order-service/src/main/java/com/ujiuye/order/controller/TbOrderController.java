package com.ujiuye.order.controller;


import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.order.service.OrderService;
import com.ujiuye.pay.pojo.TbPayLog;
import com.ujiuye.order.util.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin
@RequestMapping("/order")
public class TbOrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    TokenDecode tokenDecode;


    @PostMapping
    public Result add(@RequestBody Map map){
        try {
            String user_name = tokenDecode.getUserInfo().get("user_name");
            map.put("userId",user_name);
            orderService.add(map);
            return new Result(true,20000,"添加成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,20001,"failed",null);
        }
    }

    @GetMapping("getPaylogFromRedis/{username}")
    public Result<TbPayLog> getPaylog(@PathVariable String username){
        return new Result<TbPayLog>(true,20000,"ok",orderService.getPaylogFromRedis(username));
    }

    @RequestMapping("updateOrderStatus")
    Result updateOrderStatus(@RequestParam String out_trade_no,@RequestParam String trade_no){
        try {
            orderService.updateOrderStatus(out_trade_no,trade_no);
            return new Result(true,20000,"ok",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,20001,"failed",null);
        }
    }

}

