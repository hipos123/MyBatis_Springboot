package com.yaoxj.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo1 {
    public static void main(String[] args) throws IOException {
        //将字符串写入到"demo1.txt";
        String str="hello,filechannel";
        FileOutputStream fileOutputStream = new FileOutputStream("F://filechannel.txt");

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();//反转一下，将位置指向到第0个位置上
        FileChannel channel = fileOutputStream.getChannel();
        channel.write(byteBuffer);

        fileOutputStream.close();
    }
}
