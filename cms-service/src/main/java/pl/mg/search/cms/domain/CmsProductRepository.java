package pl.mg.search.cms.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "cmsProduct", path = "cmsProduct")
public interface CmsProductRepository extends PagingAndSortingRepository<CmsProduct, Long>,
        CrudRepository<CmsProduct, Long> {

}
