package com.dev.github.marshalling.server;/**
 * @description
 * @author zhhy
 * @date 2018-11-18-11-19 上午9:35
 */

import com.dev.github.marshalling.MarshallingCodeFactor;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 *
 * @description
 * @author zhhy
 * @date 2018-11-18-11-19 上午9:35
 *
 */
public class MarshallingServer  {
    private static int port = 8007;
    public static void main(String[] args){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(MarshallingCodeFactor.buildMarshallingDecoder());
                            pipeline.addLast(MarshallingCodeFactor.buildMarshallingEncoder());

                        }
                    });

            //绑定端口号
            ChannelFuture f = b.bind(port).sync();
            //等待服务端端口关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
