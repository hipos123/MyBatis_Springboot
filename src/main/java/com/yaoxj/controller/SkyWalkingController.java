package com.yaoxj.controller;

import com.agent.GetNumber;
import com.demo.starter.service.DemoService;
import com.github.pagehelper.PageHelper;
import com.yaoxj.entity.UserEntity;
import com.yaoxj.service.BizService;
import com.yaoxj.service.UserService;
import com.yaoxj.skywalking.CompletableFutureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
public class SkyWalkingController {

    @Autowired
    private  CompletableFutureService completableFutureService;

    @GetMapping(value = "/apm")
    public String apmTest() throws Exception {
        System.out.println(GetNumber.getNumber());
        return completableFutureService.queryGoodsDetailWithCatalogInfo();
    }




}
