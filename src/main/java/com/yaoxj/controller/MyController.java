package com.yaoxj.controller;


import com.github.pagehelper.PageHelper;
import com.yaoxj.entity.UserEntity;
import com.yaoxj.service.BizService;
import com.yaoxj.service.UserService;
import com.yaoxj.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
public class MyController {
	@Autowired
	private UserService userService;


    @RequestMapping("/orderList")
	public List<UserEntity> orderList(){
        log.info("获取用户列表数据");
		PageHelper.startPage(1, 2);
		log.info("我在master分支下做了修改");
		return userService.queryList();
	}

	@RequestMapping("/getUserId")
	public UserEntity getUserId() throws Exception {
		UserEntity byId = userService.findById(1);
		Map map=new HashMap();
		map.put("username","1111");
		String s = HttpUtil.httpPost("http://localhost:8083/insertByMap",map);
		return byId;
	}

	@RequestMapping("/getUserId2")
	public UserEntity getUserId2() throws Exception {
		UserEntity byId = userService.findById(1);
		Map map=new HashMap();
		map.put("username","1111");
		String s = HttpUtil.httpPost("http://localhost:8083/insertByMap2",map);
		return byId;
	}



}
