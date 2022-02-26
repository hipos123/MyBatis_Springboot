package com.yaoxj.controller;


import com.github.pagehelper.PageHelper;
import com.yaoxj.entity.UserEntity;
import com.yaoxj.service.BizService;
import com.yaoxj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;


@Slf4j
@RestController
public class MyController {
	@Autowired
	private UserService userService;


    @RequestMapping("/orderList")
	public List<UserEntity> orderList(){
        log.info("获取用户列表数据");
		PageHelper.startPage(1, 2);
		log.info("我在dev做了修改");
		return userService.queryList();
	}

	public UserEntity getUserId(){
		UserEntity byId = userService.findById(1);
		return byId;
	}



}
