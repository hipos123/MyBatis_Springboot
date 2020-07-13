package com.joy.controller;

import java.util.List;

import com.joy.service.BizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.joy.entity.UserEntity;
import com.joy.service.UserService;

@RestController
@Slf4j
public class UserController {
	@Autowired
	private UserService userService;
    @Autowired
    private BizService bb;

    @RequestMapping("/userlist")
	public List<UserEntity> queryList(){
        log.info("获取用户列表数据");
		PageHelper.startPage(1, 2);
		return userService.queryList();
	}

    @RequestMapping("/queryUser")
    public UserEntity queryUserEntity(long userId){
        UserEntity userEntity=userService.findById(userId);
        return userEntity;
    }
    @RequestMapping("/getBeanId")
    public String getBeanField(){
        return bb.getBeanName();
    }

    @RequestMapping("/insert")
	public int insertEntity() {
        return userService.insertEntity();
	}

    @RequestMapping("/insertParam")
    public int insertParam() {
        return userService.insertParam();
    }

    @RequestMapping("/insertByMap")
    public int insertByMap() {
        return userService.insertByMap();
    }

    @RequestMapping("/updateEntity")
    public int updateEntity() {
        return userService.updateEntity();
    }

    @RequestMapping("/deleteEntity")
    public int deleteEntity() {
        return userService.deleteEntity();
    }
}
