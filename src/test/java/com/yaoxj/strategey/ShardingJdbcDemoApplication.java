package com.yaoxj.strategey;

import com.yaoxj.shardingjdbc.mapper.CourseMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-12-01 14:51
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingJdbcDemoApplication {

    @Autowired
    private CourseMapper courseMapper;

    @Test
    public void contextLoads(){
//        courseMapper.selectById();
    }
}
