package com.evivv.store.clients;

import com.evivv.store.entity.Product;
import com.evivv.store.entity.SeckillProduct;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("cloud-products-service")
public interface ProductsClient {
    @GetMapping("/products/seckill_products/getInfoById")
    Product getInfoById(@RequestParam("spid") Integer spid);


    @RequestMapping("/products/seckill_products/update_inventory")
    boolean updateInventory(@RequestParam("spid") Integer pid, @RequestParam("stock") Integer stock, @RequestParam("num") Integer num);

    @GetMapping("/products/seckill_products/getAll")
    List<SeckillProduct> getAll();


}
