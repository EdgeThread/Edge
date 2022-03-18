package com.ujiuye.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ujiuye.user.pojo.TbUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
//import com.ujiuye.generator.entity.TbUser;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author EdgeThread
 * @since 2021-11-05
 */
public interface TbUserMapper extends BaseMapper<TbUser> {

    @Update("update tb_user set points=points+#{points} where username = #{username}")
    void addPoints(@Param("username") String user_name, @Param("points") int points);
}
