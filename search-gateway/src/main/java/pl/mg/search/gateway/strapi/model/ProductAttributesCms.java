package pl.mg.search.gateway.strapi.model;

import lombok.Data;

@Data
public class ProductAttributesCms {

    private String title;
    private String productCode;
    private String description;

    private String locale;
    private ImagesCms images;
    private String[] region;
    private String slug;

    private String createdAt;

    private String updatedAt;
}
