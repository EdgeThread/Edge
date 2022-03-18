package com.ujiuye.itempage.feign;

import com.ujiuye.dongyimaicommon.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "dym-itempage")
@RequestMapping("/page")
public interface ItemPageFeign {
    /***
     * 根据SpuID生成静态页
     * @param id
     * @return
     */
    @RequestMapping("/createHtml/{id}")
    Result createHtml(@PathVariable Long id);
    /***
     * 根据SpuID删除静态页
     * @param id
     * @return
     */
    @RequestMapping("/deleteHtml/{id}")
    Result deleteHtml(@PathVariable Long id);

}
