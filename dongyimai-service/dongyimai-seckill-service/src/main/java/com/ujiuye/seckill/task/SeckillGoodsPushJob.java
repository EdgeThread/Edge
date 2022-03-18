package com.ujiuye.seckill.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ujiuye.dongyimaicommon.entity.DateUtil;
import com.ujiuye.seckill.dao.TbSeckillGoodsMapper;
import com.ujiuye.seckill.pojo.TbSeckillGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class SeckillGoodsPushJob {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    TbSeckillGoodsMapper seckillGoodsMapper;

    @Scheduled(cron = "0/30 * * * * ?")
    public void loadGoods2Redis(){
        System.out.println("loading");
        List<Date> dateMenus = DateUtil.getDateMenus();
        for (Date dateMenu : dateMenus) {
            QueryWrapper<TbSeckillGoods> qw = new QueryWrapper<TbSeckillGoods>().eq("status", "1")
                    .gt("stock_count", 0)
                    .ge("start_time", DateUtil.date2StrFull(dateMenu))
                    .lt("end_time", DateUtil.date2StrFull(DateUtil.addDateHour(dateMenu, 2)));
            Set keys = redisTemplate.opsForHash().keys("seckillGoods_" + DateUtil.date2Str(dateMenu));
            if (keys != null && keys.size()>0)
                qw.notIn("id",keys);
            List<TbSeckillGoods> list = seckillGoodsMapper.selectList(qw);
            for (TbSeckillGoods goods : list) {
                redisTemplate.opsForHash().put("SeckillGoods_"+DateUtil.date2Str(dateMenu),goods.getGoodsId(),goods);
                redisTemplate.expireAt("SeckillGoods_"+DateUtil.date2Str(dateMenu),DateUtil.addDateHour(dateMenu,2));

            }


        }
    }
}
