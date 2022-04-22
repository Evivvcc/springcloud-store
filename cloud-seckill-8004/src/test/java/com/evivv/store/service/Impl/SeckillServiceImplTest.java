package com.evivv.store.service.Impl;

import com.evivv.store.clients.ProductsClient;
import com.evivv.store.entity.SeckillProduct;
import com.evivv.store.service.ISeckillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SeckillServiceImplTest {

    @Autowired
    private ISeckillService seckillService;
    @Autowired
    private ProductsClient productsClient;

    @Test
    void addSeckillProducts() {
        List<SeckillProduct> all = productsClient.getAll();
        seckillService.addSeckillProducts(all);
    }

    @Test
    void createSeckill() {
        seckillService.createSeckill(10000001, 13);
    }
}