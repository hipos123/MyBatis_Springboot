package com.yaoxj.autowire;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述：
 *
 * @Author shf
 * @Date 2019/5/7 22:08
 * @Version V1.0
 **/
@ConfigurationProperties(prefix = "spring.enableauoconfig")
public class MyImportProperties {
    private String val;

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
