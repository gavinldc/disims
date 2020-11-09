package com.dc.disims.boss.support;

import java.util.concurrent.*;

/**
 * Created by lvdanchen on 20/9/18.
 */
public class LogicWorker {

    private ExecutorService es = Executors.newFixedThreadPool(8);


    private ThreadPoolExecutor executor = new ThreadPoolExecutor(128, 128, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(102400), Executors.defaultThreadFactory(), new LogicRejectedExecutionHandler());


    public void execute(Runnable runnable){
        executor.execute(runnable);
    }
}
