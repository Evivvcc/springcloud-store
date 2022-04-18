package com.evivv.store.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.evivv.store.entity.SeckillOrder;

public interface ISeckillService extends IService<SeckillOrder> {
    /**
     * 创建秒杀订单
     * @param aid 收货地址的id
     * @param pid 秒杀的商品数据在秒杀商品表中的id
     * @param uid 当前登录的用户的id
     * @param username 当前登录的用户名
     * @return 成功创建的订单数据
     */
    SeckillOrder createSeckill(Integer aid, Integer pid, Integer uid, String username);
}
