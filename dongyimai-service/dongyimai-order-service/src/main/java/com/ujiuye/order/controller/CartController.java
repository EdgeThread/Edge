package com.ujiuye.order.controller;

import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.order.pojo.Cart;
import com.ujiuye.order.service.CartService;
import com.ujiuye.order.util.TokenDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin
@RequestMapping("cart")
public class CartController {

    @Autowired
    CartService cartService;
    @Autowired
    TokenDecode tokenDecode;


    String userName = "";
    @RequestMapping("findCartList")
    public List<Cart> findCartList(){
        Map<String,String> userInfo = tokenDecode.getUserInfo();
        userName = userInfo.get("user_name");
        System.out.println(userName);
        return cartService.findCartListFromRedis(userName);
    }

    @RequestMapping("/addGoodsToCartList")
    public Result addGoodsToCartList(long itemId,int num){
        try {
//            String username ="ujiuye";
            List<Cart> cartList = findCartList();
            cartList = cartService.addGoodsToCartList(cartList,itemId,num);
            cartService.saveCartListToRedis(userName,cartList);
            return new Result(true,20000,"添加成功",null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,2001,"添加失败",null);
        }
    }

}
