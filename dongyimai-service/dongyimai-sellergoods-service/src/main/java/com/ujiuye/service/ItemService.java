package com.ujiuye.service;

import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.pojo.TbItem;

public interface ItemService {
    Result findByStatus(String status);

    TbItem getById(long id);

    void decrCount(String username);
}
