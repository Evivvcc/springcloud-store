package com.evivv.store.mapper;

import com.evivv.store.entity.SeckillProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SeckillProductMapperTest {

    @Autowired
    SeckillProductMapper seckillProductMapper;

    @Test
    public void save() {
        SeckillProduct seckillProduct = new SeckillProduct();

        seckillProductMapper.selectById(10000001);
    }


}