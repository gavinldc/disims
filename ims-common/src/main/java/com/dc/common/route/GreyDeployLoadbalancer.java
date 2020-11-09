package com.dc.common.route;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.Server;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvdanchen on 20/9/15.
 */
@Slf4j
public class GreyDeployLoadbalancer extends RandomRule{

    @Override
    public Server choose(ILoadBalancer lb, Object key) {

        List<Server> useServers = new ArrayList<>();
        //先判定隔离
        List<Server> servers = lb.getAllServers();
        lb.getReachableServers().get(0).getScheme();

        //
       log.info("object key:{}",key);



        return super.choose(lb, key);
    }

    @Override
    protected int chooseRandomInt(int serverCount) {
        return super.chooseRandomInt(serverCount);
    }

    @Override
    public Server choose(Object key) {
        return super.choose(key);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        super.initWithNiwsConfig(clientConfig);
    }
}
