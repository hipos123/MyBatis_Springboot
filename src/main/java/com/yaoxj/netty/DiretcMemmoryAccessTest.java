package com.yaoxj.netty;

import com.google.common.base.Stopwatch;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: yaoxj
 * @create: 2021-05-28 16:38
 **/
public class DiretcMemmoryAccessTest {
    public static void main(String[] args) {
//        heapMemmoryAccess();
        DirectMemmoryAccess();
    }

    public static void heapMemmoryAccess(){
        Stopwatch stopwatch=Stopwatch.createStarted();
        ByteBuffer buffer = ByteBuffer.allocate(160000);//
        for (int i = 0; i < 900000; i++) {
            for (int j = 0; j < 19900; j++) {
                // 数据的写入
                buffer.putInt(j);
            }
            buffer.flip();
            for (int j = 0; j < 19900; j++) {
                // 数据的读取
                buffer.get();
            }
            // 数据清理
            buffer.clear();
        }
        System.out.println(stopwatch.elapsed(TimeUnit.SECONDS));
    }

    public static void DirectMemmoryAccess(){
        Stopwatch stopwatch=Stopwatch.createStarted();
        ByteBuffer buffer = ByteBuffer.allocateDirect(160000);//直接内存
        for (int i = 0; i < 900000; i++) {
            for (int j = 0; j < 19900; j++) {
                // 数据的写入
                buffer.putInt(j);//拿的是地址访问，不要进行用户态和内核态的拷贝。直接操作。写进去，也是对引用地址写
            }
            buffer.flip();
            for (int j = 0; j < 19900; j++) {
                // 数据的读取
                buffer.get();//读出来，也是对引用地址读
            }
            // 数据清理
            buffer.clear();
        }
        System.out.println(stopwatch.elapsed(TimeUnit.SECONDS));
    }
}
