package com.ujiuye.entity;

import com.ujiuye.pojo.TbItem;
import com.ujiuye.pojo.TbGoods;
import com.ujiuye.pojo.TbGoodsDesc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsEntity implements Serializable {
    private TbGoods goods;
    private TbGoodsDesc goodsDesc;
    private List<TbItem> itemList;
}
