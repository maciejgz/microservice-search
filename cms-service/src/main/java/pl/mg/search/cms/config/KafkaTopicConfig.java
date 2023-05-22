package pl.mg.search.cms.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

public class KafkaTopicConfig {

    public static final String TOPIC_PRODUCT_TRANSLATIONS = "product_translations";

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    /**
     * Create topic for product translations
     *
     * @return NewTopic
     */
    @Bean
    public NewTopic productTranslationsTopic() {
        return new NewTopic(TOPIC_PRODUCT_TRANSLATIONS, 1, (short) 1);
    }

}
