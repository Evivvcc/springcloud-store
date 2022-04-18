package com.evivv.store.controller;

import com.evivv.store.entity.Address;
import com.evivv.store.service.IAddressService;
import com.evivv.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("users")
public class AddressController extends BaseController{
    @Autowired
    IAddressService addressService;

    @RequestMapping("add_new_address")
    public JsonResult<Void> addNewAddress(Address address, HttpSession session) {
        // 从Session中获取uid和username
        Integer uid = (Integer) session.getAttribute("uid");
        String username = (String) session.getAttribute("username");
        // 调用业务对象的方法执行业务
        addressService.addNewAddress(uid, username, address);
        // 响应成功
        return new JsonResult<Void>(OK);
    }

    @GetMapping("get_addresses")
    public JsonResult<List<Address>> getByUid(HttpSession session) {
        Integer uid = (Integer) session.getAttribute("uid");
        List<Address> data = addressService.getByUid(uid);
        return new JsonResult<List<Address>>(OK, data);
    }

    @RequestMapping("{aid}/set_default")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid, HttpSession session) {
        Integer uid = (Integer)session.getAttribute("uid");
        String username = (String)session.getAttribute("username");
        addressService.setDefault(aid, uid, username);
        return new JsonResult<Void>(OK);
    }
}
