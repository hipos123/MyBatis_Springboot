package com.yaoxj.javatest;

import java.util.Date;
import java.util.TimeZone;

/**
 * @description:
 * @author: yaoxj
 * @create: 2020-12-09 16:11
 **/
public class TimeZoneTest {
    public static void main(String[] args) {
        System.out.println("原时间 " + new Date());
        System.out.println("默认时区：" + TimeZone.getDefault().getID());
        TimeZone time = TimeZone.getTimeZone("Asia/Shanghai");

//        TimeZone time = TimeZone.getTimeZone("ETC/GMT-8");

        TimeZone.setDefault(time);

        System.out.println("修改后时间 " + new Date());
    }
}
