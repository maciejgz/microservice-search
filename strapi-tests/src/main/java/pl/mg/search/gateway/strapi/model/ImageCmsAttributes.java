package pl.mg.search.gateway.strapi.model;

import lombok.Data;

@Data
public class ImageCmsAttributes {

    private String name;
    private String alternativeText;
    private String caption;
    private long width;
    private long height;
    private ImageCmsFormats formats;

    private String hash;
    private String ext;
    private String mime;
    private double size;
    private String url;
    private String previewUrl;
    private String provider;
    private String provider_metadata;
    private String createdAt;
    private String updatedAt;



}
