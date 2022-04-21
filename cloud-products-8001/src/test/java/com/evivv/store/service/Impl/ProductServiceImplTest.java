package com.evivv.store.service.Impl;

import com.evivv.store.service.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductServiceImplTest {
    @Autowired
    IProductService productService;

    @Test
    void updateInventory() {
//        productService.updateInventory(10000001, 1);
        productService.update().setSql("num = num - 1").eq("pid",10000001).update();
    }

}