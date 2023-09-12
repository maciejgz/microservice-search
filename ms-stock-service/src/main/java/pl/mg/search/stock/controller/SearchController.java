package pl.mg.search.stock.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.mg.search.stock.domain.StockProduct;
import pl.mg.search.stock.domain.StockProductRepository;

@RestController
@RequestMapping(value = {"/api/v1/stockProduct", "/api/v2/stockProduct"})
@Slf4j
@AllArgsConstructor
public class SearchController {

    private final StockProductRepository repository;

    @GetMapping("/filter")
    public ResponseEntity<Page<StockProduct>> filter(
            HttpServletRequest request,
            Pageable page,
            @RequestParam(name = "q") String query
    ) {
        log.debug("Request: {}", request.getRequestURI());
        //TODO add search by title and description in translation
        Page<StockProduct> products = repository.findByCategoryEqualsIgnoreCase(query, page);
        return ResponseEntity.ok(products);
    }


}
