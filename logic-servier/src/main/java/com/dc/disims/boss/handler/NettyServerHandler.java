package com.dc.disims.boss.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter{

	/**
	 * on client connecting
	 */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	

    	
        log.info("Channel active......，ip:{}",ctx.channel().remoteAddress());
    }

    /**
     * on cilent sending message
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("服务器收到消息: {}", msg.toString());
        //ctx.write("你也好哦");
        ctx.flush();
    }

    /**
     * on sending message to client error
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

	
}
