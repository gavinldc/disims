package com.dc.ims.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lvdanchen on 2020/10/29.
 * @description 用户接口
 */
@RequestMapping("/api/user/")
@RestController
public class UserController {


    /**
     * 添加用户
     * @param userId
     * @param username
     * @return
     */
    @PostMapping(path = "save")
    public String addUser(@RequestParam String userId, @RequestParam String username){
       return "success";
    }
}
