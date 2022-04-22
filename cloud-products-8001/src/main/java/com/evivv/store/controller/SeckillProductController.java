package com.evivv.store.controller;

import com.evivv.store.entity.Product;
import com.evivv.store.entity.SeckillProduct;
import com.evivv.store.service.ISeckillProductService;
import com.evivv.store.util.JsonResult;
import com.evivv.store.vo.ProductVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.evivv.store.controller.BaseController.OK;

@RestController
@RequestMapping("products/seckill_products")
public class SeckillProductController {
    @Autowired
    ISeckillProductService seckillProductService;


    @GetMapping("/seckill_list")
    public JsonResult<List<SeckillProduct>> getSeckillList() {
        List<SeckillProduct> seckillList = seckillProductService.getSeckillList();
        // 将秒杀商品信息添加进缓存
        return new JsonResult<List<SeckillProduct>>(OK, seckillList);
    }

    @GetMapping("/{spid}/details")
    public JsonResult<ProductVO> findByid(@PathVariable("spid") Integer pid) throws JsonProcessingException {
        return new JsonResult<ProductVO>(OK, seckillProductService.getInfoById(pid));
    }


    /**
     * -------------------- 服务接口 --------------------
     */

    /**
     * 根据pid提供秒杀商品信息
     *
     * @param spid 秒杀的商品数据在秒杀商品表中的id
     * @return 成功创建的订单数据
     */
    @GetMapping("getInfoById")
    public SeckillProduct getInfoById(Integer spid) {
        return seckillProductService.getById(spid);
    }

    @GetMapping("getAll")
    public List<SeckillProduct> getAll() {
        return seckillProductService.list();
    }
}
