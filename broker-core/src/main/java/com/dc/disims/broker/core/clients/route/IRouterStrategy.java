package com.dc.disims.broker.core.clients.route;

import com.dc.disims.broker.core.clients.ClientFactory;
import com.dc.disims.broker.core.clients.LogicClient;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by lvdanchen on 20/9/18.
 */
public interface IRouterStrategy {

    public LogicClient routeClient(ClientFactory factory);




}
