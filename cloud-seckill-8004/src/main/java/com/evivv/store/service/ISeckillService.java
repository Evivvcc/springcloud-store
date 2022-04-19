package com.evivv.store.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.evivv.store.entity.SeckillOrder;


public interface ISeckillService extends IService<SeckillOrder> {
    /**
     * 创建秒杀订单
     *
     * @param pid 秒杀的商品数据在秒杀商品表中的id
     * @param uid 当前登录的用户的id
     * @return 成功创建的订单数据
     */
    SeckillOrder createSeckill(Integer pid, Integer uid, String username, String ticket);



    /**
     * 秒杀压力测试接口
     * @param pid 秒杀的商品数据在秒杀商品表中的id
     * @param username 用户名称
     * @return 成功创建的订单数据
     */
    public SeckillOrder seckillTest(Integer pid,String username) ;
}
