package com.ujiuye.seckill.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillStatus implements Serializable {
    //秒杀用户名
    private String username;
    //创建时间
    private Date createTime;
    //秒杀状态  1:排队中，2:秒杀等待支付,3:支付超时，4:秒杀失败,5:支付完成
    private Integer status;
    //秒杀的商品ID
    private Long goodsId;
    //应付金额
    private Float money;
    //订单号
    private Long orderId;
    //时间段
    private String time;

}
