package com.evivv.store.controller;


import com.evivv.store.entity.Product;
import com.evivv.store.service.IProductService;
import com.evivv.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController extends BaseController{

    @Autowired
    IProductService productService;

    @GetMapping("products/hot_list")
    public JsonResult<List<Product>> getHotList() {
        return new JsonResult<List<Product>>(OK, productService.getHotList());
    }

    @GetMapping("products/{id}/details")
    public JsonResult<Product> findByid(@PathVariable("id") Integer id) {
       return new JsonResult<Product>(OK, productService.getById(id));
    }

    @GetMapping("products/seckill_list")
    public JsonResult<List<Product>> getSeckillList() {
        return new JsonResult<List<Product>>(OK, productService.getSeckillList());
    }

}
