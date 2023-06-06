package pl.mg.search.commons.domain;

import lombok.Data;


@Data
public class CmsProductTranslationDTO {

    private long id;

    private long cmsProductId;

    private long stockProductId;

    private String language;

    private String title;

    private String description;

}
