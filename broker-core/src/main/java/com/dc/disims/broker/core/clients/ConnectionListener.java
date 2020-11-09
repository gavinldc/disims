package com.dc.disims.broker.core.clients;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lvdanchen on 20/9/17.
 */
public class ConnectionListener implements ChannelFutureListener {
    private LogicClient client;

    private AtomicInteger maxConnect = new AtomicInteger(1);

    public ConnectionListener(LogicClient client) {
        this.client = client;
    }

    public void operationComplete(ChannelFuture future) throws Exception {
        if (!future.isSuccess()&&maxConnect.get()<=5) {

            System.out.println("Reconnect");
            maxConnect.getAndSet(1);

            final EventLoop loop = future.channel().eventLoop();

            loop.schedule(new Runnable() {

                public void run() {

                    client.connect(new Bootstrap(), loop);

                }

            }, 1L, TimeUnit.SECONDS);

        }else if(maxConnect.get()>5){
            maxConnect.set(1);
            if(!future.isSuccess()){
                client.setState(LogicClient.ClientStateEnum.CLOSE);
            }
        }

    }
}
