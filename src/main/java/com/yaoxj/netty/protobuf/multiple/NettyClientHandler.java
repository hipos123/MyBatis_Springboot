package com.yaoxj.netty.protobuf.multiple;

import com.yaoxj.netty.protobuf.simple.StudentPOJO;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NettyClientHandler  extends SimpleChannelInboundHandler<DataInfoPOJO.DataPackage> {
    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//一秒执行一次
        executor.scheduleAtFixedRate(() -> {
            //随机生成 0 或者 1
            int packType = new Random().nextInt(2);

            switch (DataInfoPOJO.DataPackage.PackageType.forNumber(packType)) {
                case STUDENT:
                    System.out.println("发送student");
                    DataInfoPOJO.DataPackage dataPackage = DataInfoPOJO.DataPackage.newBuilder()
                            .setPackageType(DataInfoPOJO.DataPackage.PackageType.STUDENT)
                            .setSudent(DataInfoPOJO.Student.newBuilder()
                                    .setName("LeoLee").setAge(25).setAddress("上海").build()).build();
                    ctx.channel().writeAndFlush(dataPackage);
                    break;
                case DOG:
                    System.out.println("发送dog");
                    DataInfoPOJO.DataPackage dataPackage2 = DataInfoPOJO.DataPackage.newBuilder()
                            .setPackageType(DataInfoPOJO.DataPackage.PackageType.DOG)
                            .setDog(DataInfoPOJO.Dog.newBuilder()
                                    .setDogName("恶霸犬").setDogAge(3).build()).build();
                    ctx.channel().writeAndFlush(dataPackage2);
                    break;
            }


        }, 0, 1, TimeUnit.SECONDS);

    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfoPOJO.DataPackage dataPackage) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        System.out.println("出现了异常----》"+cause.getMessage());
        ctx.close();

    }
}
