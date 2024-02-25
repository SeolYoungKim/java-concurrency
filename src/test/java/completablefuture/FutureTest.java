package completablefuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;

public class FutureTest {
    @Test
    void future() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(5)) {
            Future<String> future = executorService.submit(() -> {
                Thread.sleep(1000);
                return "[" + Thread.currentThread().getName() + "] Hello, world!";
            });

            String result = future.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
