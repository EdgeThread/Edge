package com.ujiuye.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.ujiuye.dao.TbBrandMapper;
import com.ujiuye.dongyimaicommon.entity.PageResult;
import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.dongyimaicommon.entity.StatusCode;
import com.ujiuye.pojo.TbBrand;
import com.ujiuye.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl extends ServiceImpl<TbBrandMapper, TbBrand> implements BrandService {

    @Autowired
    TbBrandMapper brandMapper;
    @Override
    public List<TbBrand> getAll() {
        return this.list();
    }

    @Override
    public Result findBrandById(Long id) {
        if(id<=0) return new Result(false, StatusCode.ACCESSERROR,"id不正常",null);
        return new Result(true, StatusCode.OK,"查询成功",this.getById(id));
    }

    @Override
    public void addBrand(TbBrand brand) {
        if (StringUtils.isNullOrEmpty(brand.getFirstChar()))throw new RuntimeException("s首字母不能为空");
        if (StringUtils.isNullOrEmpty(brand.getName()))throw new RuntimeException("名字不能为空");
        this.save(brand);
    }

    @Override
    public void deleteById(Long id) {
        if (id<=0) throw new RuntimeException("id超范围");
        this.removeById(id);
    }

    @Override
    public void updateBrand(TbBrand brand) {
        if (brand.getId() == null)throw  new RuntimeException("id不能为空");
        if (StringUtils.isNullOrEmpty(brand.getFirstChar()))throw new RuntimeException("s首字母不能为空");
        if (StringUtils.isNullOrEmpty(brand.getName()))throw new RuntimeException("名字不能为空");
        this.updateById(brand);
    }

    @Override
    public List<TbBrand> getByCondition(TbBrand brand) {
        QueryWrapper<TbBrand> qw = this.getQueryWraper(brand);
        return this.list(qw);
    }

    @Override
    public PageResult<TbBrand> findByPage(int page, int size, TbBrand brand) {
        Page<TbBrand> page1 = new Page<>(page, size);
        Page<TbBrand> page2 = page(page1, this.getQueryWraper(brand));
        return new PageResult<TbBrand>(page2.getTotal(),page2.getRecords());
    }

    @Override
    public List<Map> getAllOptions() {

        return brandMapper.getAllOptions();
    }

    public QueryWrapper<TbBrand> getQueryWraper(TbBrand brand){
        QueryWrapper<TbBrand> qw = new QueryWrapper<>();
        if (brand.getId()!=null){
            qw.eq("id",brand.getId());
        }
        if (brand.getName()!= null){
            qw.like("name",brand.getName());
        }
        if (brand.getFirstChar()!=null)
        {
            qw.like("first_char",brand.getFirstChar());
        }
        return qw;
    }
}
