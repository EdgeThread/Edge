package com.ujiuye.user.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 省份信息表
 * </p>
 *
 * @author EdgeThread
 * @since 2021-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbProvinces implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 唯一ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 省份ID
     */
    private String provinceid;

    /**
     * 省份名称
     */
    private String province;


}
