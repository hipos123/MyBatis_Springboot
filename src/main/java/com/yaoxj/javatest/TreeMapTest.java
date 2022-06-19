package com.yaoxj.javatest;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yaoxj.util.JsonUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TreeMapTest {
    public static void main(String[] args) {
        TreeMap<String,Object> treeMap = new TreeMap<String,Object>();

        System.out.println("初始化后,TreeMap元素个数为：" + treeMap.size());

//新增元素:

        treeMap.put("hello",1);

        treeMap.put("world",2);

        treeMap.put("my",3);
        treeMap.put("yaoxj",null);

        treeMap.put("name",4);

        treeMap.put("is",5);

        treeMap.put("huangqiuping",6);

        treeMap.put("i",6);

        treeMap.put("am",6);

        treeMap.put("a",6);

        treeMap.put("developer",6);

        System.out.println("添加元素后,TreeMap元素个数为：" + treeMap.size());

//遍历元素：

/*        Set<Map.Entry<String,Object>> entrySet = treeMap.entrySet();

        for(Map.Entry<String,Object> entry : entrySet){

            String key = entry.getKey();

            Object value = entry.getValue();

            System.out.println("TreeMap元素的key:"+key+",value:"+value);

        }*/
        String s="{\"sex\":\"man\",\"name\":\"yaoxj\",\"addr\":\"\",\"age\":12,\"work\":null}";
        JSONObject jsonObject2 = JSON.parseObject(s);
        System.out.println("jsonObject2===="+jsonObject2.toJSONString());

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("name","yaoxj");
        jsonObject.put("sex","man");
        jsonObject.put("age",12);
        jsonObject.put("addr","");
        jsonObject.put("work",null);
        System.out.println(jsonObject.toJSONString());
        TreeMap treeMap2 = JSON.parseObject(jsonObject.toJSONString(), TreeMap.class);
//        log.info("TreeMap:{}", treeMap2);
        System.out.println("tttmap===="+treeMap2);

        Set<Map.Entry<String,Object>> entrySet = treeMap2.entrySet();

//        for(Map.Entry<String,Object> entry : entrySet){
//
//            String key = entry.getKey();
//
//            Object value = entry.getValue();
//
//            System.out.println("TreeMap元素的key:"+key+",value:"+value);
//
//        }

        final JSONObject data = new JSONObject();
        // 客户ID对应及其对应的秘钥
        data.put("appid", "comm-adapter");
        data.put("secret", "232iadaoaduoiadalksjliao");
//        data.put("clientId",clientId);
        // 转为JSON字符串后Base64加密
        String encodeTokenParam = Base64.encode(data.toJSONString());

        TreeMap<String, Object> signData = new TreeMap<>();
        signData.put("clientId","comm-adapter");
        signData.put("data", encodeTokenParam);
        signData.put("timestamp", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        signData.put("version", "1.0.0");
        String s1 = JSON.toJSONString(signData);
        System.out.println(s1);
    }
}
