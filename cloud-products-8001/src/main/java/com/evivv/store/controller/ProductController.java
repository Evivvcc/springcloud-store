package com.evivv.store.controller;


import com.evivv.store.entity.Product;
import com.evivv.store.service.IProductService;
import com.evivv.store.service.ISeckillProductService;
import com.evivv.store.util.JsonResult;
import com.evivv.store.vo.ProductVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("products")
@RestController
public class ProductController extends BaseController {

    @Autowired
    IProductService productService;
    @Autowired
    ISeckillProductService seckillProductService;

    @GetMapping("/hot_list")
    public JsonResult<List<Product>> getHotList() {
        return new JsonResult<List<Product>>(OK, productService.getHotList());
    }

    @GetMapping("/{pid}/details")
    public JsonResult<ProductVO> findByid(@PathVariable("pid") Integer pid) throws JsonProcessingException {
        return new JsonResult<ProductVO>(OK, productService.getInfoById(pid));
    }



    /**
     * 更新库存
     *
     * @param pid   商品的id
     * @param stock 商品的库存
     * @param num   下单的商品数量
     */
    @RequestMapping("update_inventory")
    public boolean updateInventory(Integer pid, Integer stock, Integer num) {
        return productService.updateInventory(pid, stock, num);
    }



    /**
     * -------------------- 服务接口 --------------------
     */

    /**
     * 服务接口，根据pid提供商品信息
     * @param pid  秒杀的商品数据在秒杀商品表中的id
     * @return 成功创建的订单数据
     */
    @GetMapping("getInfoById")
    public Product getInfoById(Integer pid) {
        return productService.getById(pid);
    }
}
