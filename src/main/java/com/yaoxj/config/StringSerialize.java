package com.yaoxj.config;

import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * @Author: bssc
 * @CreateTime: 2022-11-21
 * @Description: LongDoubleSerialize
 * @Version: 1.0
 */
@Component
public class StringSerialize extends JsonSerializer<Object> {

    @Override
    public void serialize(Object object, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        System.out.println("dadadada:"+object.toString());
        if(object == null||ObjectUtil.isEmpty(object)) {
            gen.writeString("我是空的lllllll");
        }else{
            gen.writeString((String) object);
        }

    }
}
