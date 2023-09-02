package pl.mg.search.stock.integration.cms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.stereotype.Component;
import pl.mg.search.commons.domain.CmsProductTranslationDTO;
import pl.mg.search.stock.domain.StockProductService;

@Component
@Slf4j
public class CmsProductTranslationListener {

    public static final String TOPIC_PRODUCT_TRANSLATIONS = "product_translations";
    public static final String GROUP_ID_PRODUCT_TRANSLATIONS = "stock-service";

    private final StockProductService stockProductService;

    public CmsProductTranslationListener(StockProductService stockProductService) {
        this.stockProductService = stockProductService;
    }

    @KafkaListener(topics = TOPIC_PRODUCT_TRANSLATIONS, groupId = GROUP_ID_PRODUCT_TRANSLATIONS, errorHandler = "cmsProductErrorHandler")
    public void listenGroupFoo(CmsProductTranslationDTO message) {
        log.debug("Received Message in group {}: {}", GROUP_ID_PRODUCT_TRANSLATIONS, message);
        stockProductService.addProductTranslation(message);
    }

}
