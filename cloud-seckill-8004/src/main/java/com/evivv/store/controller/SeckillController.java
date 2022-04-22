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
    public JsonResult<Long> create(Integer spid, @CookieValue("userTicket") String ticket) {
        User user = (User) redisTemplate.opsForValue().get("user:" + ticket);
        Long orderId = seckillService.createSeckill(spid, user.getUid());
        return new JsonResult<Long>(OK, orderId);
    }


}
