package com.evivv.store.controller;

import com.evivv.store.entity.SeckillOrder;
import com.evivv.store.service.ISeckillService;
import com.evivv.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("seckill")
public class SeckillController extends BaseController {

    @Autowired
    ISeckillService seckillService;
//    @Autowired
//    IAddressService addressService;

//    @RequestMapping("create")
//    public JsonResult<SeckillOrder> create(Integer aid, Integer pid, HttpSession session) {
//        // 从Session中取出uid和username
//        Integer uid = (Integer) session.getAttribute("uid");
//        String username = (String) session.getAttribute("username");
//
//        Address address = addressService.getOne(new QueryWrapper<Address>().eq("uid", uid).eq("is_default", 1));
//
//        SeckillOrder data = seckillService.createSeckill(address.getAid(), pid, uid, username);
//        return new JsonResult<SeckillOrder>(OK, data);
//
//
//    }
}
