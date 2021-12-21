package com.yaoxj.rocketmq.orderdemo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-12-13 15:35
 **/
@Data
@AllArgsConstructor
public class Order implements Serializable {
    private int id;
    private String desc;
}
