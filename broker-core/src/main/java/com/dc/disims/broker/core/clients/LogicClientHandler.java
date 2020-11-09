package com.dc.disims.broker.core.clients;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Created by lvdanchen on 20/9/17.
 */
@Slf4j
public class LogicClientHandler extends ChannelInboundHandlerAdapter {

    private LogicClient client;

    public LogicClientHandler(LogicClient client) {
        this.client = client;
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        final EventLoop eventLoop = ctx.channel().eventLoop();
        log.error("server disconnect=====");
        client.setState(LogicClient.ClientStateEnum.CONNECTING);
        // 重新连接服务器
        ctx.channel().eventLoop().schedule(new Runnable() {
            public void run() {
                client.connect(new Bootstrap(), eventLoop);
            }
        }, 2, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("channel active");
        client.registerChannel(ctx.channel());
        //login checking
        //send ping
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // super.channelReadComplete(ctx);
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //if(loginchecking){}
        client.setState(LogicClient.ClientStateEnum.READY);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        client.close();
    }




}
