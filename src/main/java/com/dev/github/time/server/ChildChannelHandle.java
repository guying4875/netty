package com.dev.github.time.server;/**
 * @description
 * @author zhhy
 * @date 2018-11-18-11-16 下午6:09
 */

import com.dev.github.time.server.handler.TimeServiceHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 *
 * @description
 * @author zhhy
 * @date 2018-11-18-11-16 下午6:09
 *
 */
public class ChildChannelHandle extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new LineBasedFrameDecoder(1024));
        p.addLast(new StringDecoder());
        p.addLast(new LoggingHandler(LogLevel.DEBUG));
        p.addLast(new TimeServiceHandler());
    }
}
