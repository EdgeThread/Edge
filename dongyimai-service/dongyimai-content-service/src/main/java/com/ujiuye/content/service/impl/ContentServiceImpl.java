package com.ujiuye.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ujiuye.content.dao.TbContentMapper;
import com.ujiuye.content.pojo.TbContent;
import com.ujiuye.content.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    TbContentMapper contentMapper;

    @Override
    public List<TbContent> getContentById(long id) {
        return contentMapper.selectList(new QueryWrapper<TbContent>().eq("status","1")
        .eq("category_id",id)
        .orderByAsc("sort_order")
        );
    }
}
