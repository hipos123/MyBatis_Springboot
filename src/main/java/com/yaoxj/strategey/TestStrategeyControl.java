package com.yaoxj.strategey;

import cn.hutool.core.lang.Validator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-08-24 15:42
 **/
@RestController
@Slf4j
public class TestStrategeyControl {
    @Autowired
    private GetPointAdvertisePageReflectFactory getItemRecommendStrategey;

    @RequestMapping("/test/strategey")
    public String  refectFactoryTest(){
        String path = "itemrecommend";
        GetPointAdvertisePageStrategey getPointAdvertisePageStrategey =
                getItemRecommendStrategey.getAdvertisePageStrategey(path,"pdp.item.advertise");
        if(Validator.isNotNull(getPointAdvertisePageStrategey)){
            log.info("通过配置文件和反射机制，在运行时动态获取指定的执行类，测试成功");
            log.info(getPointAdvertisePageStrategey.getAdvertisePage(null));
        }
        return "success";
    }
}
