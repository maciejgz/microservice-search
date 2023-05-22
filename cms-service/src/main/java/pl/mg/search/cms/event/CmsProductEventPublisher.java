package pl.mg.search.cms.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CmsProductEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishCustomEvent(final String message) {
        System.out.println("Publishing custom event. ");
        CmsProductCreatedEvent customSpringEvent = new CmsProductCreatedEvent(message);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }
}
