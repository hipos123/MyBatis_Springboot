package com.yaoxj.redis.globalid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-10-25 16:29
 **/
@Component
public class IdGeneratorService {

/*    INCR 命令主要有以下2个特征：
    Redis的INCR命令具备了“INCR AND GET”的原子操作
    Redis是单进程单线程架构，INCR命令不会出现ID重复
    基于以上2个特性，可以采用INCR命令来实现分布式全局ID生成。*/

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private static final String ID_KEY = "id:generator:product";
    public Long incrementId() {
            //delta  设置递增因子
           return stringRedisTemplate.opsForValue().increment(ID_KEY,1);
    }
}
