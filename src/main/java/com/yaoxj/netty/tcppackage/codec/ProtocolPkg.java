package com.yaoxj.netty.tcppackage.codec;

import lombok.Data;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-03-18 15:22
 **/
@Data
public class ProtocolPkg {
    private int length;
    private String content;
}
