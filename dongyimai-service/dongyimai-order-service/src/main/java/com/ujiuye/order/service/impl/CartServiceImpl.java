package com.ujiuye.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.ujiuye.feign.ItemFeign;
import com.ujiuye.order.pojo.Cart;
import com.ujiuye.order.pojo.TbOrderItem;
import com.ujiuye.order.service.CartService;
import com.ujiuye.pojo.TbItem;
import org.codehaus.jackson.map.ser.std.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {


    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ItemFeign itemFeign;

    @Override
    public List<Cart> findCartListFromRedis(String username) {
        List<Cart> cartList = (List<Cart>) redisTemplate.boundHashOps("cartList").get(username);
        if (cartList == null)
            cartList = new ArrayList<>();
        return cartList;
    }

    @Override
    public List<Cart> addGoodsToCartList(List<Cart> cartList, long itemId, int num) {
        TbItem item = itemFeign.getById(itemId).getData();
        if(item==null){
            throw new RuntimeException("商品不存在");
        }
        if(!item.getStatus().equals("1")){
            throw new RuntimeException("商品状态无效");
        }
        //获取商家id
        String sellerId = item.getSellerId();
        //判断购物车中是否有该商家的商品
        Cart cart = searchCartBySellerId(cartList, sellerId);
        //不存在该商家的购物车
        if (cart == null ){
            List<Cart> carts = new ArrayList<>();
            TbOrderItem orderItem = createOrderItem(item, num);
            List<TbOrderItem> lsit = new ArrayList<>();
            lsit.add(orderItem);
            Cart cart1 = new Cart(item.getSellerId(), item.getSeller(), lsit);
            cartList.add(cart1);

        }else {
            //5.如果购物车列表中存在该商家的购物车
            // 判断购物车明细列表中是否存在该商品
            TbOrderItem orderItem = searchOrderItemByItemId(cart.getOrderItemList(), itemId);
            if (orderItem == null){
               cart.getOrderItemList().add( createOrderItem(item,num));
            }else {
                //5.2. 如果有，在原购物车明细上添加数量，更改金额
                orderItem.setNum(orderItem.getNum()+num);
                orderItem.setTotalFee(new BigDecimal(orderItem.getNum()*orderItem.getPrice().doubleValue()));
                //如果数量操作后小于等于0，则移除
                if(orderItem.getNum()<=0){
                    cart.getOrderItemList().remove(orderItem);//移除购物车明细
                }
                //如果移除后cart的明细数量为0，则将cart移除
                if(cart.getOrderItemList().size()==0){
                    cartList.remove(cart);
                }
            }
        }


        return cartList;
    }

    @Override
    public void saveCartListToRedis(String username, List<Cart> cartList) {
        redisTemplate.boundHashOps("cartList").put(username,cartList);
    }

    private TbOrderItem createOrderItem(TbItem item, Integer num){
        if(num<=0){
            throw new RuntimeException("数量非法");
        }

        TbOrderItem orderItem=new TbOrderItem();
        orderItem.setGoodsId(item.getGoodsId());
        orderItem.setItemId(item.getId());
        orderItem.setNum(num);
        orderItem.setPicPath(item.getImage());
        orderItem.setPrice(item.getPrice());
        orderItem.setSellerId(item.getSellerId());
        orderItem.setTitle(item.getTitle());
        orderItem.setTotalFee(new BigDecimal(orderItem.getPrice().doubleValue()*num));
        return orderItem;
    }
    /**
     * 根据商家ID查询购物车对象
     * @param cartList
     * @param sellerId
     * @return
     */
    private Cart searchCartBySellerId(List<Cart> cartList, String sellerId){
        for(Cart cart:cartList){
            if(cart.getSellerId().equals(sellerId)){
                return cart;
            }
        }
        return null;
    }
    /**
     * 根据商品明细ID查询
     * @param orderItemList
     * @param itemId
     * @return
     */
    private TbOrderItem searchOrderItemByItemId(List<TbOrderItem> orderItemList ,Long itemId ){
        for(TbOrderItem orderItem :orderItemList){
            if(orderItem.getItemId().longValue()==itemId.longValue()){
                return orderItem;
            }
        }
        return null;
    }
}
