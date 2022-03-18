package com.ujiuye.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ujiuye.user.dao.AddressMapper;
import com.ujiuye.user.pojo.TbAddress;
import com.ujiuye.user.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressMapper addressMapper;
    @Override
    public List<TbAddress> getListByName(String userName) {
        return addressMapper.selectList(new QueryWrapper<TbAddress>().eq("user_id",userName));
    }
}
