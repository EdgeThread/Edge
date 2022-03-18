package com.ujiuye.order.service;

import com.ujiuye.order.pojo.Cart;

import java.util.List;

public interface CartService {
    List<Cart> findCartListFromRedis(String username);

    List<Cart> addGoodsToCartList(List<Cart> cartList, long itemId, int num);

    void saveCartListToRedis(String username, List<Cart> cartList);
}
