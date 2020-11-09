package com.dc.disims.boss.support;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by lvdanchen on 20/9/15.
 */
public class ClientHolder {

    private static Map<String,Channel> pool = new ConcurrentHashMap<>();

    public static void add(Channel channel){
        pool.put(channel.remoteAddress().toString(),channel);
    }

    public static Channel getCLient(String address){
        return pool.get(address);
    }

}
