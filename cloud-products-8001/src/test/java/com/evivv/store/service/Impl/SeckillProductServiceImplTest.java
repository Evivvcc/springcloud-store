package com.evivv.store.service.Impl;

import com.evivv.store.entity.SeckillProduct;
import com.evivv.store.service.ISeckillProductService;
import com.evivv.store.vo.ProductVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SeckillProductServiceImplTest {
    @Autowired
    ISeckillProductService seckillProductService;

    @Test
    void getSeckillList() {
        List<SeckillProduct> seckillList = seckillProductService.getSeckillList();
        for (SeckillProduct product : seckillList) {
            System.out.println(product.getSpid());
        }
    }

    @Test
    void getInfoById() throws JsonProcessingException {
        ProductVO infoById = seckillProductService.getInfoById(10000014);
        System.out.println(infoById.getSellPoint());
    }

    @Test
    void updateInventory() {
    }

    @Test
    void getAll() {
        seckillProductService.list();
    }
}