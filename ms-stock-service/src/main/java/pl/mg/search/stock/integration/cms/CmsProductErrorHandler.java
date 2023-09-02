package pl.mg.search.stock.integration.cms;

import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service(value = "cmsProductErrorHandler")
public class CmsProductErrorHandler implements KafkaListenerErrorHandler {
    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
        return null;
    }

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
        return KafkaListenerErrorHandler.super.handleError(message, exception, consumer);
    }

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer, Acknowledgment ack) {
        return KafkaListenerErrorHandler.super.handleError(message, exception, consumer, ack);
    }
}
