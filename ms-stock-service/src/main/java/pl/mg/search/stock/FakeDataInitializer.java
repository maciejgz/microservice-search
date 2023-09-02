package pl.mg.search.stock;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.mg.search.stock.domain.fake.FakeDataGenerator;

@Component
public class FakeDataInitializer {

    private final FakeDataGenerator generator;

    public FakeDataInitializer(FakeDataGenerator generator) {
        this.generator = generator;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeFakeData() {
        generator.initializeFakeData();
    }
}
