package pl.mg.search.stock;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import pl.mg.search.commons.domain.CmsProductTranslationDTO;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;
    @Value("${spring.kafka.bootstrap-servers}")
    private String brokers;

    @Bean
    public ConsumerFactory<String, CmsProductTranslationDTO> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put("auto.offset.reset", "latest");
        JsonDeserializer<CmsProductTranslationDTO> cmsProductTranslationJsonDeserializer = new JsonDeserializer<>(CmsProductTranslationDTO.class);
        cmsProductTranslationJsonDeserializer.addTrustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), cmsProductTranslationJsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CmsProductTranslationDTO> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CmsProductTranslationDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
