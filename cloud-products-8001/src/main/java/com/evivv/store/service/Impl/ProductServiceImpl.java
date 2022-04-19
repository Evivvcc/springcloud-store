package com.evivv.store.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.evivv.store.entity.Product;
import com.evivv.store.mapper.ProductMapper;
import com.evivv.store.service.IProductService;
import com.evivv.store.service.ex.ProductNotEnoughException;
import com.evivv.store.service.ex.ProductNotFoundException;
import com.evivv.store.service.ex.UpdateException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.zip.Adler32;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
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
    public Product getById(Integer pid) {
        Product result = super.getById(pid);
        if (result == null) {
            throw new ProductNotFoundException("尝试访问的商品数据不存在");
        }
        // 将查询结果中的部分属性设置为null
        result.setPriority(null);
        result.setCreatedUser(null);
        result.setCreatedTime(null);
        result.setModifiedUser(null);
        result.setModifiedTime(null);
        // 返回查询结果
        return result;
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


    @Override
    public void updateInventory(Integer pid, Integer num) {
        Product product = getOne(new QueryWrapper<Product>().eq("pid", pid));
        int count = product.getNum() - num;
        if (count < 0) {
            throw new ProductNotEnoughException("商品库存不足");
        }
        product.setNum(count);
        if (!updateById(product)){
            throw new UpdateException("库存更新失败");
        }
    }
}
