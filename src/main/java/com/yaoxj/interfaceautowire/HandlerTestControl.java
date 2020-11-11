package com.yaoxj.interfaceautowire;

import com.github.pagehelper.PageHelper;
import com.yaoxj.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: yaoxj
 * @create: 2020-10-16 17:34
 **/
@RestController
@Slf4j
public class HandlerTestControl {
    @Autowired
    Map<String,MyInterface> myInterfaceMap;

    @RequestMapping("/handler")
    public void queryList(){
        MyInterface zhifubao = myInterfaceMap.get("zhifubao");
        zhifubao.getName();
    }
}
