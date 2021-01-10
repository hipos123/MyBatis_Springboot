package com.yaoxj.nio;

import java.nio.ByteBuffer;

public class BufferPutAndGet {
    public static void main(String[] args) {
        ByteBuffer allocate = ByteBuffer.allocate(64);
        allocate.putInt(12);
        allocate.putLong(32);
        allocate.putShort((short) 4.2);
        allocate.putChar('中');

        allocate.flip();
        //顺序放什么类型，顺序读出来什么类型
/*        System.out.println(allocate.getInt());
        System.out.println(allocate.getLong());
        System.out.println(allocate.getShort());
        System.out.println(allocate.getChar());*/

        //如果类型不一致的情况下，可能会报BufferUnderflowException
        System.out.println(allocate.getInt());
        System.out.println(allocate.getLong());
        System.out.println(allocate.getInt());
        System.out.println(allocate.getShort());


    }
}
