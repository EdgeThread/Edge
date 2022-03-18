package com.ujiuye.seckill.controller;


import com.ujiuye.dongyimaicommon.entity.DateUtil;
import com.ujiuye.seckill.pojo.TbSeckillGoods;
import com.ujiuye.seckill.service.SeckilGoodslService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zs
 * @since 2021-11-05
 */
@RestController
@RequestMapping("/seckillGoods")
public class TbSeckillGoodsController {

    @Autowired
    SeckilGoodslService seckilGoodslService;

    @RequestMapping("menus")
    public List<Date> getMenus(){
        return DateUtil.getDateMenus();
    }

    @RequestMapping("list")
    public List<TbSeckillGoods> list(String time){
        return seckilGoodslService.list(time);
    }

    @RequestMapping("one")
    public TbSeckillGoods one(String time,String id){
        return seckilGoodslService.one(time,id);
    }

}

