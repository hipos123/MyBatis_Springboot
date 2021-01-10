package com.yaoxj.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MapByteBuffer {
    public static void main(String[] args) throws IOException {
//        FileInputStream fileInputStream = new FileInputStream("F://filechannel.txt");
        RandomAccessFile rw = new RandomAccessFile("F://filechannel.txt", "rw");
        FileChannel fileInputStreamChannel = rw.getChannel();

        /**
         * 1,FileChannel.MapMode.READ_WRITE 使用读写模式
         * 2，可以直接修改的起始位置
         * 3，映射到内存的大小，不是索引大小
         *
         */
        MappedByteBuffer map = fileInputStreamChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        map.put(0, (byte) 'Y');
        map.put(3, (byte) 't');
        map.put(4, (byte) 'g');
        rw.close();
        fileInputStreamChannel.close();
    }
}
