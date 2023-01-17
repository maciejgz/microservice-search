package pl.mg.search.stock;

import net.datafaker.Faker;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.mg.search.stock.domain.StockProduct;
import pl.mg.search.stock.domain.StockProductRepository;

import java.math.BigDecimal;

@Component
public class FakeDataInitializer {

    private final StockProductRepository repo;

    public FakeDataInitializer(StockProductRepository repo) {
        this.repo = repo;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeFakeData() {
        Faker faker = new Faker();
        if (repo.count() == 0) {
            for (int i = 0; i < 30000; i++) {
                repo.save(
                        new StockProduct(i + 1, i + 1, BigDecimal.valueOf(Double.parseDouble(faker.commerce().price())),
                                faker.commerce().brand()));
            }
        }
    }

}
