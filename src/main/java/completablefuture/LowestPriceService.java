package completablefuture;

import java.util.List;

public class LowestPriceService {
    private static final List<Shop> SHOPS = List.of(
            new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll")
    );

    public static List<String> findPrices(String product) {
        // 병렬 스트림은 내부적으로 ForkJoinPool을 사용한다. (ForkJoinPool.commonPool 사용)
        // main 스레드도 해당 작업에 참여한다.
        return SHOPS.parallelStream()
                .map(shop -> {
                    System.out.println("[" + Thread.currentThread().getName() + "] search price of '" + shop.getName() + "'");
                    return String.format("%s price is %.2f", shop.getName(), shop.getPrice(product));
                })
                .toList();
    }

    public static void main(String[] args) {
        long start = System.nanoTime();
        System.out.println(findPrices("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }
}
