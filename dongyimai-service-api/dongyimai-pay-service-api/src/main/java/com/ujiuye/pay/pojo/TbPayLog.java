package com.ujiuye.pay.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author EdgeThread
 * @since 2021-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbPayLog implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 支付订单号
     */
    @TableId(value = "out_trade_no",type=IdType.INPUT)
    private String outTradeNo;

    /**
     * 创建日期
     */
    private LocalDateTime createTime;

    /**
     * 支付完成时间
     */
    private LocalDateTime payTime;

    /**
     * 支付金额（分）
     */
    private Long totalFee;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 交易号码
     */
    private String transactionId;

    /**
     * 交易状态
     */
    private String tradeState;

    /**
     * 订单编号列表
     */
    private String orderList;

    /**
     * 支付类型
     */
    private String payType;


}
