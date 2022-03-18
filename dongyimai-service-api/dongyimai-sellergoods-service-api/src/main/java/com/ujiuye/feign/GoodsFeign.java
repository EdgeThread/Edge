package com.ujiuye.feign;

import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.entity.GoodsEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "dym-sellergoods")
@RequestMapping("goods")
public interface GoodsFeign {
    @GetMapping("/{id}")
    public Result<GoodsEntity> getById(@PathVariable long id);
}
