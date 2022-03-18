package com.ujiuye.service;

import com.ujiuye.entity.GoodsEntity;

public interface GoodsService {
    void add(GoodsEntity goodsEntity);

    GoodsEntity getById(long id);

    void audit(long id);

    void pull(long id);

    void put(long id);

    int putMany(long[] ids);

    int pullMany(long[] ids);

    void delete(long id);
}
