package com.evivv.store.service.Impl;

import com.evivv.store.service.ISeckillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SeckillServiceImplTest {

    @Autowired
    private ISeckillService seckillService;

    @Test
    void addSeckillProducts() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(10000001);
        list.add(10000002);
        list.add(10000003);
        list.add(10000004);
        seckillService.addSeckillProducts(list);

    }

    @Test
    void createSeckill() {
        seckillService.createSeckill(10000001, 13);
    }
}