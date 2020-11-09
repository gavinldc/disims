package com.dc.disims.broker.core.clients;

import com.dc.disims.broker.core.clients.route.IRouterStrategy;
import lombok.*;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lvdanchen on 20/9/17.
 */
@RequiredArgsConstructor
public class ClientFactory {

    @NonNull
    private SpringClientFactory factory;
    @NonNull
    private String servicName;
    @NonNull
    private int port;
    @Setter
    private IRouterStrategy router;

    @Getter
    private Set<LogicClient> clients = new LinkedHashSet<>();

    private Timer timer = new Timer();

    public void init(){
       getServerAddress().forEach(s->{
           Optional.of(loadClient(s,port,"","")).ifPresent(l->{
                   clients.add(l);
           });
       });

       //start server list monitor
       timer.schedule(new TimerTask() {
           @Override
           public void run() {
               getServerAddress().forEach(s->{
                   Optional.of(loadClient(s,port,"","")).ifPresent(l->{
                       clients.add(l);
                   });
               });
           }
       },300000l,600000l);
    }

    private LogicClient loadClient(String ip,int port,String user,String pwd){
        LogicClient client = null;
        for(LogicClient lc:clients){
            if(lc.getIp().equals(ip)){
                client=lc;
                break;
            }
        }
        if(client!=null&&client.isReady()){
            return client;
        }else if(client==null){
            client = new LogicClient(ip, port, user, pwd);
        }else if(client.isClose()){
            client.setState(LogicClient.ClientStateEnum.CONNECTING);
        }else{
            return client;
        }
        try {
            client.connect();
            return client;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<String> getServerAddress(){
        return factory.getLoadBalancer(servicName).getReachableServers().stream().map(ll->{return ll.getHost();}).collect(Collectors.toList());
    }


    public void destory(){
        if(clients!=null&&clients.size()>0){
            clients.forEach(ll->{
                ll.close();
            });
        }
    }

    public LogicClient routeClient(){
        return router.routeClient(this);
    }

}
