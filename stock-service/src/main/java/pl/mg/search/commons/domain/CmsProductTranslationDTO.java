package pl.mg.search.commons.domain;

import lombok.Data;


//TODO move this class to common module and make it DTO instead of entity in CMS
@Data
public class CmsProductTranslationDTO {

    private long id;

    private long cmsProductId;

    private long stockProductId;

    private String language;

    private String title;

    private String description;

}
