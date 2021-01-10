package com.yaoxj.nio;

import java.nio.ByteBuffer;

public class BufferReadOnly {
    public static void main(String[] args) {
        ByteBuffer allocate = ByteBuffer.allocate(64);
        for (int i = 0; i <64 ; i++) {
            allocate.put((byte) i);
        }

        allocate.flip();
        ByteBuffer byteBuffer = allocate.asReadOnlyBuffer();
        while (byteBuffer.hasRemaining()){
            System.out.println(byteBuffer.get());
        }
        //java.nio.ReadOnlyBufferException 转为只读buffer之后，就不可以王里面放内容了，否则就会报错
        byteBuffer.put((byte) 2);

    }
}
