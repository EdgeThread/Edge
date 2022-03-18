package com.ujiuye.seckill.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class TbSeckillGoods implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * spu ID
     */
    private Long goodsId;

    /**
     * sku ID
     */
    private Long itemId;

    /**
     * 标题
     */
    private String title;

    /**
     * 商品图片
     */
    private String smallPic;

    /**
     * 原价格
     */
    private BigDecimal price;

    /**
     * 秒杀价格
     */
    private BigDecimal costPrice;

    /**
     * 商家ID
     */
    private String sellerId;

    /**
     * 添加日期
     */
    private LocalDateTime createTime;

    /**
     * 审核日期
     */
    private LocalDateTime checkTime;

    /**
     * 审核状态
     */
    private String status;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 秒杀商品数
     */
    private Integer num;

    /**
     * 剩余库存数
     */
    private Integer stockCount;

    /**
     * 描述
     */
    private String introduction;


}
