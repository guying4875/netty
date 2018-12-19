package com.dev.github.learn.nio;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @author zhy
 * @desc 采用java实现reactor
 * @create 2018-12-19 20:09
 **/
public class ReactorServer {

    public static void main(String[] ages){
        ReactorServer rs = new ReactorServer();
        rs.init(8081,1);
    }


    public void init(int prot,int id) {
        try {
            // 创建通道,并设置非阻塞
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            // 创建选择器，并为通道绑定感兴趣的事件
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, (SelectionKey.OP_ACCEPT)).attach("主监听通道" + id);
            System.out.println("监听的channel Id：" + serverSocketChannel.hashCode());
            // 通道绑定端口号
            InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", prot);
            serverSocketChannel.socket().bind(inetSocketAddress);
            //开始监听服务
            listen( selector, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listen(Selector selector,int id){
        try{
            // 开始轮询通道事件
            while (true) {
                // 可以是阻塞，非阻塞，也可以设置超时
                int readyChannels = selector.select();
                if (readyChannels <=0){
                    Thread.sleep(100);
                    continue;
                }
                if (readyChannels > 0) {
                    Set<SelectionKey> readyKeys = selector.selectedKeys();
                    Iterator iterator = readyKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey readyKey = (SelectionKey) iterator.next();

                        if (readyKey.isAcceptable()) {
                            ServerSocketChannel readyChannel = (ServerSocketChannel) readyKey.channel();
                            System.out.println("可接受连接的channel Id:" + readyChannel.hashCode() + readyKey.attachment());
                            SocketChannel socketChannel1 = (SocketChannel) readyChannel.accept().configureBlocking(false);
                            System.out.println("接受连接后返回的channel Id" + socketChannel1.hashCode());
                            socketChannel1.register(selector, (SelectionKey.OP_READ | SelectionKey.OP_WRITE)).attach(id);

                        }

                        if (readyKey.isWritable()) {
                            SocketChannel readyChannel = (SocketChannel) readyKey.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(512);
                            String msg = "to " + readyKey.attachment() + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n";
                            System.out.println("writ 事件："+readyKey.attachment() + " 写出内容" + msg+" "+id);
                            buffer.put(msg.getBytes());
                            buffer.flip();
                            readyChannel.write(buffer);
                        }

                        if (readyKey.isReadable()) {
                            SocketChannel readyChannel = (SocketChannel) readyKey.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(512);
                            readyChannel.read(buffer);
                            System.out.println("read 事件："+readyKey.attachment() + " 读取到内容" + getString(buffer)+"  "+id);
                        }

                        if (readyKey.isConnectable()) {
                            System.out.println("Connectable 事件："+readyKey.attachment() + " 建立连接" );
                        }

                        if (!readyKey.isValid()){
                            System.out.println("Connectable 事件："+readyKey.attachment() + " 关闭连接" );
                        }
                        readyKey.attach(id++);
                        iterator.remove();   //要清除该key否则下次依然会被监听
                    }
                }

                Thread.sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * ByteBuffer 转换 String
     *
     * @param buffer
     * @return
     */
    public static String getString(ByteBuffer buffer) {
        String string = "";
        try {
            for (int i = 0; i < buffer.position(); i++) {
                string += (char) buffer.get(i);
            }
            return string;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

}
