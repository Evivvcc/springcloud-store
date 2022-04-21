package com.evivv.store.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVO {
    private Integer pid; // '商品id'
    private String title; //'商品标题'
    private String sellPoint; //'商品卖点'
    private Long price; //'商品单价
    private String image; //'图片路径'
}
