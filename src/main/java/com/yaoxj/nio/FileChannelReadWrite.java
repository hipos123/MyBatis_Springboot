package com.yaoxj.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelReadWrite {
    //读到文件内容，写入到另一个文件中，只使用一个buffer
    public static void main(String[] args) throws IOException {
        File file=new File("F://filechannel.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();

        File file2=new File("F://filechannel2.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();

        ByteBuffer allocate = ByteBuffer.allocate((int) file.length());

        while(true){
            allocate.clear();
            int read = fileInputStreamChannel.read(allocate);
            if(read == -1){
                break;
            }
            allocate.flip();
            fileOutputStreamChannel.write(allocate);
        }
        fileInputStream.close();
        fileOutputStream.close();


    }
}
