package pl.mg.search.stock.domain.fake;

import net.datafaker.Faker;
import org.springframework.stereotype.Component;
import pl.mg.search.stock.domain.StockProduct;
import pl.mg.search.stock.domain.StockProductRepository;

import java.math.BigDecimal;

@Component
public class FakeDataGenerator {

    private final StockProductRepository repo;
    private final Faker faker = new Faker();

    public FakeDataGenerator(StockProductRepository repo) {
        this.repo = repo;
    }

    public void initializeFakeData() {
        if (repo.count() == 0) {
            for (int i = 0; i < 30000; i++) {
                repo.save(
                        new StockProduct(i + 1L, i + 1L, BigDecimal.valueOf(Double.parseDouble(faker.commerce().price())),
                                faker.commerce().brand()));
            }
        }
    }
}

