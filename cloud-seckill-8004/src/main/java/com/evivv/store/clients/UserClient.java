package com.evivv.store.clients;

import com.evivv.store.entity.Address;
import com.evivv.store.util.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("cloud-users-service")
public interface UserClient {

    @GetMapping("/users/addresses/get_defaultAddress")
    JsonResult<Address> getDefaultAddress(@RequestHeader(name = "Cookie") String ticket);
}
