package com.ujiuye.controller;


import com.ujiuye.dongyimaicommon.entity.PageResult;
import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.dongyimaicommon.entity.StatusCode;
import com.ujiuye.pojo.TbBrand;
import com.ujiuye.service.BrandService;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/brand")
public class TbBrandController {
    @Autowired
    BrandService brandService;

    /**
     * 查询是所有品牌
     * @return
     */
    @GetMapping
    public Result<List<TbBrand>> getAll(){
        return  new Result<List<TbBrand>>(true, StatusCode.OK,"查询成功",brandService.getAll());
    }

    /**
     * 根据id查询品牌
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result findBrandById(@PathVariable("id")Long id){
        return brandService.findBrandById(id);
    }

    /**
     * 添加品牌
     * @param brand
     * @return
     */
    @PostMapping
    public Result addBrand(@RequestBody TbBrand brand){
        try{
            brandService.addBrand(brand);
            return new Result(true, StatusCode.OK,"新增成功",null);
        }catch (Exception e){
            return new Result(false,StatusCode.ERROR,e.getMessage(),null);
        }

    }

    /**
     * 根据id删除品牌
     * @param id
     */
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable("id")Long id){
        try {
            brandService.deleteById(id);
            return new Result(true,StatusCode.OK,"删除成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"删除失败"+e.getMessage(),null);
        }
    }

    /**
     * 根据id修改
     * @param id
     * @param brand
     * @return
     */

    @PutMapping("/{id}")
    public Result update(@PathVariable("id")Long id,@RequestBody TbBrand brand){
        brand.setId(id);
        try {
            brandService.updateBrand(brand);
            return new Result(true,20000,"修改成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,e.getMessage(),null);
        }
    }

    /**
     * 条件查询
     * @param brand
     * @return
     */
    @PutMapping("/search")
    public Result getByCondition(@RequestBody(required = false) TbBrand brand){
        return new Result(true,20000,"查询成功",brandService.getByCondition(brand));

    }

    /**
     * 条件分页查询
     * @param page
     * @param size
     * @param brand
     * @return
     */

    @GetMapping("/search/{page}/{size}")
    public Result<PageResult> findByPage(@RequestBody(required = false)TbBrand brand,
                                          @PathVariable("page")int page,
                                          @PathVariable("size") int size){
        System.out.println(brand.getFirstChar());
       return new Result<>(true,20000,"查询成功",brandService.findByPage(page, size, brand));
    }

    @GetMapping("selectOptions")
    @ApiOperation(value = "品牌下拉菜单",notes = "查询所有品牌菜单")
    public List<Map> getAllOptions(){
        return brandService.getAllOptions();
    }

}

