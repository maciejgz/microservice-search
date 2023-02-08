package pl.mg.search.gateway.strapi.model;

import lombok.Data;

@Data
public class ProductCmsLocalization {
    private String title;
    private String description;
    private String slug;
    private String locale;

    private Image[] images;
    private Image[] additionalFiles;
}
