package com.evivv.store.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.evivv.store.clients.ProductsClient;
import com.evivv.store.clients.UserClient;
import com.evivv.store.entity.Product;
import com.evivv.store.entity.SeckillMessage;
import com.evivv.store.entity.SeckillOrder;
import com.evivv.store.mapper.SeckillMapper;
import com.evivv.store.service.ISeckillService;
import com.evivv.store.service.ex.DuplicateSeckillException;
import com.evivv.store.service.ex.ProductNotEnoughException;
import com.evivv.store.util.RedisConstant;
import com.evivv.store.util.RedisIdWorker;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SeckillServiceImpl extends ServiceImpl<SeckillMapper, SeckillOrder> implements ISeckillService {


    @Autowired
    private UserClient userClient;

    @Autowired
    private ProductsClient productsClient;

    @Autowired
    RedisIdWorker redisIdWorker;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 加载Lua脚本
    private static final DefaultRedisScript<Long> SECKILL_SCRIPT;

    static {
        SECKILL_SCRIPT = new DefaultRedisScript<>();
        SECKILL_SCRIPT.setLocation(new ClassPathResource("seckill.lua"));
        SECKILL_SCRIPT.setResultType(Long.TYPE);
    }

    /**
     * 创建秒杀订单
     * @param pid    秒杀的商品数据在秒杀商品表中的id
     * @param uid    当前登录的用户的id
     * @return
     */
    @Override
    public Long createSeckill(Integer pid, Integer uid) {
        // 1 执行Lua脚本，将库存检查，库存预减，一人一单检查流程放在redis中
        Long result = stringRedisTemplate.execute(SECKILL_SCRIPT, Collections.emptyList(), pid.toString(), uid.toString());
        // 2 判断结果是否为0
        int i = result.intValue();
        // 2.1 不为 0，没有购买资格
        if (i != 0) {
            if (i == 1) throw new ProductNotEnoughException("库存不足");
            else throw new DuplicateSeckillException("不能重复下单");
        }
        // 2.2 为 0，有购买资格，把下单信息保存到消息队列，返回订单
        long orderId = redisIdWorker.nextId("order");

        // 将订单消息放入消息队列
        // 交换机名称
        String exchangeName = "order.topic";
        // 消息
        SeckillMessage seckillMessage = new SeckillMessage(uid, pid, orderId);
        // 存入消息队列
        rabbitTemplate.convertAndSend(exchangeName, "order.seckill", seckillMessage);
        return orderId;
    }

    /**
     * 添加秒杀商品库存到redis，进行库存预热
     *
     * @param products 秒杀的商品数据在秒杀商品表中的id列表
     * @return 成功创建的订单数据
     */
    @Override
    public void addSeckillProducts(List<Integer> products) {
        for (Integer pid : products) {
            // 获取商品信息
            Product byId = productsClient.getInfoById(pid);
            Integer num = byId.getNum();
            // 保存秒杀库存到Redis
            stringRedisTemplate.opsForValue().set(RedisConstant.SECKILL_STOCK_KEY + pid, num.toString());
        }
    }

//    @Override
//    public SeckillOrder createSeckill(Integer pid, Integer uid, String username, String ticket) {
//
//
//        // 查询库存
//        Product result = productsClient.getInfoById(pid);
//        Integer count = result.getNum();
//        if (count == 0) {
//            throw new ProductNotEnoughException("库存不足");
//        }
//        // 查询秒杀订单
//        SeckillOrder one = getOne(new QueryWrapper<SeckillOrder>().eq("uid", uid).eq("pid", pid));
//        if (one != null) {
//            throw new DuplicateSeckillException("重复抢购");
//        }
//
//        // 更新库存
//        productsClient.updateInventory(pid, count, 1);
//
//        // 查询地址
//        JsonResult<Address> result1 = userClient.getDefaultAddress("userTicket=" + ticket);
//        Address address = result1.getData();
//        // 生成订单
//        SeckillOrder seckillOrder = new SeckillOrder();
//        seckillOrder.setUid(uid);
//        seckillOrder.setPid(pid);
//        seckillOrder.setRecvName(address.getName());
//        seckillOrder.setRecvPhone(address.getPhone());
//        seckillOrder.setRecvProvince(address.getProvinceName());
//        seckillOrder.setRecvCity(address.getCityName());
//        seckillOrder.setRecvArea(address.getAreaName());
//        seckillOrder.setRecvAddress(address.getAddress());
//        seckillOrder.setTotalPrice(result.getPrice());
//        Date now = new Date();
//        seckillOrder.setOrderTime(now);
//        seckillOrder.setCreatedUser(username);
//        seckillOrder.setCreatedTime(now);
//        seckillOrder.setModifiedUser(username);
//        seckillOrder.setModifiedTime(now);
//
//
//        if (!save(seckillOrder)) {
//            throw new InsertException("订单插入失败");
//        }
//
//        return seckillOrder;

//    }

    /**
     * 秒杀压力测试接口
     *
     * @param pid      秒杀的商品数据在秒杀商品表中的id
     * @param username 用户名称
     * @return 成功创建的订单数据
     */
//    @Override
//    public SeckillOrder seckillTest(Integer pid, String username) {
//
//        // 查询库存
//        Product result = productsClient.getInfoById(pid);
//        Integer count = result.getNum();
//        if (count == 0) {
//            throw new ProductNotEnoughException("库存不足");
//        }
//        return create(pid, username, count);
//    }
//
//
//
//    // 悲观锁 保证一人一单
//    private SeckillOrder create(Integer pid, String username, Integer count) {
//        // 生成订单
//        SeckillOrder seckillOrder = new SeckillOrder();
//        synchronized (username.intern()) {
//            // 查询秒杀订单,一人一单
//            SeckillOrder one = getOne(new QueryWrapper<SeckillOrder>().eq("created_user", username).eq("pid", pid));
//            if (one != null) {
//                throw new DuplicateSeckillException("重复抢购");
//            }
//            if (productsClient.updateInventory(pid, count, 1)) {
//                Date now = new Date();
//                seckillOrder.setUid(1);
//                seckillOrder.setPid(pid);
//                seckillOrder.setRecvName(username);
//                seckillOrder.setOrderTime(now);
//                seckillOrder.setCreatedUser(username);
//                seckillOrder.setCreatedTime(now);
//                seckillOrder.setModifiedUser(username);
//                seckillOrder.setModifiedTime(now);
//
//                if (!save(seckillOrder))
//                    throw new InsertException("订单插入失败");
//
//            } else {
//                throw new InsertException("订单插入失败");
//            }
//        }
//        return seckillOrder;
//    }


}
