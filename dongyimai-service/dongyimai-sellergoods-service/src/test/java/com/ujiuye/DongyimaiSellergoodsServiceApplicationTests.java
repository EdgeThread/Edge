package com.ujiuye;

import com.ujiuye.service.impl.BrandServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class DongyimaiSellergoodsServiceApplicationTests {


    @Test
    void contextLoads() {
        BrandServiceImpl brandService = new BrandServiceImpl();
        List<Map> allOptions = brandService.getAllOptions();
        System.out.println(allOptions);
    }

}
