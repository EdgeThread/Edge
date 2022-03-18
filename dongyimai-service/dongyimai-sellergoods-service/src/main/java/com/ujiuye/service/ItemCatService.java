package com.ujiuye.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ujiuye.pojo.TbItemCat;

import java.util.List;

public interface ItemCatService extends IService<TbItemCat> {
    List<TbItemCat> getItemCatByParentId(long id);
}
