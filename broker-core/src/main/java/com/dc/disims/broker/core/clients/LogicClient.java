package com.dc.disims.broker.core.clients;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by lvdanchen on 20/9/17.
 */
@Slf4j
public class LogicClient {

    @Getter
    private String ip;

    private int port;

    private String user;

    private String pwd;

    private ChannelFuture channelFuture;

    private EventLoopGroup group;

    private Channel channel;

    private ClientStateEnum state = ClientStateEnum.CONNECTING;

    @Getter
    @Setter
    private Long busyEndTime;


    public LogicClient(String ip,int port,String user,String pwd){
        this.ip=ip;
        this.port=port;
        this.user=user;
        this.pwd=pwd;
    }

    public void close(){
        if(channelFuture!=null){
            try {
                channelFuture.channel().closeFuture().sync();
                if(group!=null){
                    group.shutdownGracefully();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void registerChannel(Channel channel){
        this.channel=channel;
    }

    public void setState(ClientStateEnum state){
        this.state=state;
    }

    public boolean isReady(){
        return ClientStateEnum.READY==state;
    }

    public boolean isClose(){
        return ClientStateEnum.CLOSE==state;
    }

    public ChannelFuture connect() throws Exception {
        // 配置客户端NIO线程组
        group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        return connect(b, group);
    }

    public ChannelFuture connect(Bootstrap b, EventLoopGroup group) {
        try {
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4));
                            ch.pipeline().addLast(new LogicClientHandler(LogicClient.this));
                        }
                    });

            // 发起异步连接操作
            ChannelFuture f = b.connect(this.ip,this.port).addListener(new ConnectionListener(this)).sync();
            if (f.isSuccess()) {
                log.info("连接成功======port:" + ip + "===host:" + port);
            }
            // 当代客户端链路关闭
            // f.channel().closeFuture().sync();
            return f;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // 优雅退出，释放NIO线程组
            // group.shutdownGracefully();
        }
        return null;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof LogicClient){
            return ((LogicClient)obj).getIp().equals(this.getIp());
        }
        return false;
    }

    @Override
    public int hashCode(){
        return this.ip.hashCode();
    }


    static public enum ClientStateEnum{
        CONNECTING,READY,CLOSE
    }
}
