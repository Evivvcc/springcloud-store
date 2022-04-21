package com.evivv.store.controller;

import com.evivv.store.entity.User;
import com.evivv.store.service.IUserService;
import com.evivv.store.util.CookieUtil;
import com.evivv.store.util.JsonResult;
import com.evivv.store.util.UUIDUtil;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.security.krb5.internal.Ticket;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.channels.ReadPendingException;
import java.util.concurrent.TimeUnit;

@RestController // @Controller + @ResponseBody
@RequestMapping("admin/users")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/reg")
    public JsonResult<Void> reg(User user) {
        userService.register(user);
        return new JsonResult<Void>(OK);
    }

    @RequestMapping("/login")
    public JsonResult<User> login(String username, String password, HttpServletRequest request,HttpServletResponse response) {
        User user = userService.login(username, password);
        // 保存信息到redis
        // 1 随机生成cookie，作为登录令牌
        String ticket = UUIDUtil.uuid();
        // 2 将数据存入redis
        redisTemplate.opsForValue().set("user:" + ticket, user);
        // 3 设置token有效期30分钟
        redisTemplate.expire("user:" + ticket, 30, TimeUnit.MINUTES);
        //向前端返回cookie
        CookieUtil.setCookie(request, response, "userTicket", ticket);

        return new JsonResult<User>(OK, user);
    }

    /**
     * 修改当前用户的密码
     *
     * @param ticket 用户登陆cookie
     * @return 当前登录的用户的信息
     */
    @RequestMapping("/change_password")
    public JsonResult<User> changePassword(String oldPassword, String newPassword, @CookieValue("userTicket") String ticket) {
        User user = (User) redisTemplate.opsForValue().get("user:" + ticket);
        // 调用业务对象执行修改密码
        userService.changePassword(user.getUid(), user.getUsername(), oldPassword, newPassword);
        // 返回成功
        return new JsonResult<User>(OK);
    }

    /**
     * 获取当前登录的用户的信息
     *
     * @param ticket 用户登陆cookie
     * @return 当前登录的用户的信息
     */
    @GetMapping("get_info")
    public JsonResult<User> getInfo(@CookieValue("userTicket") String ticket) {
        User userCache = (User) redisTemplate.opsForValue().get("user:" + ticket);
        User infoByUId = userService.getInfoByUId(userCache.getUid());
        return new JsonResult<User>(OK, infoByUId);
    }

    /**
     * 修改当前登录的用户的信息
     *
     * @param ticket 用户登陆cookie
     * @return void
     */
    @RequestMapping("/change_info")
    public JsonResult<Void> changeInfo(User user, @CookieValue("userTicket") String ticket) {
        User result = (User) redisTemplate.opsForValue().get("user:" + ticket);
        Integer uid = result.getUid();
        userService.changeInfo(uid, user);
        return new JsonResult<Void>(OK);
    }

    /**
     * 根据cookie获取用户用于验证是否登陆
     * @param userTicket 用户ticket
     */
    private User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response){
        if (StringUtils.isEmpty(userTicket)) {
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:" + userTicket);
        return user;
    }


    /**
     * -------------------- 服务接口 --------------------
     */



    /**
     * 测试用接口
     */
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
    @GetMapping("userinfo")
    public String hello( @CookieValue("userTicket") String ticket) {
        return ticket;
    }
}
