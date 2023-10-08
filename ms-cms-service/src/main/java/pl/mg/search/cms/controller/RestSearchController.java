package pl.mg.search.cms.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.mg.search.cms.domain.CmsProduct;
import pl.mg.search.cms.domain.CmsProductRepository;
import pl.mg.search.cms.domain.CmsProductTranslation;
import pl.mg.search.cms.event.CmsProductCreatedEvent;
import pl.mg.search.cms.event.CmsProductEventListener;

import java.util.Random;


/**
 * Standard Spring Rest controller
 */
@RestController
@RequestMapping(value = {"/api/v1/cmsProduct", "/api/v2/cmsProduct"})
@Slf4j
@AllArgsConstructor
public class RestSearchController {

    private final CmsProductRepository repository;

    private final CmsProductEventListener eventListener;

    @GetMapping("/filter")
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_REALM_ADMIN')")
    public ResponseEntity<Page<CmsProduct>> filter(
            Pageable page,
            @RequestParam(name = "q") String query,
            Authentication authentication
    ) {
        log.debug("filterAdmin");
        Page<CmsProduct> products = repository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query, page);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/filterUser")
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_REALM_USER')")
    public ResponseEntity<Page<CmsProduct>> filterUser(
            Pageable page,
            @RequestParam(name = "q") String query,
            Authentication authentication
    ) {
        log.debug("filterUser");
        Page<CmsProduct> products = repository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query, page);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/random")
    @jakarta.transaction.Transactional
    public ResponseEntity<?> random() {
        CmsProduct cmsProduct = new CmsProduct();
        cmsProduct.setTitle("random_product");
        cmsProduct.setDescription("test");
        cmsProduct.setStockProductId(74L);
        long id = new Random().nextLong(1000000L);
        cmsProduct.setId(id);

        CmsProductTranslation translation = new CmsProductTranslation();
        translation.setLanguage("EN");
        translation.setTitle("test");
        translation.setDescription("test");
        translation.setProduct(cmsProduct);
        cmsProduct.addTranslation(translation);
        CmsProduct save = this.repository.save(cmsProduct);
        return ResponseEntity.ok(save.getTitle());
    }

    @GetMapping("/event")
    @jakarta.transaction.Transactional
    public ResponseEntity<?> event() {
        this.eventListener.onApplicationEvent(new CmsProductCreatedEvent("testttttt"));
        return ResponseEntity.ok(null);
    }

}
