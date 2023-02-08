package pl.mg.search.gateway.strapi.model;

import com.jsoniter.annotation.JsonProperty;
import lombok.Data;

@Data
public class AdditionalFileCmsAttributes {

    private String name;
    private String alternativeText;
    private String caption;
    @JsonProperty(defaultValueToOmit = "null")
    private Long width;
    @JsonProperty(defaultValueToOmit = "null")
    private Long height;
    @JsonProperty(defaultValueToOmit = "null")
    private ImageCmsFormats formats;
    private String hash;
    private String ext;
    private String mime;
    @JsonProperty(defaultValueToOmit = "null")
    private Double size;
    private String url;
    private String previewUrl;
    private String provider;
    private String provider_metadata;
    private String createdAt;
    private String updatedAt;

}
