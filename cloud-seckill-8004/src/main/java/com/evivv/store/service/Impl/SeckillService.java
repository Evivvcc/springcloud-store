package com.evivv.store.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.evivv.store.clients.ProductsClient;
import com.evivv.store.clients.UserClient;
import com.evivv.store.entity.Address;
import com.evivv.store.entity.Product;
import com.evivv.store.entity.SeckillOrder;
import com.evivv.store.mapper.SeckillMapper;
import com.evivv.store.service.ISeckillService;
import com.evivv.store.service.ex.DuplicateSeckillException;
import com.evivv.store.service.ex.InsertException;
import com.evivv.store.service.ex.ProductNotEnoughException;
import com.evivv.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class SeckillService extends ServiceImpl<SeckillMapper, SeckillOrder> implements ISeckillService {


    @Autowired
    private UserClient userClient;

    @Autowired
    private ProductsClient productsClient;


    @Override
    public SeckillOrder createSeckill(Integer pid, Integer uid, String username, String ticket) {

        // 查询库存
        JsonResult<Product> result = productsClient.getById(pid);
        Product product = result.getData();
        if (product.getNum() == 0) {
            throw new ProductNotEnoughException("库存不足");
        }
        // 查询秒杀订单
        SeckillOrder one = getOne(new QueryWrapper<SeckillOrder>().eq("uid", uid).eq("pid", pid));
        if (one != null) {
            throw new DuplicateSeckillException("重复抢购");
        }
        // 查询地址
        JsonResult<Address> result1 = userClient.getDefaultAddress("userTicket=" + ticket);
        Address address = result1.getData();
        // 生成订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUid(uid);
        seckillOrder.setPid(pid);
        seckillOrder.setRecvName(address.getName());
        seckillOrder.setRecvPhone(address.getPhone());
        seckillOrder.setRecvProvince(address.getProvinceName());
        seckillOrder.setRecvCity(address.getCityName());
        seckillOrder.setRecvArea(address.getAreaName());
        seckillOrder.setRecvAddress(address.getAddress());
        seckillOrder.setTotalPrice(product.getPrice());
        Date now = new Date();
        seckillOrder.setOrderTime(now);
        seckillOrder.setCreatedUser(username);
        seckillOrder.setCreatedTime(now);
        seckillOrder.setModifiedUser(username);
        seckillOrder.setModifiedTime(now);


        if (!save(seckillOrder)) {
            throw new InsertException("订单插入失败");
        }

        return seckillOrder;
    }


}
