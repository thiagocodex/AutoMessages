package me.thiagocodex.automessages;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class FutureRequest implements Callable<Void> {
    static final ExecutorService executorService = Executors.newFixedThreadPool(1);

    void getNewsMessage() {
        Future<Void> future = executorService.submit(this);
        try {
            future.get(3, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    @Override
    public Void call() {
        News.request();
        return null;
    }
}
