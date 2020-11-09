package com.dc.disims.broker.core.clients.route;

import com.dc.disims.broker.core.clients.ClientFactory;
import com.dc.disims.broker.core.clients.LogicClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by lvdanchen on 20/9/18.
 */
public class SimpleRouter implements IRouterStrategy{
    @Override
    public LogicClient routeClient(ClientFactory factory) {
        List<LogicClient> tempList = new ArrayList<>();
        for(LogicClient lc:factory.getClients()){
            if(lc.isReady()&&(lc.getBusyEndTime()==null)||lc.getBusyEndTime()<System.currentTimeMillis()){
                tempList.add(lc);
            }
        }
        if(tempList.size()>0){
            int i = ThreadLocalRandom.current().nextInt(tempList.size());
            LogicClient temp = tempList.get(i);
            tempList = null;
            return temp;
        }
        return null;
    }
}
