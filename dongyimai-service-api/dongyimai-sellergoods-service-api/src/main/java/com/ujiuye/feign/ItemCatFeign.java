package com.ujiuye.feign;

import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.pojo.TbItemCat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "dym-sellergoods")
@RequestMapping("itemCat")
public interface ItemCatFeign {
    /**
     * 根据id查询分类
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<TbItemCat> findById(@PathVariable long id);
}
