package pl.mg.search.cms;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.mg.search.cms.domain.fake.FakeDataGenerator;

@Component
public class FakeDataInitializer {

    private final FakeDataGenerator generator;

    public FakeDataInitializer(FakeDataGenerator generator) {
        this.generator = generator;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeFakeData() {
        this.generator.initializeFakeData();
    }

}
