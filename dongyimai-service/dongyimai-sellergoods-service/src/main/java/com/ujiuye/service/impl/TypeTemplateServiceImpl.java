package com.ujiuye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ujiuye.dao.TbTypeTemplateMapper;
import com.ujiuye.pojo.TbTypeTemplate;
import com.ujiuye.service.TypeTemplateService;
import org.springframework.stereotype.Service;

@Service
public class TypeTemplateServiceImpl  extends ServiceImpl<TbTypeTemplateMapper,TbTypeTemplate> implements TypeTemplateService {

    @Override
    public TbTypeTemplate getTemplateById(long id) {
        return this.getById(id);
    }
}
