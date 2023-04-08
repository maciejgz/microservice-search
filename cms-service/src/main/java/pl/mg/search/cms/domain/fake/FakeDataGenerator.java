package pl.mg.search.cms.domain.fake;

import net.datafaker.Faker;
import org.springframework.stereotype.Component;
import pl.mg.search.cms.domain.CmsProduct;
import pl.mg.search.cms.domain.CmsProductRepository;

@Component
public class FakeDataGenerator {

    private final CmsProductRepository repo;
    private final Faker faker = new Faker();

    public FakeDataGenerator(CmsProductRepository repo) {
        this.repo = repo;
    }

    public void initializeFakeData() {
        if (repo.count() == 0) {
            for (int i = 0; i < 30000; i++) {
                repo.save(new CmsProduct(i + 1L, i + 1L, faker.commerce().productName(),
                        faker.commerce().material()));
            }
        }
    }
}

