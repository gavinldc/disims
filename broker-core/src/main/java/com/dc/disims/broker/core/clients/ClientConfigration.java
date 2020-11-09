package com.dc.disims.broker.core.clients;

import com.dc.disims.broker.core.clients.route.SimpleRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lvdanchen on 20/9/17.
 */
@Configuration
public class ClientConfigration {


    @Autowired
    private SpringClientFactory factory;

    @Value("${socket.server.port}")
    private int port;

    @Value("${socket.server.service}")
    private String serviceName;

    @Value("${socket.server.user}")
    private String user;
    @Value("${socket.server.pwd}")
    private String pwd;

    @Bean(initMethod = "init",destroyMethod = "destroy")
    public ClientFactory clientFactory(){
        ClientFactory clientFactory = new ClientFactory(factory,serviceName,port);
        clientFactory.setRouter(new SimpleRouter());
        return clientFactory;
    }


}
