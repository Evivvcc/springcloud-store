package com.evivv.store.controller;

import com.evivv.store.entity.Order;
import com.evivv.store.service.IOrderService;
import com.evivv.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("orders")
public class OrderController extends BaseController{

    @Autowired
    IOrderService orderService;

    @RequestMapping("create")
    public JsonResult<Void> create(Integer aid, Integer[] cids, HttpSession session) {
        // 从Session中取出uid和username
        Integer uid = (Integer) session.getAttribute("uid");
        String username = (String) session.getAttribute("username");
        // 调用业务对象执行业务
        Order data = orderService.create(aid,cids, uid, username);
        // 返回成功与数据
        return new JsonResult<Void>(OK);
    }



}
