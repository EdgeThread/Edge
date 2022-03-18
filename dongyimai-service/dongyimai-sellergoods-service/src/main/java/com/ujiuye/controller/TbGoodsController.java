package com.ujiuye.controller;


import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.dongyimaicommon.entity.StatusCode;
import com.ujiuye.entity.GoodsEntity;
import com.ujiuye.service.GoodsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-11-05
 */
@RestController
@RequestMapping("/goods")
public class TbGoodsController {
    @Autowired
    GoodsService goodsService;


    /**
     * 添加商品
     * @param goodsEntity
     * @return
     */
    @ApiOperation(value = "添加商品",notes = "根据goodsENtity")
    @PostMapping
    public Result add(@RequestBody GoodsEntity goodsEntity){
        try {
            goodsService.add(goodsEntity);
            return new Result(true,20000,"添加成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR,"添加失败",null);
        }
    }

    /**
     * get局id获取商品实体
     * @param id
     * @return
     */
    @ApiOperation(value = "getGoodsById" ,notes = "根据id查找商品")
    @GetMapping("/{id}")
    public Result<GoodsEntity> getById(@PathVariable long id){
        return new Result(true,20000,"查询成功",goodsService.getById(id));
    }

    /**
     * 根据id审核商品
     * @param id
     * @return
     */

    @ApiOperation(value = "audit",notes = "根据id审核商品")
    @PutMapping("/audit/{id}")
    public Result aodit(@PathVariable("id") long id){
        try {
            goodsService.audit(id);
            return new Result(true,20000,"审核成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,StatusCode.ERROR,"审核失败"+e.getMessage(),null);
        }
    }

    /**
     * 下架商品
     * @param id
     * @return
     */
    @ApiOperation(value = "pull商品",notes = "根据id下架商品")
    @PutMapping("pull/{id}")
    public Result upMarket(@PathVariable long id ){
        try {
            goodsService.pull(id);
            return new Result(true,20000,"下架成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,20001,"下架失败"+e.getMessage(),null);
        }
    }

    /**
     * 下架商品
     * @param id
     * @return
     */
    @ApiOperation(value = "put商品",notes = "根据id上架商品")
    @PutMapping("put/{id}")
    public Result putMarket(@PathVariable long id ){
        try {
            goodsService.put(id);
            return new Result(true,20000,"上架成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,20001,"上架失败"+e.getMessage(),null);
        }
    }

    /**
     * 批量上架
     * @param ids
     * @return
     */
    @ApiOperation(value = "putmany",notes = "批量上架")
    @PutMapping("/put/many/")
    public Result batchPut(@RequestBody long[] ids){
        int count = goodsService.putMany(ids);
        return new Result(true,20000,"上架了"+count+"个商品",null);
    }

    /**
     * 批量下架
     * @param ids
     * @return
     */
    @ApiOperation(value = "putmany",notes = "批量下架")
    @PutMapping("/pull/many/")
    public Result batchPull(@RequestBody long[] ids){
        int count = goodsService.pullMany(ids);
        return new Result(true,20000,"下架了"+count+"个商品",null);
    }

    /**
     * 逻辑删除商品
     * @param id
     * @return
     */
    @ApiOperation(value = "logicDelete",notes = "逻辑删除商品")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable long id){
        try {
            goodsService.delete(id);
            return  new Result(true,20000,"删除成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false,20001,"删除失败"+e.getMessage(),null);
        }
    }
}

