package pl.mg.search.stock.domain.fake;

import net.datafaker.Faker;
import org.springframework.stereotype.Component;
import pl.mg.search.stock.domain.StockProduct;
import pl.mg.search.stock.domain.StockProductRepository;
import pl.mg.search.stock.domain.StockProductTranslation;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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
                Set<StockProductTranslation> translations = new HashSet<>();
                translations.add(
                        new StockProductTranslation(i + 1L, i + 1L, "de", "de_" + faker.commerce().productName(),
                                "de_" + faker.commerce().material()));
                translations.add(new StockProductTranslation(i + 60000 + 1L, i + 1L, "en",
                        "en" + faker.commerce().productName(),
                        "en_" + faker.commerce().material()));
                repo.save(
                        new StockProduct(i + 1L, i + 1L,
                                BigDecimal.valueOf(Double.parseDouble(faker.commerce().price())),
                                faker.commerce().brand(), translations));
            }
        }
    }
}

