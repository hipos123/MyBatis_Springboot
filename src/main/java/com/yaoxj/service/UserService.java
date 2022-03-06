package com.yaoxj.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaoxj.dao.UserMapper;
import com.yaoxj.entity.UserEntity;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserService {

    @Autowired(required = false)
    private UserMapper mapper;

    public List<UserEntity> queryList() {
        List<UserEntity> userList = mapper.queryList();
        return userList;
    }

    public UserEntity findById(long userId) {
        System.out.println("userId:" + userId);
        return mapper.findById(userId);
    }

    public int insertEntity() {
        UserEntity entity = new UserEntity();
        entity.setUserName("lisi2");
        entity.setUserCode("lisi2" + new Date());
        entity.setNickName("郭靖2");
        entity.setUserPwd("1232");
        entity.setCreateDate(new Date());
        entity.setUpdateDate(new Date());
        return mapper.insertEntity(entity);
    }

    public int insertParam() {
        return mapper.insertParam("linzhiqiang", "lzq");
    }

    @Transactional
    public int insertByMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("nickName", "zhaotong"+ new Date());
        map.put("userCode", "zt"+ new Date());
        return mapper.insertByMap(map);
    }

    @Transactional
    public int updateEntity() {
        UserEntity entity = new UserEntity();
        entity.setUserId(1);
        entity.setNickName("郭靖4567");
        int i = mapper.updateEntity(entity);

//        entity=new UserEntity();
//        entity.setUserId(1);
//        entity.setNickName("郭靖777777");
//        i = mapper.updateEntity(entity);
        i = 10 / 0;
        return i;
    }


    private int updateEntity2() {
        UserEntity entity = new UserEntity();
        entity.setUserId(1);
        entity.setNickName("郭靖890");
        int i = mapper.updateEntity(entity);
        i = 10 / 0;
        return i;
    }

    public int deleteEntity() {
        UserEntity entity = new UserEntity();
        entity.setUserId(11);
        return mapper.deleteEntity(entity);
    }

    //    @Transactional
    public void doTranscationTest() {
        UserEntity entity = new UserEntity();
        entity.setUserName("lisi333");
        entity.setUserCode("lisi33" + new Date());
        entity.setNickName("郭靖333");
        entity.setUserPwd("1232");
        entity.setCreateDate(new Date());
        entity.setUpdateDate(new Date());
        mapper.insertEntity(entity);
        this.updateEntity();
    }

    @Transactional
    public void doTranscationTest2() {
        UserEntity entity = new UserEntity();
        entity.setUserName("lisi44");
        entity.setUserCode("lisi4" + new Date());
        entity.setNickName("郭靖44");
        entity.setUserPwd("1232");
        entity.setCreateDate(new Date());
        entity.setUpdateDate(new Date());
        mapper.insertEntity(entity);
        this.updateEntity2();
    }

    public void get(Integer uid) throws Exception {
        check(uid);
        service(uid);
        redis(uid);
        mysql(uid);
    }

    public void service(Integer uid) throws Exception {
        int count = 0;
        for (int i = 0; i < 10; i++) {
            count += i;
        }
        log.info("service  end {}", count);
    }

    public void redis(Integer uid) throws Exception {
        log.info("redis begin");
        int count = 0;
        for (int i = 0; i < 1000; i++) {
            Thread.sleep(10);
            count += i;
        }
        log.info("redis  end {}", count);
    }

    public void mysql(Integer uid) throws Exception {
        log.info("mysql begin");
        long count = 0;
        for (int i = 0; i < 1000; i++) {
            count += i;
        }
        log.info("mysql end {}", count);
    }

    public boolean check(Integer uid) throws Exception {
        if (uid == null || uid < 0) {
            log.error("uid不正确，uid:{}", uid);
            throw new Exception("uid不正确");
        }
        return true;
    }

}
