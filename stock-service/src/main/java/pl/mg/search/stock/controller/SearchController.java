package pl.mg.search.stock.controller;

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
import pl.mg.search.stock.domain.StockProduct;
import pl.mg.search.stock.domain.StockProductRepository;

@RepositoryRestController
@RequiredArgsConstructor
public class SearchController {

    protected static final String CATEGORY_LABEL = "category";
    protected static final String TITLE_LABEL = "title";
    protected static final String STOCK_PRODUCT_ID_LABEL = "stockProductId";
    private final StockProductRepository repository;

    @GetMapping("/stockProduct/filter")
    public ResponseEntity<?> filter(
            StockProduct product,
            Pageable page,
            PagedResourcesAssembler assembler,
            PersistentEntityResourceAssembler entityAssembler,
            @RequestParam(name = "q") String query
    ) {
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withIgnoreCase()
                .withMatcher(CATEGORY_LABEL, ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher(TITLE_LABEL, ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths(STOCK_PRODUCT_ID_LABEL);
        product.setCategory(query);
        Example<StockProduct> example = Example.of(product, matcher);
        Page<?> result = this.repository.findAll(example, page);
        return ResponseEntity.ok(assembler.toModel(result, entityAssembler));
    }

}
