package com.ujiuye.feign;

import com.ujiuye.pojo.TbSpecification;
import com.ujiuye.pojo.TbSpecificationOption;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "规格和规格选项实体类",value = "SpecEntity")
public class SpecEntity implements Serializable {
    private TbSpecification specification;
    private List<TbSpecificationOption> options;
}
