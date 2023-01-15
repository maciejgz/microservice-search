package pl.mg.search.cms;

import com.github.javafaker.Faker;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.mg.search.cms.domain.CmsProduct;
import pl.mg.search.cms.domain.CmsProductRepository;

@Component
public class FakeDataInitializer {

    private final CmsProductRepository repo;

    public FakeDataInitializer(CmsProductRepository repo) {
        this.repo = repo;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeFakeData() {
        Faker faker = new Faker();
        if (repo.count() == 0) {
            for (int i = 0; i < 30000; i++) {
                repo.save(new CmsProduct(faker.number().randomNumber(), faker.book().title(),
                        faker.elderScrolls().quote()));
            }
        }
    }

}
