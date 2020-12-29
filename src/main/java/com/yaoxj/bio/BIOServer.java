package com.yaoxj.bio;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {



    public static void main(String[] args) {

        ExecutorService threadPool= Executors.newCachedThreadPool();

        try {
            ServerSocket serverSocket =new ServerSocket(6666);
            System.out.println("socker服务端已经启动了，但是还没客户端连接进来======"+Thread.currentThread().getName());

            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("线程阻塞,有客户端接进来，才会下来,Thread=="+Thread.currentThread().getId()+"&&&name==="+Thread.currentThread().getName());
                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        dataHandler(socket);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }

    }

    private static void  dataHandler(Socket socket) {
        System.out.println("dataHandler,Thread=="+Thread.currentThread().getId()+"&&&name==="+Thread.currentThread().getName());
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            while (true){
                int length = inputStream.read(bytes);
                System.out.println("读取阻塞，读完之后才下来,Thread=="+Thread.currentThread().getId()+"&&&name==="+Thread.currentThread().getName());
                if(length!=-1){
                    System.out.println(new String(bytes,0,length));
                }else{
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
