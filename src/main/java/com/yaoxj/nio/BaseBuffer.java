package com.yaoxj.nio;

import java.nio.Buffer;
import java.nio.IntBuffer;

public class BaseBuffer {
    public static void main(String[] args) {
        //  final int[] hb; buffer 实际上就是一个数组，每个buffer都有这样子一个变量，就是用来存放数据
        //    private int mark = -1;
        //    private int position = 0; //位置
        //    private int limit;
        //    private int capacity; //容量
        IntBuffer intBuffer = IntBuffer.allocate(5);
//        intBuffer.put(0);
        for (int i = 0; i <5 ; i++) {
            intBuffer.put(i*2);
        }
        //intBuffer 有值 0，2，4，6，8
        //flip 读写反转
        intBuffer.flip();

        //将buffer的位置指到第二个位置上，从第二个位置开始读取数据
        intBuffer.position(2);

        while(intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}
