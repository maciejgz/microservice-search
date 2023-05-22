package pl.mg.search.cms.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import pl.mg.search.cms.domain.CmsProduct;
import pl.mg.search.cms.domain.CmsProductRepository;
import pl.mg.search.cms.domain.CmsProductTranslation;

import java.util.Random;

@Component
public class CmsProductEventListener implements ApplicationListener<CmsProductCreatedEvent> {

    private final CmsProductRepository repository;

    public CmsProductEventListener(CmsProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onApplicationEvent(CmsProductCreatedEvent event) {
        System.out.println("Received spring custom event - " + event.getMessage());

        CmsProduct cmsProduct = new CmsProduct();
        cmsProduct.setTitle(event.getMessage());
        cmsProduct.setDescription("test");
        cmsProduct.setStockProductId(1L);
        cmsProduct.setId(new Random(1000000L).nextLong());

        CmsProductTranslation translation = new CmsProductTranslation();
        translation.setLanguage("EN");
        translation.setTitle("test");
        translation.setDescription("test");
        translation.setCmsProductId(1L);
        cmsProduct.addTranslation(translation);
        CmsProduct save = this.repository.save(cmsProduct);
        System.out.println(save);
    }

}
