package pl.mg.search.stock.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "stockProduct", path = "stockProduct")
public interface StockProductRepository extends PagingAndSortingRepository<StockProduct, Long>,
        CrudRepository<StockProduct, Long>, JpaRepository<StockProduct, Long> {

    Optional<StockProduct> findByStockProductId(Long stockProductId);

    Page<StockProduct> findByCategoryEqualsIgnoreCase(String category, Pageable pageable);

}
