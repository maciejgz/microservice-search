package pl.mg.search.gateway.strapi.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductCmsModel {
    private String title;
    private String productCode;
    private String description;
    private String[] region;
    private Image[] images;
}
