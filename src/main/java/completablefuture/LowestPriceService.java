package completablefuture;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LowestPriceService {
    private static final List<Shop> SHOPS = List.of(
            new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll")
    );

    public static void main(String[] args) {
        long start = System.nanoTime();
        System.out.println(findPrices("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    private static List<String> findPrices(String product) {
//        return SHOPS.stream()
//                .map(shop ->
//                        CompletableFuture.supplyAsync(
//                                () -> {
//                                    System.out.println("[" + Thread.currentThread().getName() + "] search price of '" + shop.getName() + "'");
//                                    return String.format("%s price is %.2f", shop.getName(), shop.getPrice(product));
//                                }
//                        )
//                )
//                .map(cp -> {
//                    System.out.println("[" + Thread.currentThread().getName() + "] join");
//                    String result = cp.join();
//                    System.out.println("[" + Thread.currentThread().getName() + "] joined. result = " + result);
//                    return result;
//                })
//                .toList();

        List<CompletableFuture<String>> findPrices = SHOPS.stream()
                .map(shop ->
                        CompletableFuture.supplyAsync(  // 여기서는 main 스레드는 참여하지 않음
                                () -> {
                                    System.out.println("[" + Thread.currentThread().getName() + "] search price of '" + shop.getName() + "'");
                                    return String.format("%s price is %.2f", shop.getName(), shop.getPrice(product));
                                }
                        )
                )
                .toList();

        return findPrices.stream()
                .map(cp -> {
                    System.out.println("[" + Thread.currentThread().getName() + "] join");
                    String result = cp.join();
                    System.out.println("[" + Thread.currentThread().getName() + "] joined. result = " + result);
                    return result;
                })
                .toList();
    }
}
