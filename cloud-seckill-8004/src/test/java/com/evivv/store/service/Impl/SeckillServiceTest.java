package com.evivv.store.service.Impl;

import com.evivv.store.clients.UserClient;
import com.evivv.store.entity.Address;
import com.evivv.store.util.JsonResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SeckillServiceTest {

    @Autowired
    UserClient userClient;

    @Test
    void createSeckill() {
        JsonResult<Address> defaultAddress = userClient.getDefaultAddress("userTicket=fbde32fed358402eac7822a8c22d90c5");

    }
}