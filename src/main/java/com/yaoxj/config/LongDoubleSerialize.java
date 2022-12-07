package com.yaoxj.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public class LongDoubleSerialize extends JsonSerializer<Double> {

    private DecimalFormat df = new DecimalFormat("##.00");

    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        if(value != null) {
            gen.writeString(df.format(value));
        }
    }
}
