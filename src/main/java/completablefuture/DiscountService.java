package completablefuture;

import java.util.List;
import java.util.stream.IntStream;

public class DiscountService {
    private static final int SHOP_COUNT = 10;
    private static final List<Shop> SHOPS = IntStream.rangeClosed(1, SHOP_COUNT)
            .mapToObj(i -> new Shop("Shop" + i))
            .toList();

    public static void main(String[] args) {
        long start = System.nanoTime();
        System.out.println(findPrices("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    public static List<String> findPrices(String product) {
        // element1: 할인 전 가격 얻기 -> 파싱 -> 할인 적용 가격 얻기 -> element2: 할인 전 가격 얻기 -> 파싱 -> 할인 적용 가격 얻기 -> ...
        return SHOPS.stream()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .toList();
    }
}
