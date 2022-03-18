package com.ujiuye.feign;

import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.pojo.TbItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "dym-sellergoods")
@RequestMapping(value = "item")
public interface ItemFeign {

    /***
     * 根据审核状态查询Sku
     * @param status
     * @return
     */
    @GetMapping("/status/{status}")
    Result findByStatus(@PathVariable String status);

    @RequestMapping("/{id}")
    Result<TbItem> getById(@PathVariable long id);

    @PostMapping("decr/count")
    Result decrCount(@RequestParam String username);
}