package com.evivv.store.clients;

import com.evivv.store.entity.Address;
import com.evivv.store.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("cloud-users-service")
public interface UserClient {

    @GetMapping("users/get_defaultAddress")
    Address getDefaultAddress();
}
