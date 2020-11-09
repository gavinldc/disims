package com.dc.common.devops;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by lvdanchen on 20/9/15.
 */
public class IsolationContext {


    private static Set<String> islationList = new ConcurrentSkipListSet<String>();

    private static Map<String,List<String>> geyDeployList = new ConcurrentHashMap<>();

    public static void addIslation(String ... hostPort){
        for(String host:hostPort){
            islationList.add(host);
        }
    }

    public static void remove(String ... hostPort){
        for(String host:hostPort){
            islationList.remove(host);
        }
    }

    public static boolean contains(String host){
        return islationList.contains(host);
    }

    synchronized
    public static void addGreyDeploy(String uri,List<String> hosts){
        Optional.of(hosts).ifPresent(v->{
            List<String> tempList = geyDeployList.get(uri);
            if(tempList==null){
                tempList = new ArrayList<>();
            }
            tempList.addAll(v);
            geyDeployList.put(uri,tempList);
        });
    }

    synchronized
    public static void removeGreyDeploy(String uri){
        geyDeployList.remove(uri);
    }

}
