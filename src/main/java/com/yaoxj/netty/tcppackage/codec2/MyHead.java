package com.yaoxj.netty.tcppackage.codec2;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-03-19 14:39
 **/
public class MyHead {
    //数据长度
    private int length;

    //数据版本
    private int version;


    public MyHead(int length, int version) {
        this.length = length;
        this.version = version;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
