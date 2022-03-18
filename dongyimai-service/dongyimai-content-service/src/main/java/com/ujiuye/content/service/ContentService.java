package com.ujiuye.content.service;

import com.ujiuye.content.pojo.TbContent;

import java.util.List;

public interface ContentService {
    List<TbContent> getContentById(long id);
}
