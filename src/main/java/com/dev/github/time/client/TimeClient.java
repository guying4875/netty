package com.dev.github.time.client;/**
 * @description
 * @author zhhy
 * @date 2018-11-18-11-7 下午4:17
 */

import com.dev.github.time.client.handler.TimeClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author zhhy
 * @description
 * @date 2018-11-18-11-7 下午4:17
 */
public class TimeClient {
    public static void main(String[] args) throws Exception {
        // Configure SSL.git


        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new LineBasedFrameDecoder(1024));
                            p.addLast(new StringDecoder());
                            p.addLast(new LoggingHandler(LogLevel.DEBUG));
                            p.addLast(new TimeClientHandler());
                        }
                    });

            // Start the client.
            ChannelFuture f = b.connect("127.0.0.1", 8007).sync();

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }
}
