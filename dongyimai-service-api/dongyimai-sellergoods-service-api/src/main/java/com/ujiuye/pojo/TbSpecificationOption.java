package com.ujiuye.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
public class TbSpecificationOption implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 规格项ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 规格项名称
     */
    private String optionName;

    /**
     * 规格ID
     */
    private Long specId;

    /**
     * 排序值
     */
    private Integer orders;


}
