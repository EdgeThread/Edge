package com.ujiuye.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ujiuye.dao.TbItemCatMapper;
import com.ujiuye.pojo.TbItemCat;
import com.ujiuye.service.ItemCatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCatServiceImpl extends ServiceImpl<TbItemCatMapper, TbItemCat> implements ItemCatService {
    @Override
    public List<TbItemCat> getItemCatByParentId(long id) {
        return this.list(new QueryWrapper<TbItemCat>().eq("parent_id",id));
    }
}
