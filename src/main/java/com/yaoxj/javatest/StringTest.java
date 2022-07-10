package com.yaoxj.javatest;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.yaoxj.entity.UserEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StringTest {
    public static void main(String[] args) {
//        String fullTypeName="标准件与通用配件-其他通用备件-管路卡具";
        String fullTypeName="标准件与通用配件-其他通用备件";
//        fullTypeName="标准件与通用配件";
        String substring=fullTypeName;
        if(fullTypeName.lastIndexOf("-")>0){
             substring = fullTypeName.substring(0, fullTypeName.lastIndexOf("-"));
        }
        System.out.println(substring);

        System.out.println(JSONObject.toJSONString(null));
        String ids="111,22,333,444,555,666";
        List<UserEntity> userEntityList= Lists.newArrayList();
        String[] strings = Convert.toStrArray(ids);
        List<UserEntity> collect = Arrays.stream(strings).map(s -> {
            UserEntity userEntity = new UserEntity();
            userEntity.setUserName(s);
            userEntity.setUserCode(s+"2222");
            return userEntity;
        }).collect(Collectors.toList());

        collect.stream().forEach(userEntity -> {
            System.out.println(userEntity.getUserName());
            userEntityList.add(userEntity);
        });

        UserEntity userEntity = new UserEntity();
        userEntity.setUserName("yaoxj");
        userEntity.setUserCode("eeeee");
        System.out.println(JSONObject.toJSONString(userEntity));

//        userEntityList.stream().forEach(s -> {
//            System.out.println(s.getUserName()+"*****"+s.getUserCode());
////            userEntityList.add(userEntity);
//        });

        List<UserEntity> collect1 = userEntityList.stream().map(s ->  {
                s.setPhonenumber("16666282171");
                return s;
        }).collect(Collectors.toList());

        collect1.stream().forEach(s -> {
            System.out.println(s.getUserName()+"*****"+s.getUserCode()+"ssss"+s.getPhonenumber());
//            userEntityList.add(userEntity);
        });


        Long storeMaterialsSysIdTemp=Long.valueOf("1533642995253161986");
        System.out.println(storeMaterialsSysIdTemp);

        String ss="parent=12&test=12";
        String jsonStr2 ="{"+ss.replace("=",":").replace("&",",")+"}";
//                String jsonStr2 = '{"' + ss.replace(/ /g, '", "').replace(/=/g, '": "') + '"}';
        JSONObject json1 = JSONObject.parseObject(jsonStr2);
        System.out.println(json1);
//        Object parse = JSON.parse(ss);
//        Map<String,JSONObject> map1 = (Map)JSON.parse(conResult);
        //通过key=result,key=name，获取name对应的value值
//        JSONObject json2 = map1.get("result");
    }
}
