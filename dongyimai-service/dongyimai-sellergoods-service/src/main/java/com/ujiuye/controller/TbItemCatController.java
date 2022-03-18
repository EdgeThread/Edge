package com.ujiuye.controller;


import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.pojo.TbItemCat;
import com.ujiuye.service.ItemCatService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 商品类目 前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-11-05
 */
@RestController
@RequestMapping("/itemCat")
public class TbItemCatController {

    @Autowired
    ItemCatService itemCatService;

    /**
     * 根据父级id查询品类信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据父级id获取itemcat" , notes = "获取品类信息")
    @GetMapping("getItemCatByParentId/{id}")
    public Result<List<TbItemCat>> getItemCatByParentId(@PathVariable long id){
        return new Result<>(true,20000,"查询成功",itemCatService.getItemCatByParentId(id));
    }

    /**
     * 根据id查询分类
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<TbItemCat> findById(@PathVariable long id){
        return new Result(true,20000,"查询成功",itemCatService.getById(id));
    }
}

