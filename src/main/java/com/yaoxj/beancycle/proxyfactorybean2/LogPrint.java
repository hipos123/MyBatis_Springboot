package com.yaoxj.beancycle.proxyfactorybean2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-08-17 15:50
 **/
@Slf4j
@Component
public class LogPrint implements IPrint {
    @Override
    public void print(String msg) {
        log.info("log print: {}", msg);
    }

    @Override
    public boolean verify(Integer condition) {
        return condition > 0;
    }
}
