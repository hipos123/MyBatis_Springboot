package com.yaoxj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: bssc
 * @CreateTime: 2022-12-07
 * @Description: TestExceptionController
 * @Version: 1.0
 */
@RestController
@RequestMapping("/v1/test")
@Slf4j
public class TestExceptionController {
    @GetMapping("/err")
    public ResponseEntity<String> errTest() {
        int a = 1 / 0;
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
