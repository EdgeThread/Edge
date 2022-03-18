package com.ujiuye.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ujiuye.pojo.TbBrand;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author EdgeThread
 * @since 2021-11-05
 */
public interface TbBrandMapper extends BaseMapper<TbBrand> {

    @Select("select id,name as text from tb_brand")
    List<Map> getAllOptions();
}
