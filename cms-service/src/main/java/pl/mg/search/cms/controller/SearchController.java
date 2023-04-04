package pl.mg.search.cms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.mg.search.cms.domain.CmsProduct;
import pl.mg.search.cms.domain.CmsProductRepository;

@RepositoryRestController
@RequiredArgsConstructor
public class SearchController {

    protected static final String DESCRIPTION_LABEL = "description";
    protected static final String TITLE_LABEL = "title";
    protected static final String STOCK_PRODUCT_ID_LABEL = "stockProductId";
    private final CmsProductRepository repository;

    @GetMapping("/cmsProduct/filter")
    public ResponseEntity<?> filter(
            CmsProduct product,
            Pageable page,
            PagedResourcesAssembler assembler,
            PersistentEntityResourceAssembler entityAssembler,
            @RequestParam(name = "q") String query
    ) {
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withIgnoreCase()
                .withMatcher(DESCRIPTION_LABEL, ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher(TITLE_LABEL, ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths(STOCK_PRODUCT_ID_LABEL);
        product.setTitle(query);
        product.setDescription(query);
        Example<CmsProduct> example = Example.of(product, matcher);
        Page<?> result = this.repository.findAll(example, page);
        return ResponseEntity.ok(assembler.toModel(result, entityAssembler));
    }
}
