package com.ujiuye.controller;


import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.pojo.TbItem;
import com.ujiuye.service.ItemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-11-05
 */
@RestController
@RequestMapping("/item")
public class TbItemController {

    @Autowired
    ItemService itemService;


    @ApiOperation(value = "根据状态查询")
    @GetMapping("/status/{status}")
    public Result findByStatus(@PathVariable String status){

        return itemService.findByStatus(status);
    }

    @RequestMapping("/{id}")
    public Result<TbItem> getById(@PathVariable long id){
        return  new Result(true,20000,"success",itemService.getById(id));
    }

    @PostMapping("decr/count")
    Result decrCount(@RequestParam String username){
        try {
            itemService.decrCount(username);
            return new Result(true,20000,"修改成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,20001,"修改失败",null);
        }
    }

}

