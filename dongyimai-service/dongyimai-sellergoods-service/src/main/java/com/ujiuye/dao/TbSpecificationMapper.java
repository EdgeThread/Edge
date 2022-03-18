package com.ujiuye.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ujiuye.pojo.TbSpecification;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
//import com.ujiuye.generator.entity.TbSpecification;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author EdgeThread
 * @since 2021-11-05
 */
public interface TbSpecificationMapper extends BaseMapper<TbSpecification> {

    @Select("select id,spec_name as text from tb_specification")
    List<Map> getAllSpec();
}
