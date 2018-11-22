package com.dev.github.time.server.handler;/**
 * @description
 * @author zhhy
 * @date 2018-11-18-11-6 下午6:17
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @description
 * @author zhhy
 * @date 2018-11-18-11-6 下午6:17
 *
 */
public class TimeServiceHandler extends ChannelInboundHandlerAdapter {

    private String req;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("channel 正在读取数据。。。");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
        String body = (String) msg;//new String(byteArray,"utf-8").substring(0,byteArray.length-System.getProperty("line.separator").length());
        System.out.println("接受内容为："+body);

        String currentTime = body.equalsIgnoreCase("time")?sdf.format(new Date()):"false";
        req = currentTime+System.getProperty("line.separator");
        System.out.println("返回内容："+currentTime);
        ByteBuf buf = Unpooled.copiedBuffer(req.getBytes());
        ctx.writeAndFlush(buf);
    }



    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("channel 读取数据完成");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        System.out.println("channel 读取数据异常");
        cause.printStackTrace();
        ctx.close();
    }
}
