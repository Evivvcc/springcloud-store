package com.evivv.store.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.evivv.store.entity.SeckillOrder;
import com.evivv.store.entity.SeckillProduct;

import java.util.List;


public interface ISeckillService extends IService<SeckillOrder> {
    /**
     * 创建秒杀订单
     *  @param pid 秒杀的商品数据在秒杀商品表中的id
     * @param uid 当前登录的用户的id
     * @return
     */
    Long createSeckill(Integer pid, Integer uid);




    /**
     * 添加秒杀商品库存到redis，进行库存预热
     * @param products  秒杀的商品数据在秒杀商品表中的id列表
     * @return 成功创建的订单数据
     */
    void addSeckillProducts(List<SeckillProduct> products);
}
