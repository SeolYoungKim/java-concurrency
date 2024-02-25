package completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompletableFutureTest {
    @DisplayName("get()에서 대기하므로 블로킹이 발생하여 자원이 낭비된다.")
    @Test
    void completableFutureGet() throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        int x = 1337;

        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        threadPool.submit(() -> completableFuture.complete(f(x)));

        int b = g(x);
        System.out.println(completableFuture.get() + b);

        threadPool.shutdown();
    }

    @DisplayName("thenCombine()을 이용할 수 있다")
    @Test
    void completableFutureThenCombine() throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        int x = 1337;

        CompletableFuture<Integer> a = new CompletableFuture<>();
        CompletableFuture<Integer> b = new CompletableFuture<>();
        CompletableFuture<Integer> c = a.thenCombine(b, (y, z) -> y + z);

        threadPool.submit(() -> a.complete(f(x)));
        threadPool.submit(() -> b.complete(g(x)));

        System.out.println(c.get());
        threadPool.shutdown();
    }

    private Integer f(int x) {
        return x + 10;
    }

    private int g(int x) {
        return x - 10;
    }
}
