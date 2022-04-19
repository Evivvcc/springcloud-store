package com.evivv.store.controller;


import com.evivv.store.entity.Product;
import com.evivv.store.service.IProductService;
import com.evivv.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("products")
@RestController
public class ProductController extends BaseController {

    @Autowired
    IProductService productService;

    @GetMapping("/hot_list")
    public JsonResult<List<Product>> getHotList() {
        return new JsonResult<List<Product>>(OK, productService.getHotList());
    }

    @GetMapping("/{pid}/details")
    public JsonResult<Product> findByid(@PathVariable("pid") Integer id) {
        return new JsonResult<Product>(OK, productService.getById(id));
    }

    @GetMapping("/seckill_list")
    public JsonResult<List<Product>> getSeckillList() {
        return new JsonResult<List<Product>>(OK, productService.getSeckillList());
    }

    /**
     * 更新库存
     * @param pid 商品id
     * @param num 更新数量
     * @return
     */
    @RequestMapping("update_inventory")
    public JsonResult<Void> updateInventory(Integer pid, Integer num) {
        productService.updateInventory(pid, num);
        return new JsonResult<>(OK);
    }

    /**
     * -------------------- util --------------------
     */

}
