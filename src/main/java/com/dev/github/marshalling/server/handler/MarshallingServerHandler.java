package com.dev.github.marshalling.server.handler;/**
 * @description
 * @author zhhy
 * @date 2018-11-18-11-19 上午10:20
 */

import com.dev.github.SubscribeReq;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * @description
 * @author zhhy
 * @date 2018-11-18-11-19 上午10:20
 *
 */
public class MarshallingServerHandler  extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReq req = (SubscribeReq) msg;

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }
}
