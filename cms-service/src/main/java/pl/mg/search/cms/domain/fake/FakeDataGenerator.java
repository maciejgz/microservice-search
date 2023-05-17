package pl.mg.search.cms.domain.fake;

import net.datafaker.Faker;
import org.springframework.stereotype.Component;
import pl.mg.search.cms.domain.CmsProduct;
import pl.mg.search.cms.domain.CmsProductRepository;
import pl.mg.search.cms.domain.CmsProductTranslation;

import java.util.HashSet;
import java.util.Set;

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
                Set<CmsProductTranslation> translations = new HashSet<>();
                translations.add(new CmsProductTranslation(i + 1L, i + 1L, "de", "de_" + faker.commerce().productName(),
                        "de_" + faker.commerce().material()));
                translations.add(new CmsProductTranslation(i + 60000 + 1L, i + 1L, "en",
                        "en" + faker.commerce().productName(),
                        "en_" + faker.commerce().material()));
                CmsProduct cmsProduct = new CmsProduct(i + 1L, i + 1L, faker.commerce().productName(),
                        faker.commerce().material(), translations);
                repo.save(cmsProduct);
            }
        }
    }
}

