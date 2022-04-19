package com.evivv.store.clients;

import com.evivv.store.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("cloud-products-service")
public interface ProductsClient {
    @GetMapping("products/{id}/details")
    Product getById(@PathVariable("id") Integer id);

}
