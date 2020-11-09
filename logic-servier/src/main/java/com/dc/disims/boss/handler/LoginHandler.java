package com.dc.disims.boss.handler;

import com.dc.common.util.Convert;
import com.dc.common.util.Strings;
import com.dc.disims.boss.support.ClientHolder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by lvdanchen on 20/9/14.
 */
public class LoginHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        String loginJson = Convert.parseString(msg);
        if(Strings.isNullOrEmpty(loginJson)){
            return;
        }




        //login checking
        if(true){
            ClientHolder.add(ctx.channel());
            ctx.pipeline().remove(this);
            ctx.pipeline().addLast(new NettyServerHandler());
        }

        super.channelRead(ctx, msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
