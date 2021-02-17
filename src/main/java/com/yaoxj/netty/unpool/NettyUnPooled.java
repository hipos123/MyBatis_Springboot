package com.yaoxj.netty.unpool;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class NettyUnPooled {
    public static void main(String[] args) {
        //创建一个ByteBuf，该对象包含一个数组arr，是一个byte[10]
        //在netty的buffer，不需要示使用flip进行反转，底层维护了readindex和writeindex
        // buffer.readByte() 使用readByte进行读取数据的时候，readindex会不断的加1

        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i <10 ; i++) {
            buffer.writeByte(i);
        }

        System.out.println("capacity===="+buffer.capacity());
        for (int i = 0; i <buffer.capacity() ; i++) {
            System.out.println(buffer.getByte(i));
//            buffer.readByte()
        }

        ByteBuf byteBufStr = Unpooled.copiedBuffer("hello yaoxj", CharsetUtil.UTF_8);
        if (byteBufStr.hasArray()){
            byte[] array = byteBufStr.array();
            System.out.println(new String(array,CharsetUtil.UTF_8));
        }
        System.out.println(byteBufStr.arrayOffset());
        System.out.println(byteBufStr.readerIndex());
        System.out.println(byteBufStr.writerIndex());
        System.out.println(byteBufStr.capacity());
        System.out.println(byteBufStr.getCharSequence(0,4,CharsetUtil.UTF_8));
        System.out.println(byteBufStr.readerIndex());


    }
}
