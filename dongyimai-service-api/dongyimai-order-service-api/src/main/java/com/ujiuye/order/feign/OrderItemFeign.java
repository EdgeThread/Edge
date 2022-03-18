package com.ujiuye.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "dym-order")
@RequestMapping("orderItem")
public interface OrderItemFeign {
}
