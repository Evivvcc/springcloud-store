package com.evivv.store.controller;

import com.evivv.store.entity.Address;
import com.evivv.store.entity.User;
import com.evivv.store.service.IAddressService;
import com.evivv.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users/addresses")
public class AddressController extends BaseController {
    @Autowired
    private IAddressService addressService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("add_new_address")
    public JsonResult<Void> addNewAddress(Address address, @CookieValue("userTicket") String ticket) {
        User user = (User) redisTemplate.opsForValue().get("user:" + ticket);
        // 调用业务对象的方法执行业务
        addressService.addNewAddress(user.getUid(), user.getUsername(), address);
        // 响应成功
        return new JsonResult<Void>(OK);
    }

    @GetMapping("get_addresses")
    public JsonResult<List<Address>> getByUid(@CookieValue("userTicket") String ticket) {
        User user = (User) redisTemplate.opsForValue().get("user:" + ticket);
        List<Address> data = addressService.getByUid(user.getUid());
        return new JsonResult<List<Address>>(OK, data);
    }

    @RequestMapping("{aid}/set_default")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid, @CookieValue("userTicket") String ticket) {
        User user = (User) redisTemplate.opsForValue().get("user:" + ticket);
        addressService.setDefault(aid, user.getUid(), user.getUsername());
        return new JsonResult<Void>(OK);
    }


    @GetMapping("get_defaultAddress")
    public JsonResult<Address> getDefaultAddress(@CookieValue("userTicket") String ticket) {
        User user = (User) redisTemplate.opsForValue().get("user:" + ticket);
        Address defaultAddress = addressService.getDefault(user.getUid());
        return new JsonResult<Address>(OK, defaultAddress);


    }


}
