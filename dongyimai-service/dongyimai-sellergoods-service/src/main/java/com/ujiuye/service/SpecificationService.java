package com.ujiuye.service;

import com.ujiuye.feign.SpecEntity;

import java.util.List;
import java.util.Map;

public interface SpecificationService {
    void add(SpecEntity specEntity);

    SpecEntity getById(long id);

    void update(SpecEntity specEntity);

    void delete(long id);

    List<Map> getAllSpec();
}
