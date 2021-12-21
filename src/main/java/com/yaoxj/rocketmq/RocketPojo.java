package com.yaoxj.rocketmq;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-12-13 11:03
 **/
@Data
public class RocketPojo implements Serializable {
    private String name;
    private int age;
}
