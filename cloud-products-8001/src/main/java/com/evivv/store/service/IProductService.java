package com.evivv.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.evivv.store.entity.Product;

import java.util.List;

/** 处理商品数据的业务层接口 */
public interface IProductService extends IService<Product> {
    /**
     * 查询热销商品的前四名
     * @return 热销商品前四名的集合
     */
    List<Product> getHotList();

    /**
     * 根据商品id查询商品详情
     * @param id 商品id
     * @return 匹配的商品详情，如果没有匹配的数据则返回null
     */
    Product getById(Integer id);

    /**
     * 查询秒杀商品的前四名
     * @return 热销商品前四名的集合
     */
    List<Product> getSeckillList();
}
