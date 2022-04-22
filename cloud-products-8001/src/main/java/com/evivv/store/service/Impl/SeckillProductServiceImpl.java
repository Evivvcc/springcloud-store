package com.evivv.store.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.evivv.store.entity.SeckillProduct;
import com.evivv.store.mapper.SeckillProductMapper;
import com.evivv.store.service.ISeckillProductService;
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
public class SeckillProductServiceImpl extends ServiceImpl<SeckillProductMapper, SeckillProduct> implements ISeckillProductService {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final ObjectMapper mapper = new ObjectMapper();


    @Override
    public List<SeckillProduct> getSeckillList() {
        Page<SeckillProduct> page = new Page<>(0, 4);
        Page<SeckillProduct> result = page(page, new QueryWrapper<SeckillProduct>().eq("status", 1).orderByAsc("priority"));
        List<SeckillProduct> records = result.getRecords();
        for (SeckillProduct seckillProduct : records) {
            seckillProduct.setPriority(null);
            seckillProduct.setCreatedUser(null);
            seckillProduct.setCreatedTime(null);
            seckillProduct.setModifiedUser(null);
            seckillProduct.setModifiedTime(null);
        }
        return records;
    }

    @Override
    public ProductVO getInfoById(Integer spid) throws JsonProcessingException {
        // 1. 从redis中查询商品信息
        String productJson = stringRedisTemplate.opsForValue().get(RedisConstant.CACHE_PRODUCT_KEY + spid);
        // 2. 判断是否存在 存在直接返回，不存在查询数据库
        if (productJson != null) {
            ProductVO productVO = mapper.readValue(productJson, ProductVO.class);
            return productVO;
        }
        // 3. 根据id查询数据库 不存在抛出异常，存在写入redis并返回
        SeckillProduct result = getById(spid);
        if (result == null) {
            throw new ProductNotFoundException("尝试访问的商品数据不存在");
        }
        // 创建返回的商品
        ProductVO productVO = new ProductVO();
        productVO.setPid(spid);
        productVO.setTitle(result.getTitle());
        productVO.setSellPoint(result.getSellPoint());
        productVO.setPrice(result.getPrice());
        productVO.setImage(result.getImage());

        String s = mapper.writeValueAsString(productVO);
        stringRedisTemplate.opsForValue().set(RedisConstant.CACHE_PRODUCT_KEY + spid, s);
        // 返回查询结果
        return productVO;
    }

    /**
     * 更新库存
     *
     * @param spid   商品的id
     * @param stock 商品的库存
     * @param num   下单的商品数量
     */
    @Override
    public boolean updateInventory(Integer spid, Integer stock, Integer num) {
        // 库存更新做了CAS校验，防止超卖
        return update().setSql("num = num - 1").eq("spid", spid).eq("num", stock).update();
    }



}
