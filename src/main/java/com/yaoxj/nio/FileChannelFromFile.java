package com.yaoxj.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelFromFile {
    public static void main(String[] args) throws IOException {
        //将文件内容读进来
        File file=new File("F://filechannel.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        ByteBuffer allocate = ByteBuffer.allocate((int) file.length());
        FileChannel channel = fileInputStream.getChannel();
        try {
            int read = channel.read(allocate);
            System.out.println(new String(allocate.array()));

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            fileInputStream.close();
        }

    }
}
