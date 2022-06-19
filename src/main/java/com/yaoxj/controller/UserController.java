package com.yaoxj.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.demo.starter.service.DemoService;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.yaoxj.service.BizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.yaoxj.entity.UserEntity;
import com.yaoxj.service.UserService;

import javax.annotation.Resource;
import javax.naming.Name;

@RestController
@Slf4j
public class UserController {
	@Autowired
	private UserService userService;
    @Autowired
    private BizService bb;


//    test.val=012333
//    @Value("test.val")
//    int val;
    //自定义starter
    @Resource(name = "demo")
    private DemoService demoService;

    @RequestMapping("/demostarter")
    public String demostarter(){
        String say = demoService.say();
        return say;
    }

    @RequestMapping("/userlist")
	public List<UserEntity> queryList(){
//        log.info("获取用户列表数据"+val);
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
    public int insertParam() throws InterruptedException {
        Thread.sleep(100);
        return userService.insertParam();
    }

    @RequestMapping("/insertByMap")
    public int insertByMap() throws InterruptedException {
        Thread.sleep(1000);
        return userService.insertByMap();
    }
    @RequestMapping("/insertByMap2")
    public int insertByMap2() throws InterruptedException {
        Thread.sleep(2000);

        return userService.insertByMap();
    }

    @RequestMapping("/insertBatch")
    public boolean insertBatch() throws InterruptedException {
//        Thread.sleep(2000);

        List<UserEntity> list= Lists.newArrayList();
        for (int i=0;i<10000;i++){
            UserEntity userEntity=new UserEntity();
//            userEntity.setUserCode("mycode"+i);
            userEntity.setUserName("myname"+i);
            userEntity.setNickName("nickname"+i);
            list.add(userEntity);
        }
        Stopwatch stopwatch = Stopwatch.createStarted();
        boolean b = userService.saveBatch(list);
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));;
        return b;
    }

    @RequestMapping("/insertBatch2")
    public void insertBatch2() throws InterruptedException {
//        Thread.sleep(2000);

        List<UserEntity> list= Lists.newArrayList();
        for (int i=0;i<10000;i++){
            UserEntity userEntity=new UserEntity();
//            userEntity.setUserCode("mycode"+i);
            userEntity.setUserName("myname"+i);
            userEntity.setNickName("nickname"+i);
            list.add(userEntity);
        }
        Stopwatch stopwatch = Stopwatch.createStarted();
        userService.batchInsert(list,50);
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));;
    }


    @RequestMapping("/updateEntity")
    public int updateEntity() throws InterruptedException {
        return userService.updateEntity();
    }

    @RequestMapping("/deleteEntity")
    public int deleteEntity() {
        return userService.deleteEntity();
    }

    @RequestMapping("/doTransTest")
    public void doTransTest() throws InterruptedException {
        userService.doTranscationTest();
    }

    @RequestMapping("/doTransTest2")
    public void doTransTest2() {
        userService.doTranscationTest2();
    }


}
