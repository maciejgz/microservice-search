package pl.mg.search.cms.domain;

import pl.mg.search.commons.domain.CmsProductTranslationDTO;

public class CmsProductTranslationMapper {

    public static CmsProductTranslationDTO toDto(CmsProductTranslation translation) {
        CmsProductTranslationDTO dto = new CmsProductTranslationDTO();
        dto.setId(translation.getId());
        dto.setTitle(translation.getTitle());
        dto.setDescription(translation.getDescription());
        dto.setLanguage(translation.getLanguage());
        dto.setCmsProductId(translation.getProduct().getId());
        dto.setStockProductId(translation.getProduct().getStockProductId());
        return dto;
    }
}
