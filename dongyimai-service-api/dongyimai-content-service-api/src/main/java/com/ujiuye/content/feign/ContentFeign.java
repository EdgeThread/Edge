package com.ujiuye.content.feign;

import com.ujiuye.content.pojo.TbContent;
import com.ujiuye.dongyimaicommon.entity.Result;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "dym-content")
@RequestMapping("/content")
public interface ContentFeign {
    @GetMapping("/list/category/{id}")
    Result<List<TbContent>> getContentById(@PathVariable long id);
}
