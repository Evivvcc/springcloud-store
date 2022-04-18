package com.evivv.store.controller;

import com.evivv.store.entity.User;
import com.evivv.store.service.IUserService;
import com.evivv.store.util.CookieUtil;
import com.evivv.store.util.JsonResult;
import com.evivv.store.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.security.krb5.internal.Ticket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.nio.channels.ReadPendingException;

@RestController // @Controller + @ResponseBody
@RequestMapping("/users")
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
        // 生成cookie，存入redis
        String ticket = UUIDUtil.uuid();
        redisTemplate.opsForValue().set("user:" + ticket, user);

//        request.getSession().setAttribute(ticket, user);
        CookieUtil.setCookie(request, response, "userTicker", ticket);
//        System.out.println(request.getSession().getAttribute("ticket"));

        return new JsonResult<User>(OK, user);
    }


    @RequestMapping("/change_password")
    public JsonResult<User> changePassword(String oldPassword, String newPassword, HttpSession session) {
//        // 调用session.getAttribute("")获取uid和username
//        Integer uid = (Integer) session.getAttribute("uid");
//        String username = (String) session.getAttribute("username");
//        // 调用业务对象执行修改密码
//        userService.changePassword(uid, username, oldPassword, newPassword);
//        // 返回成功
        return new JsonResult<User>(OK);
    }

    @GetMapping("/get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        // 从HttpSession对象中获取uid
        // 调用业务对象执行获取数据
        // 响应成功和数据
        Integer uid = (Integer) session.getAttribute("uid");
        User data = userService.getByUid(uid);
        return new JsonResult<User>(OK, data);
    }

    @RequestMapping("/change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session) {
        // 从HttpSession对象中获取uid和username
        // 调用业务对象执行修改用户资料
        // 响应成功
        Integer uid = (Integer) session.getAttribute("uid");
        String username = (String) session.getAttribute("username");
        userService.changeInfo(uid, username, user);
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

}
