package com.yaoxj.service;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Service;

@Service("bb")
public class BizService implements BeanNameAware {
    private String beanName;

    public void setBeanName(String s) {
        this.beanName = s;
    }

    public String getBeanName() {
        return beanName;
    }
}
