package com.evivv.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.evivv.store.entity.Product;
import com.evivv.store.vo.ProductVO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

/**
 * 处理商品数据的业务层接口
 */
public interface IProductService extends IService<Product> {
    /**
     * 查询热销商品的前四名
     *
     * @return 热销商品前四名的集合
     */
    List<Product> getHotList();

    /**
     * 根据商品id查询商品详情
     *
     * @param id 商品id
     * @return 匹配的商品详情，如果没有匹配的数据则返回null
     */
    ProductVO getInfoById(Integer id) throws JsonProcessingException;

    /**
     * 查询秒杀商品的前四名
     *
     * @return 热销商品前四名的集合
     */
    List<Product> getSeckillList();

    /**
     * 更新库存
     *
     * @param pid   商品的id
     * @param stock 商品的库存
     * @param num   下单的商品数量
     */
    boolean updateInventory(Integer pid, Integer stock, Integer num);
}
