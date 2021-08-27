package com.yaoxj.strategey;

import cn.hutool.core.lang.Validator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-08-24 15:26
 **/
public class ItemRecommendReflectFactoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemRecommendReflectFactoryTest.class);
    @Autowired
    private GetPointAdvertisePageReflectFactory getItemRecommendStrategey;

    @Test
    public void  refectFactoryTest(){
        String path = "/itemrecommend.properties";
        GetPointAdvertisePageStrategey getPointAdvertisePageStrategey = getItemRecommendStrategey.getAdvertisePageStrategey(path,"pdp.item.advertise");
        if(Validator.isNotNull(getPointAdvertisePageStrategey)){
            LOGGER.info("通过配置文件和反射机制，在运行时动态获取指定的执行类，测试成功");
            getPointAdvertisePageStrategey.getAdvertisePage(null);
        }
    }
}
