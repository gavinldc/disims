package com.dc.disims.boss;

import java.net.InetSocketAddress;

import com.dc.disims.boss.handler.LoginHandler;
import com.dc.disims.boss.handler.NettyServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BootStrap extends Thread{

	private int port;
	
	private String address;
	
	private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    //new 一个工作线程组
    private EventLoopGroup workGroup = new NioEventLoopGroup(200);
    
    private ChannelInitializer<SocketChannel> channelInitializer = null;
    
    ServerBootstrap bootstrap = null;
	
	
	public BootStrap(int port,String address) {
		this.address=address;
		this.port=port;
		 channelInitializer = new ChannelInitializer<SocketChannel>() {

			@Override
			protected void initChannel(SocketChannel socketChannel) throws Exception {

				socketChannel.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
		        socketChannel.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
		        socketChannel.pipeline().addLast(new LoginHandler());
				
			}
		};
	}
	
	public void shutdown() {
		//关闭主线程组
        bossGroup.shutdownGracefully();
        //关闭工作线程组
        workGroup.shutdownGracefully();
	}
	
	
	public void start() {
		InetSocketAddress socketAddress = new InetSocketAddress(address, port);
		 bootstrap = new ServerBootstrap()
                .group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(channelInitializer)
                .localAddress(socketAddress)
                //设置队列大小
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 两小时内没有数据的通信时,TCP会自动发送一个活动探测数据报文
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        //绑定端口,开始接收进来的连接
        try {
            ChannelFuture future = bootstrap.bind(socketAddress).sync();
            log.info("服务器启动开始监听端口: {}", socketAddress.getPort());
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //关闭主线程组
            bossGroup.shutdownGracefully();
            //关闭工作线程组
            workGroup.shutdownGracefully();
        }
		
		
		
		
	}
	
	

}
