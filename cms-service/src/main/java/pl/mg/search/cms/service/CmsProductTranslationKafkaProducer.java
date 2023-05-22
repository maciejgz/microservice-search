package pl.mg.search.cms.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pl.mg.search.cms.config.KafkaTopicConfig;

import java.util.UUID;

@Service
@Slf4j
public class CmsProductTranslationKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CmsProductTranslationKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProductTranslation(Object message) {
        log.debug("sending product translation='{}'", message);
        kafkaTemplate.send(KafkaTopicConfig.TOPIC_PRODUCT_TRANSLATIONS, UUID.randomUUID().toString(), message);
    }
}
