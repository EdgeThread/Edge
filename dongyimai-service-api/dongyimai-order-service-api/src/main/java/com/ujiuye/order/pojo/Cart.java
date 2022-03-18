package com.ujiuye.order.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor@NoArgsConstructor
public class Cart implements Serializable {
    private String sellerId;
    private String sellerName;
    private List<TbOrderItem> orderItemList;
}
