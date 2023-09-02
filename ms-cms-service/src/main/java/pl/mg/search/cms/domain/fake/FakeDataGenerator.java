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

    private final CmsProductRepository cmsProductRepo;
    private final Faker faker = new Faker();

    public FakeDataGenerator(CmsProductRepository cmsProductRepo) {
        this.cmsProductRepo = cmsProductRepo;
    }

    public void initializeFakeData() {
        if (cmsProductRepo.count() == 0) {
            for (int i = 0; i < 30000; i++) {
                Set<CmsProductTranslation> translations = new HashSet<>();
                CmsProduct cmsProduct = new CmsProduct(i + 1L, i + 1L, faker.commerce().productName(),
                        faker.commerce().material(), translations);
                translations.add(new CmsProductTranslation(i + 1L, cmsProduct, "de", "de_" + faker.commerce().productName(),
                        "de_" + faker.commerce().material()));
                translations.add(new CmsProductTranslation(i + 60000 + 1L, cmsProduct, "en",
                        "en" + faker.commerce().productName(),
                        "en_" + faker.commerce().material()));
                cmsProductRepo.save(cmsProduct);
            }
        }

        //faster fake data generator directly in database
        /*
        insert into search_table(description, "language", title)
	SELECT md5(random()::text), 'pl', md5(random()::text) FROM generate_series(1, 10000000) s(i);


insert into search_table(description, "language", title)
	SELECT md5(random()::text), 'de', md5(random()::text) FROM generate_series(1, 10000000) s(i);

insert into search_table(description, "language", title)
	SELECT md5(random()::text), 'es', md5(random()::text) FROM generate_series(1, 10000000) s(i);

insert into search_table(description, "language", title)
	SELECT md5(random()::text), 'en', md5(random()::text) FROM generate_series(1, 10000000) s(i);

insert into search_table(description, "language", title)
	SELECT md5(random()::text), 'de', md5(random()::text) FROM generate_series(1, 10000000) s(i);
         */
    }
}

