package com.evivv.store.service.Impl;

import com.evivv.store.service.IAddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AddressServiceImplTest {
    @Autowired
    IAddressService addressService;

    @Test
    void getDefault() {
        addressService.getDefault(1);
    }
}