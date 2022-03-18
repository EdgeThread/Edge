package com.ujiuye.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ujiuye.dao.*;
import com.ujiuye.entity.GoodsEntity;
import com.ujiuye.pojo.TbItem;
import com.ujiuye.pojo.TbGoods;
import com.ujiuye.pojo.TbGoodsDesc;
import com.ujiuye.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GoodsServiceImpl  implements GoodsService {
    @Autowired
    TbGoodsMapper goodsMapper;
    @Autowired
    TbGoodsDescMapper goodsDescMapper;
    @Autowired
    TbItemMapper itemMapper;
    @Autowired
    TbItemCatMapper itemCatMapper;
    @Autowired
    TbBrandMapper brandMapper;


    @Override
    public void add(GoodsEntity goodsEntity) {
        TbGoods goods = goodsEntity.getGoods().setAuditStatus("0");
        goodsMapper.insert(goods);
        goodsEntity.getGoodsDesc().setGoodsId(goodsEntity.getGoods().getId());
        goodsDescMapper.insert(goodsEntity.getGoodsDesc());

        if("1".equals(goodsEntity.getGoods().getIsEnableSpec())){
            if (!CollectionUtils.isEmpty(goodsEntity.getItemList())){
                for (TbItem item : goodsEntity.getItemList()) {
                    String title = goodsEntity.getGoods().getGoodsName();
                    Map<String,String> map = JSON.parseObject(item.getSpec(), Map.class);
                    for (String s  : map.keySet()) {
                        title+=s;
                    }
                    item.setTitle(title);
                    item.setCategoryId(goodsEntity.getGoods().getCategory3Id());
                    item.setCreateTime(LocalDateTime.now());
                    item.setUpdateTime(LocalDateTime.now());
                    item.setGoodsId(goodsEntity.getGoods().getId());
                    item.setSellerId(goodsEntity.getGoods().getSellerId());
                    item.setCategory(itemCatMapper.selectById(goodsEntity.getGoods().getCategory3Id()).getName());
                    item.setBrand(brandMapper.selectById(goodsEntity.getGoods().getBrandId()).getName());
                    List<Map> maps = JSON.parseArray(goodsEntity.getGoodsDesc().getItemImages(), Map.class);
                    if (map.size()>0){
                        item.setImage((String)maps.get(0).get("url"));
                    }
                    itemMapper.insert(item);
                }
            }else {
                //不启用规格,item为默认值
                TbItem item = new TbItem();
                item.setTitle(goodsEntity.getGoods().getGoodsName());     //商品名称
                item.setPrice(goodsEntity.getGoods().getPrice());      //默认使用SPU的价格
                item.setNum(9999);
                item.setStatus("1");            //是否启用
                item.setIsDefault("1");         //是否默认
                item.setSpec("{}");             //没有选择规格，则放置空JSON结构

                item.setGoodsId(goodsEntity.getGoods().getId());

                itemMapper.insert(item);
            }

        }

    }

    @Override
    public GoodsEntity getById(long id) {
        TbGoods goods = goodsMapper.selectById(id);
        TbGoodsDesc goodsDesc = goodsDescMapper.selectById(id);
        List<TbItem> items = itemMapper.selectList(new QueryWrapper<TbItem>().eq("goods_id", id));
        return new GoodsEntity(goods,goodsDesc,items);
    }

    @Override
    public void audit(long id) {
        TbGoods goods = goodsMapper.selectById(id);
        if (goods == null) throw new RuntimeException("商品不存在");
        if(goods.getIsDelete().equals("1")) throw  new RuntimeException("商品已被删除");
        goods.setIsMarketable("1");
        goods.setAuditStatus("1");
        goodsMapper.updateById(goods);
    }

    @Override
    public void pull(long id) {
        TbGoods goods = goodsMapper.selectById(id);
        if (goods.getIsDelete().equals("1")) throw new RuntimeException("商品已被删除");
        goods.setIsMarketable("0");
        goodsMapper.updateById(goods);
    }

    @Override
    public void put(long id) {
        TbGoods goods = goodsMapper.selectById(id);
        if (goods.getIsDelete().equals("1")) throw new RuntimeException("商品已被删除");
        goods.setIsMarketable("1");
        goodsMapper.updateById(goods);
    }

    @Override
    public int putMany(long[] ids) {
      return  goodsMapper.update(new TbGoods().setIsMarketable("1"),new QueryWrapper<TbGoods>()
                .in("id", Arrays.asList(ids))
                .eq("is_marketable","0")
                .eq("is_delete","0")
                .eq("audit_status","1")
        );
    }

    @Override
    public int pullMany(long[] ids) {
        return  goodsMapper.update(new TbGoods().setIsMarketable("0"),new QueryWrapper<TbGoods>()
                .in("id", Arrays.asList(ids))
                .eq("is_marketable","1")
                .eq("is_delete","0")
                .eq("audit_status","1")
        );
    }

    @Override
    public void delete(long id) {
        if (goodsMapper.selectById(id).getIsMarketable().equals("1")) throw new RuntimeException("请先下架");
        goodsMapper.updateById(new TbGoods().setIsDelete("1"));
    }
}
