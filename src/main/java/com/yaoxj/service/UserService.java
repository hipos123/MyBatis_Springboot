package com.yaoxj.service;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yaoxj.dao.UserMapper;
import com.yaoxj.entity.UserEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
@Slf4j
public class UserService<T extends BaseMapper, E> extends ServiceImpl<UserMapper,UserEntity> {

    @Autowired(required = false)
    private UserMapper mapper;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    /**
     * 实体类对象class
     */
    private Class<E> entityClass;
    /**
     * 默认保存/修改 提交大小
     */
    private static final int DEFAULT_BATCH_SIZE = 20;

    public List<UserEntity> queryList() {
        List<UserEntity> userList = mapper.queryList();
        UserEntity userEntity=new UserEntity();
        userEntity.setUserName("张离尼");
        userEntity.setNickName("王全");
        userEntity.setUserCode("1111");
        userList.add(userEntity);
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

/*
    public int insertBatch() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("nickName", "zhaotong"+ new Date());
        map.put("userCode", "zt"+ new Date());
        return mapper.insertByMap(map);
    }
*/

    // @Transactional 加上这事务的话，需要等10秒才能写库，不加这事务的话，马上就更新了
    @Transactional
    public int updateEntity() {
//        UserEntity entity = new UserEntity();
//        entity.setUserId(1);
//        entity.setNickName("郭靖4567");
//        int i = mapper.updateEntity(entity);
        int i=updatestat();
        updatestat2();
//        entity=new UserEntity();
//        entity.setUserId(1);
//        entity.setNickName("郭靖777777");
//        i = mapper.updateEntity(entity);

//        try {
//            TimeUnit.SECONDS.sleep(10);
//            i = 10 / 0;
//        } catch (InterruptedException e) {
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            e.printStackTrace();
//        }
        return i;
    }

    private int updatestat2(){
        int  i=1;
        try {
            TimeUnit.SECONDS.sleep(10);
            i = 10 / 0;
        } catch (InterruptedException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }
        return i;
    }
    private int updatestat(){
        UserEntity entity = new UserEntity();
        entity.setUserId(1);
        entity.setNickName("郭靖4567");
        int i = mapper.updateEntity(entity);
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
    public void doTranscationTest() throws InterruptedException {
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


    /**
     * 根据id 删除
     * @param beanList :批量新增实体
     * @param size： 批量阈值
     */
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = RuntimeException.class)
    public void batchInsert(List<E> beanList, int size) {
        if (size < 1) {
            size = DEFAULT_BATCH_SIZE;
        }
        SqlSession session = null;
        try {
            session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
            BaseMapper mapper = session.getMapper(getEntityMapperClass());

            int count = 0;
            for (Object aBeanList : beanList) {
                mapper.insert(aBeanList);
                if (++count % size == 0) {
                    session.commit();
                    session.clearCache();
                }
            }
            session.commit();
            session.clearCache();
        }catch (Exception e) {
            e.printStackTrace();
            if (session != null) {
                session.rollback();
            }
            throw e;
        }finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /**
     * 通过反射获取T实际的Class对象
     * @return Class<T>
     */
    @SuppressWarnings("unchecked")
    private Class<T> getEntityMapperClass() {
        return (Class<T>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

//    /**
//     * 通过反射获取T实际的Class对象
//     * @return Class<E>
//     */
//    @SuppressWarnings("unchecked")
//     Class<E> getEntityClass() {
//        if (null == entityClass) {
//            entityClass = (Class<E>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
//        }
//        return entityClass;
//    }

    /**
     * SIMPLE 就是普通的执行器；REUSE 执行器会重用预处理语句
     * （prepared statements）； BATCH 执行器将重用语句并执行批量更新。
     * 通过反射获取T实际的Class对象
     * @return :SqlSession
     */
    private SqlSession getEntityMapper() {
        return sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.SIMPLE, true);
    }

}
