package com.ujiuye.service;

import com.ujiuye.dongyimaicommon.entity.PageResult;
import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.pojo.TbBrand;

import java.util.List;
import java.util.Map;

public interface BrandService {
    /**
     * 获取所有品牌
     * @return
     */
    List<TbBrand> getAll();

    /**
     * 根据id获取品牌
     * @param id
     * @return
     */
    Result findBrandById(Long id);

    /**
     * 添加品牌
     * @param brand
     * @return
     */
    void addBrand(TbBrand brand);

    /**
     * 根据id删除品牌
     * @param id
     */
    void deleteById(Long id);

    /**
     * 修改品牌
     * @param brand
     */
    void updateBrand(TbBrand brand);

    /**
     * 条件查询
     * @param brand
     * @return
     */
    List<TbBrand> getByCondition(TbBrand brand);

    /**
     * 条件分页查询
     * @param page
     * @param size
     * @param brand
     * @return
     */
    PageResult<TbBrand> findByPage(int page, int size, TbBrand brand);

    /**
     * 查询所有品牌
     * @return
     */
    List<Map> getAllOptions();
}
