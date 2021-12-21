package com.yaoxj.entity;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-11-24 11:26
 **/
public class Json2EntityMain {
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content", "测试内容");
        jsonObject.put("topic", "测试主题");
        String text = "{\"content\":\"测试内容\",\"topic\":\"测试主题\"}";
//        JSONObject jsonObject= JSONObject.parseObject(text);
        Content chuPinDian = JSONObject.toJavaObject(jsonObject, Content.class);
        System.out.println(chuPinDian);
    }
}
