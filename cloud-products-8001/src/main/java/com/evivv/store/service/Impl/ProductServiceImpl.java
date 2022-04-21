package com.evivv.store.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.evivv.store.entity.Product;
import com.evivv.store.mapper.ProductMapper;
import com.evivv.store.service.IProductService;
import com.evivv.store.service.ex.ProductNotFoundException;
import com.evivv.store.util.RedisConstant;
import com.evivv.store.vo.ProductVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override

    public List<Product> getHotList() {
        Page<Product> page = new Page<>(0, 4);
        Page<Product> result = page(page, new QueryWrapper<Product>().eq("status", 1).orderByDesc("priority"));
        List<Product> records = page.getRecords();
        for (Product product : records) {
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }
        return records;
    }

    @Override
    public ProductVO getInfoById(Integer pid) throws JsonProcessingException {
        // 1. 从redis中查询商品信息
        String productJson = stringRedisTemplate.opsForValue().get(RedisConstant.CACHE_PRODUCT_KEY + pid);
        // 2. 判断是否存在 存在直接返回，不存在查询数据库
        if (productJson != null) {
            ProductVO productVO = mapper.readValue(productJson, ProductVO.class);
            return productVO;
        }
        // 3. 根据id查询数据库 不存在抛出异常，存在写入redis并返回
        Product result = getById(pid);
        if (result == null) {
            throw new ProductNotFoundException("尝试访问的商品数据不存在");
        }
        // 创建返回的商品
        ProductVO productVO = new ProductVO();
        productVO.setPid(pid);
        productVO.setTitle(result.getTitle());
        productVO.setSellPoint(result.getSellPoint());
        productVO.setPrice(result.getPrice());
        productVO.setImage(result.getImage());

        String s = mapper.writeValueAsString(productVO);
        stringRedisTemplate.opsForValue().set(RedisConstant.CACHE_PRODUCT_KEY + pid, s);
        // 返回查询结果
        return productVO;
    }


    @Override
    public List<Product> getSeckillList() {
        Page<Product> page = new Page<>(0, 4);
        Page<Product> result = page(page, new QueryWrapper<Product>().eq("status", 1).orderByAsc("priority"));
        List<Product> records = result.getRecords();
        for (Product product : records) {
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }
        return records;
    }

    /**
     * 更新库存
     *
     * @param pid   商品的id
     * @param stock 商品的库存
     * @param num   下单的商品数量
     */
    @Override
    public boolean updateInventory(Integer pid, Integer stock, Integer num) {
        // 库存更新做了CAS校验，防止超卖
        return update().setSql("num = num - 1").eq("pid", pid).eq("num", stock).update();
    }
}
