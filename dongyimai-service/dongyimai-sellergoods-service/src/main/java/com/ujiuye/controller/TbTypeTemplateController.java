package com.ujiuye.controller;


import com.ujiuye.dao.TbTypeTemplateMapper;
import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.service.TypeTemplateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-11-05
 */
@RestController
@RequestMapping("/typeTemplate")
public class TbTypeTemplateController {
    @Autowired
    TypeTemplateService typeTemplateService;

    @ApiOperation(value = "根据id获取typeTemplate",notes = "返回result")
    @GetMapping("{id}")
    public Result getTemplateById(@PathVariable long id){
        return new Result(true,20000,"查询成功",typeTemplateService.getTemplateById(id));
    }
}

