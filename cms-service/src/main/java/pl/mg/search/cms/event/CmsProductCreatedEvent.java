package pl.mg.search.cms.event;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Slf4j
public class CmsProductCreatedEvent extends ApplicationEvent {

    @Getter
    private final String message;

    public CmsProductCreatedEvent(Object source) {
        super(source);
        this.message = (String) source;
        log.debug("event");
    }

    public CmsProductCreatedEvent(Object source, Clock clock) {
        super(source, clock);
        this.message = (String) source;
    }
}
