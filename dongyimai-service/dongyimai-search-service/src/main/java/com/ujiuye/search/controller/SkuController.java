package com.ujiuye.search.controller;

import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.dongyimaicommon.entity.StatusCode;
import com.ujiuye.search.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/search")
@CrossOrigin
public class SkuController {

    @Autowired
    private SkuService skuService;

    /**
     * 导入数据
     * @return
     */
    @GetMapping("/import")
    public Result importSku(){
        skuService.importSku();
        return new Result(true, StatusCode.OK,"导入数据到索引库中成功！",null);
    }

    /**
     * 清空es数据
     * @return
     */
    @DeleteMapping("/delete")
    public Result deleteSku(){
        skuService.delete();
        return new Result(true, StatusCode.OK,"清空成功！",null);
    }

    @PostMapping
    public Map search(@RequestBody(required = false) Map searchMap){
       return skuService.search(searchMap);
    }
}
