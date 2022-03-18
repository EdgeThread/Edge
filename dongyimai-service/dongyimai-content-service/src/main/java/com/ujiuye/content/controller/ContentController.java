package com.ujiuye.content.controller;

import com.ujiuye.content.pojo.TbContent;
import com.ujiuye.content.service.ContentService;
import com.ujiuye.dongyimaicommon.entity.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    ContentService contentService;


    /**
     * 根据品类id查询广告信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据品类id查询广告信息")
    @GetMapping("/list/category/{id}")
    public Result<List<TbContent>> getContentById(@PathVariable long id){
        return new Result<>(true,20000,"查询成功",contentService.getContentById(id));
    }
}
