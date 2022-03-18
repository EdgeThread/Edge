package com.ujiuye.search.service;

import java.util.Map;

public interface SkuService {
    void importSku();

    void delete();

    Map search(Map searchMap);
}
