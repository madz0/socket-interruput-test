package com.github.madz0;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.*;

import static org.junit.Assert.*;

public class ClientTest {

    @Test
    public void sendAndWait() throws IOException, InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<?> future = executorService.submit(() -> {
            try (Client client = new Client("127.0.0.1", 4040)) {
                client.sendAndWait();
                countDownLatch.countDown();
            } catch (IOException e) {
                e.printStackTrace();
                countDownLatch.countDown();
            }
        });
        try {
            future.get(5, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
            future.cancel(true);
            executorService.shutdownNow();
        }
        countDownLatch.await();
    }
}