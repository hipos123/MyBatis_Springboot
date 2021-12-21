package com.yaoxj.shardingjdbc.entity;

import lombok.Data;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-12-01 14:46
 **/
@Data
public class Course {
    private Long cid;
    private String cname;
    private Long userId;
    private String cstatus;
}
