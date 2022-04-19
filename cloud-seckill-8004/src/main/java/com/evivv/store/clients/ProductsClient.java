package com.evivv.store.clients;

import com.evivv.store.entity.Product;
import com.evivv.store.util.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cloud-products-service")
public interface ProductsClient {
    @GetMapping("/products/{pid}/details")
    JsonResult<Product> getById(@PathVariable("pid") Integer pid);


    @RequestMapping("/products/update_inventory")
    JsonResult<Void> updateInventory(@RequestParam("pid") Integer pid, @RequestParam("num") Integer num);
}
