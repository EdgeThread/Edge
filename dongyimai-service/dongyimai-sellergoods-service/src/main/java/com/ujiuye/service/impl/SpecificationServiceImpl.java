package com.ujiuye.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ujiuye.dao.TbSpecificationMapper;
import com.ujiuye.dao.TbSpecificationOptionMapper;
import com.ujiuye.feign.SpecEntity;
import com.ujiuye.pojo.TbSpecification;
import com.ujiuye.pojo.TbSpecificationOption;
import com.ujiuye.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
public class SpecificationServiceImpl implements SpecificationService{

    @Autowired
    TbSpecificationMapper specificationMapper;

    @Autowired
    TbSpecificationOptionMapper specificationOptionMapper;

    @Override
    public void add(SpecEntity specEntity) {
        specificationMapper.insert(specEntity.getSpecification());
        List<TbSpecificationOption> options = specEntity.getOptions();
        if(!CollectionUtils.isEmpty(options)){
            for (TbSpecificationOption option : options) {
                option.setSpecId(specEntity.getSpecification().getId());
                specificationOptionMapper.insert(option);
            }
        }
    }

    @Override
    public SpecEntity getById(long id) {
        TbSpecification specification = specificationMapper.selectById(id);
        List<TbSpecificationOption> options = specificationOptionMapper.selectList(new QueryWrapper<TbSpecificationOption>().eq("spec_id", id));
        return new SpecEntity(specification,options);

    }

    @Override
    public void update(SpecEntity specEntity) {
        specificationMapper.updateById(specEntity.getSpecification());
        specificationOptionMapper.delete(new QueryWrapper<TbSpecificationOption>().eq("spec_id",specEntity.getSpecification().getId()));
        List<TbSpecificationOption> options = specEntity.getOptions();
        for (TbSpecificationOption option : options) {
            option.setSpecId(specEntity.getSpecification().getId());
            specificationOptionMapper.insert(option);
        }
    }

    @Override
    public void delete(long id) {
        specificationMapper.deleteById(id);
        specificationOptionMapper.delete(new QueryWrapper<TbSpecificationOption>().eq("spec_id",id));
    }

    @Override
    public List<Map> getAllSpec() {
        return specificationMapper.getAllSpec();
    }
}
