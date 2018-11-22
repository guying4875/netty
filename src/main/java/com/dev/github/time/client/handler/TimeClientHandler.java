package com.dev.github.time.client.handler;/**
 * @description
 * @author zhhy
 * @date 2018-11-18-11-7 下午4:49
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author zhhy
 * @description
 * @date 2018-11-18-11-7 下午4:49
 */
public class TimeClientHandler extends ChannelInboundHandlerAdapter {

    private String sss;
    public TimeClientHandler() {
        sss = "time"+System.getProperty("line.separator");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("channel active");
        ByteBuf firstMessage = null;
        byte[] sByte = null;

        for (int  i=1;i<200;i++){
            sByte = sss.getBytes();
            firstMessage = Unpooled.buffer(sByte.length);
            firstMessage.writeBytes(sByte);
            System.out.println("发送内容"+sss);
            ctx.writeAndFlush(firstMessage);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("channel 正在读取数据。。。");
        String mm = (String) msg;
        System.out.println("接收内容："+mm);
//        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("channel readComplete");
//        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
