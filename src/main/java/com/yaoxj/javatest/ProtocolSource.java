package com.yaoxj.javatest;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @Author: bssc
 * @CreateTime: 2022-11-02
 * @Description: ProtocolSource
 * @Version: 1.0
 */

public enum ProtocolSource {
    DZZTB("01", "电子招投标"),
    XBJ("02", "询比价"),
    JJ("03", "竞价"),
    CS("04", "磋商"),
    DYLY("05", "单一来源"),
    XYGX("06", "协议共享"),
    QT("99", "其他");

    @EnumValue
    @JsonValue
    private String value;
    private String name;

    private ProtocolSource(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    @JsonCreator
    public static ProtocolSource fromValue(String value) {
        ProtocolSource[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ProtocolSource born = var1[var3];
            if (born.getValue().equals(value)) {
                return born;
            }
        }

        return null;
    }

    public String toString() {
        return this.value;
    }
}
