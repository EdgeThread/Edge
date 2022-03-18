package com.ujiuye.itempage.service.impl;

import com.alibaba.fastjson.JSON;
import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.entity.GoodsEntity;
import com.ujiuye.feign.GoodsFeign;
import com.ujiuye.feign.ItemCatFeign;
import com.ujiuye.itempage.service.PageService;
import com.ujiuye.pojo.TbItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Service
public class PageServiceImpl implements PageService {

    @Autowired
    GoodsFeign goodsFeign;

    @Autowired
    ItemCatFeign itemCatFeign;

    @Autowired
    TemplateEngine templateEngine;

    @Value("${pagepath}")
    String pagepath;


    /**
     * 根据skuId创建数据集合
     * @param skuId
     * @return
     */
    private Map<String,Object> buildData(long skuId){
        HashMap<String, Object> map = new HashMap<>();
        GoodsEntity goodsEntity = (GoodsEntity) (goodsFeign.getById(skuId).getData());

        map.put("goods",goodsEntity.getGoods());
        map.put("goodsDesc",goodsEntity.getGoodsDesc());
        map.put("itemList",goodsEntity.getItemList());

        TbItemCat itemCat1 = (TbItemCat) itemCatFeign.findById(goodsEntity.getGoods().getCategory1Id()).getData();
        TbItemCat itemCat2 = (TbItemCat) itemCatFeign.findById(goodsEntity.getGoods().getCategory2Id()).getData();
        TbItemCat itemCat3 = (TbItemCat) itemCatFeign.findById(goodsEntity.getGoods().getCategory3Id()).getData();
        map.put("category1",itemCat1);
        map.put("category2",itemCat2);
        map.put("category3",itemCat3);

        map.put("specList", JSON.parseArray(goodsEntity.getGoodsDesc().getSpecificationItems(),Map.class));
        map.put("imgList", JSON.parseArray(goodsEntity.getGoodsDesc().getItemImages(),Map.class));

        return map;

    }

    @Override
    public void createPageHtml(long id) {
        Context context = new Context();
        Map<String, Object> data = buildData(id);
        context.setVariables(data);

        File path = new File(pagepath);
        if (!path.exists()) path.mkdirs();
        File file = new File(pagepath + id + ".html");
        try {
            PrintWriter pw = new PrintWriter(file,"utf8");
            templateEngine.process("item",context,pw);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePageHtml(long id) {
        new File(pagepath+id+".html").delete();
    }
}
