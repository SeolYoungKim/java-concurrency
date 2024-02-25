package completablefuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class DiscountService {
    private static final int SHOP_COUNT = 10;
    private static final List<Shop> SHOPS = IntStream.rangeClosed(1, SHOP_COUNT)
            .mapToObj(i -> new Shop("Shop" + i))
            .toList();
    private static final Executor THREAD_POOL = Executors.newFixedThreadPool(Math.min(SHOP_COUNT, 100), r -> {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    });

    public static void main(String[] args) {
        long start = System.nanoTime();
        System.out.println(findPrices("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    public static List<String> findPrices(String product) {
        List<CompletableFuture<String>> futurePrices = SHOPS.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), THREAD_POOL))
                .map(futurePrice -> futurePrice.thenApply(Quote::parse))
                .map(futureQuote -> futureQuote.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), THREAD_POOL)))
                .toList();

        return futurePrices.stream()
                .map(CompletableFuture::join)
                .toList();
    }
}
