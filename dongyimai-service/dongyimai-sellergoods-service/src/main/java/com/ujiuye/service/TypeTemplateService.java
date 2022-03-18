package com.ujiuye.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ujiuye.dao.TbTypeTemplateMapper;
import com.ujiuye.pojo.TbTypeTemplate;

public interface TypeTemplateService  {
    TbTypeTemplate getTemplateById(long id);
}
