package com.ujiuye.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ujiuye.order.pojo.TbOrderItem;
import com.ujiuye.pojo.TbItem;
import org.apache.ibatis.annotations.Update;
//import com.ujiuye.generator.entity.TbItem;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author EdgeThread
 * @since 2021-11-05
 */
public interface TbItemMapper extends BaseMapper<TbItem> {

    @Update("update tb_item set num=num-#{num} where id=#{itemId} && num>#{num}")
    int decrCount(TbOrderItem orderItem);
}
