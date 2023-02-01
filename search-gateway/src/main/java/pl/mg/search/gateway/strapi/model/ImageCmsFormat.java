package pl.mg.search.gateway.strapi.model;

import lombok.Data;

@Data
public class ImageCmsFormat {

    private String ext;
    private String url;
    private String hash;
    private String mime;
    private String name;
    private String path;
    private double size;
    private long width;
    private long height;

}
