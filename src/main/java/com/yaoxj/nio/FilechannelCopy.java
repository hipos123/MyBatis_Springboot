package com.yaoxj.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FilechannelCopy {
    public static void main(String[] args) throws IOException {
        //文件拷贝，通过buffer
        FileInputStream sourceFile = new FileInputStream("F://filechannel.txt");
        FileChannel sourceFileChannel = sourceFile.getChannel();

        FileOutputStream destFile = new FileOutputStream("F://file22.txt");
        FileChannel destFileChannel = destFile.getChannel();
        destFileChannel.transferFrom(sourceFileChannel,0,sourceFileChannel.size());

        sourceFile.close();
        sourceFileChannel.close();
        destFile.close();
        destFileChannel.close();

    }
}
