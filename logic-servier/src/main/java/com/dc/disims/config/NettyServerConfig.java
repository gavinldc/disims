package com.dc.disims.config;

import com.dc.common.boot.BootStrap;
import com.dc.disims.boss.handler.LoginHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyServerConfig {

    @Value("${netty.server.port}")
    private int port;
    @Value("${netty.server.address}")
    private String address;

    @Bean(destroyMethod="shutdown")
    public BootStrap bootStrap(){
        BootStrap boot = new BootStrap(port,address);
        boot.setChannelInitializer(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
		        ch.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
		        ch.pipeline().addLast(new LoginHandler());
            }
        });
        boot.start();
        return boot;
    }
}
