package com.evivv.store.clients;

import com.evivv.store.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductsClientTest {
    @Autowired
    ProductsClient productsClient;
    @Test
    void getById() {

    }
}