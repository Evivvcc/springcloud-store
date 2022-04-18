package com.evivv.store.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.evivv.store.entity.SeckillOrder;
import com.evivv.store.mapper.SeckillMapper;
import com.evivv.store.service.ISeckillService;
import com.evivv.store.service.ex.DuplicateSeckillException;
import com.evivv.store.service.ex.InsertException;
import com.evivv.store.service.ex.ProductNotEnoughException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SeckillService extends ServiceImpl<SeckillMapper, SeckillOrder> implements ISeckillService {
//    @Autowired
//    IAddressService addressService;
//    @Autowired
//    IProductService productService;


//    @Override
//    public SeckillOrder createSeckill(Integer aid, Integer pid, Integer uid, String username) {
//
//
//        // 查询库存
//        Product product = productService.getById(pid);
//        if (product.getNum() == 0) {
//            throw new ProductNotEnoughException("库存不足");
//        }
//        // 查询秒杀订单
//        SeckillOrder one = getOne(new QueryWrapper<SeckillOrder>().eq("uid", uid).eq("pid", pid));
//        if (one != null) {
//            throw new DuplicateSeckillException("重复抢购");
//        }
//        // 查询地址
//        Address address = addressService.getByAid(aid, uid);
//        // 生成订单
//        SeckillOrder seckillOrder = new SeckillOrder();
//        seckillOrder.setUid(uid);
//        seckillOrder.setPid(pid);
//        seckillOrder.setRecvName(address.getName());
//        seckillOrder.setRecvPhone(address.getPhone());
//        seckillOrder.setRecvProvince(address.getProvinceName());
//        seckillOrder.setRecvCity(address.getCityName());
//        seckillOrder.setRecvArea(address.getAreaName());
//        seckillOrder.setRecvAddress(address.getAddress());
//        seckillOrder.setTotalPrice(product.getPrice());
//        Date now = new Date();
//        seckillOrder.setOrderTime(now);
//        seckillOrder.setCreatedUser(username);
//        seckillOrder.setCreatedTime(now);
//        seckillOrder.setModifiedUser(username);
//        seckillOrder.setModifiedTime(now);
//
//
//
//        if (!save(seckillOrder)) {
//            throw new InsertException("订单插入失败");
//        }
//
//        return seckillOrder;
//    }


    @Override
    public SeckillOrder createSeckill(Integer aid, Integer pid, Integer uid, String username) {
        return null;
    }
}