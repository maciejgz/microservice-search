package pl.mg.search.cms.domain;

import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import pl.mg.search.cms.service.CmsProductTranslationKafkaProducer;
import pl.mg.search.commons.domain.CmsProductTranslationDTO;

@Slf4j
public class CmsProductTranslationListener {

    static private CmsProductTranslationKafkaProducer producer;

    @Autowired
    public void init(CmsProductTranslationKafkaProducer producer) {
        CmsProductTranslationListener.producer = producer;
    }

    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdate(CmsProductTranslation translation) {
//        log.debug("beforeAnyUpdate");
    }

    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyUpdate(CmsProductTranslation translation) {
//        log.debug("afterAnyUpdate");
        CmsProductTranslationDTO dto = CmsProductTranslationMapper.toDto(translation);
        producer.sendProductTranslation(dto);
    }

    @PostLoad
    private void afterLoad(CmsProductTranslation translation) {
//        log.debug("afterLoad");
    }

}
