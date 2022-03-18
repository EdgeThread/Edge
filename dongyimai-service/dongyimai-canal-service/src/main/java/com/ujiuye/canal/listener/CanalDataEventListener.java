package com.ujiuye.canal.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.ujiuye.content.feign.ContentFeign;
import com.ujiuye.content.pojo.TbContent;
import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.itempage.feign.ItemPageFeign;
import com.xpand.starter.canal.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;

@CanalEventListener
public class CanalDataEventListener {

    @Autowired
    ContentFeign contentFeign;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    ItemPageFeign itemPageFeign;

    /**
     * 监听数据库以实时更新广告轮播图
     * @param eventType
     * @param rowData
     */
    @ListenPoint(destination = "example",
            schema = "dongyimaidb",
            table = {"tb_content_category", "tb_content"},
            eventType = {CanalEntry.EventType.UPDATE,
                    CanalEntry.EventType.DELETE,
                    CanalEntry.EventType.INSERT
            })
    public void onEventCustomUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        //1.获取品类名称
        List<CanalEntry.Column> list =null;
        String cid = null;
        if (eventType==(CanalEntry.EventType.INSERT)) list = rowData.getAfterColumnsList();
        else list=rowData.getBeforeColumnsList();
        for (CanalEntry.Column column : list) {
            if (column.getName().equalsIgnoreCase("category_id")) cid = column.getValue();
        }
        Result<List<TbContent>> category = contentFeign.getContentById(Long.parseLong(cid));
        List<TbContent> contentList = category.getData();
        if (contentList!=null && contentList.size()>0){
            redisTemplate.boundValueOps("content_"+cid).set(JSON.toJSONString(contentList));
        }
    }

    @ListenPoint(destination = "example",
            schema = "dongyimaidb",
            table = {"tb_goods_desc"},
            eventType = {CanalEntry.EventType.UPDATE,
                    CanalEntry.EventType.DELETE,
                    CanalEntry.EventType.INSERT
            })
    public void itemPageListener(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        String goodsId = "";
        System.out.println("rtyui");
        if (eventType == CanalEntry.EventType.DELETE){
            for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
                if (column.getName().equalsIgnoreCase("goods_id"))
                    goodsId = column.getValue();
                break;
            }
            System.out.println("delete");
            itemPageFeign.deleteHtml(Long.parseLong(goodsId));
        }else  {
            for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
                if (column.getName().equalsIgnoreCase("goods_id"))
                    goodsId = column.getValue();
                break;
            }
            System.out.println("update");
            System.out.println(goodsId);
            itemPageFeign.createHtml(Long.parseLong(goodsId));
        }
    }

}
