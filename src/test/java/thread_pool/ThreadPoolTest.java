package thread_pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;

public class ThreadPoolTest {
    @Test
    void newSingleThreadExecutor() {
        try (ExecutorService executorService = Executors.newSingleThreadExecutor()) {
            for (int i = 0; i < 5; i++) {
                executorService.submit(() -> print("Hello, world!"));
            }
        }
    }

    @Test
    void newFixedThreadPool() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(5)) {
            for (int i = 0; i < 10; i++) {
                Future<String> future = executorService.submit(() -> {
                    Thread.sleep(1000);
                    return "[" + Thread.currentThread().getName() + "] Hello, world!";
                });
                System.out.println(future.get());
                System.out.println("After future.get()");
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void newCachedThreadPool() {
        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            for (int i = 0; i < 100; i++) {
                executorService.submit(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("[" + Thread.currentThread().getName() + "] Hello, world!");
                });
            }
        }
    }

    void print(String message) {
        System.out.printf("[%s] %s%n", Thread.currentThread().getName(), message);
    }
}
