package com.evivv.store.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.evivv.store.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IUserService extends IService<User> {

    // 注册
    void register(User user);



    /**
     * 用户登陆
     * @param username 用户名
     * @param password 用户密码
     * @return 当前登录的用户的信息
     */
    User login(String username, String password);



    /**
     * 修改用户的密码
     * @param uid 当前登录的用户的id
     * @return 当前登录的用户的信息
     */
    void changePassword(Integer uid, String username, String oldPassword, String newPassword);


    /**
     * 获取当前登录的用户的信息
     * @param uid 当前登录的用户的id
     * @return 当前登录的用户的信息
     */
//    User getByUid(Integer uid);

    /**
     * 修改用户资料
     * @param uid 当前登录的用户的id
     * @param user 用户的新的数据
     */
    void changeInfo(Integer uid, User user);



}
