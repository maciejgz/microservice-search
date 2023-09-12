package pl.mg.search.cms.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource(collectionResourceRel = "cmsProduct", path = "cmsProduct")
public interface CmsProductRepository extends PagingAndSortingRepository<CmsProduct, Long>,
        CrudRepository<CmsProduct, Long>, JpaRepository<CmsProduct, Long> {

    Page<CmsProduct> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description, Pageable pageable);

}
