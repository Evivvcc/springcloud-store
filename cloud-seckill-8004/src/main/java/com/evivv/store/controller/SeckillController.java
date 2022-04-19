package com.evivv.store.controller;

import com.evivv.store.entity.SeckillOrder;
import com.evivv.store.entity.User;
import com.evivv.store.service.ISeckillService;
import com.evivv.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("seckill")
public class SeckillController extends BaseController {

    @Autowired
    private ISeckillService seckillService;
    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping("create")
    public JsonResult<SeckillOrder> create(Integer pid, @CookieValue("userTicket") String ticket) {
        User user = (User) redisTemplate.opsForValue().get("user:" + ticket);
        SeckillOrder data = seckillService.createSeckill(pid, user.getUid(), user.getUsername(), ticket);
        return new JsonResult<SeckillOrder>(OK, data);
    }


    /**
     * 秒杀压力测试接口
     * @param pid 秒杀的商品数据在秒杀商品表中的id
     * @param username 用户名称
     * @return 成功创建的订单数据
     */
    @RequestMapping("create_test")
    public JsonResult<SeckillOrder> createTest(Integer pid, String username) {
        SeckillOrder data = seckillService.seckillTest(pid, username);
        return new JsonResult<SeckillOrder>(OK, data);
    }
}
