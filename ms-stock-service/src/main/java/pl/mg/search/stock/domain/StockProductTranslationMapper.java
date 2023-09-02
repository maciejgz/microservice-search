package pl.mg.search.stock.domain;

import pl.mg.search.commons.domain.CmsProductTranslationDTO;

public class StockProductTranslationMapper {

    public static StockProductTranslation map(CmsProductTranslationDTO cmsProductTranslation) {
        StockProductTranslation stockProductTranslation = new StockProductTranslation();
        stockProductTranslation.setLanguage(cmsProductTranslation.getLanguage());
        stockProductTranslation.setTitle(cmsProductTranslation.getTitle());
        stockProductTranslation.setDescription(cmsProductTranslation.getDescription());
        stockProductTranslation.setStockProductId(cmsProductTranslation.getStockProductId());
        return stockProductTranslation;
    }
}
