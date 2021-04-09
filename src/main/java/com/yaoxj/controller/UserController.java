package com.yaoxj.controller;

import java.util.HashMap;
import java.util.List;

import com.yaoxj.service.BizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.yaoxj.entity.UserEntity;
import com.yaoxj.service.UserService;

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

    @GetMapping(value = "/arthas")
    public HashMap<String, Object> getUser(Integer uid) throws Exception {
        // 模拟用户查询
        userService.get(uid);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("name", "name" + uid);
        return hashMap;
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

    @RequestMapping("/doTransTest")
    public void doTransTest() {
        userService.doTranscationTest();
    }

    @RequestMapping("/doTransTest2")
    public void doTransTest2() {
        userService.doTranscationTest2();
    }


}
