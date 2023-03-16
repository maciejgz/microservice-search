package pl.mg.search.gateway.strapi.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductCmsModel {

    private String title;
    private String productCode;
    private String shortDescription;
    private String description;
    private String slug;
    private Image mainImage;
    private String[] region;
    private Image[] images;
    private Image[] additionalFiles;
}
