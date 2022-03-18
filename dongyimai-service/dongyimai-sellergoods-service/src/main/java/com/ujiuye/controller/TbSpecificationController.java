package com.ujiuye.controller;


import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.feign.SpecEntity;
import com.ujiuye.service.SpecificationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-11-05
 */
@RestController
@RequestMapping("/specification")
public class TbSpecificationController {
    @Autowired
    SpecificationService specificationService;

    /**
     * 添加规格和规格选项
     * @param specEntity
     * @return
     */
    @PostMapping
    public Result add(@RequestBody @ApiParam(name = "spec复合实体",value = "specEntity",required = true)SpecEntity specEntity){
        specificationService.add(specEntity);
        return new Result(true,20000,"添加成功",null);
    }

    /**
     * 根据id查询specEntity
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable("id") long id){
        return new Result(true,20000,"查询成功",specificationService.getById(id));
    }

    /**
     * 根据id修改specEntity
     * @param id
     * @param specEntity
     * @return
     */

    @PutMapping("/{id}")
    public Result update(@PathVariable("id") long id,@RequestBody SpecEntity specEntity){
        specEntity.getSpecification().setId(id);
        specificationService.update(specEntity);
        return new Result(true,20000,"修改成功",null);
    }

    /**
     * 通过id删除规格
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") long id){
        specificationService.delete (id);
        return new Result(true,20000,"删除成功",null);
    }

    /**
     * 查询所有下拉规格菜单
     * @return
     */
    @ApiOperation(value = "获取所有下拉规格菜单",notes = "只获取id和text")
    @GetMapping("/selectOptions")
    public List<Map> getAllSpec(){
        return specificationService.getAllSpec();
    }


 }

