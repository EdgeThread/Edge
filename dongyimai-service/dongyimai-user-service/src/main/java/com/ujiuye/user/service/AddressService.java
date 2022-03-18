package com.ujiuye.user.service;

import com.ujiuye.user.pojo.TbAddress;

import java.util.List;

public interface AddressService {
    List<TbAddress> getListByName(String userName);
}
