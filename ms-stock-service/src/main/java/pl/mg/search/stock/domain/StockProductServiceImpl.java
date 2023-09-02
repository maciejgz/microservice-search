package pl.mg.search.stock.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mg.search.commons.domain.CmsProductTranslationDTO;

import java.util.Optional;

@Service
public class StockProductServiceImpl implements StockProductService {

    private final StockProductRepository stockProductRepository;

    public StockProductServiceImpl(StockProductRepository stockProductRepository) {
        this.stockProductRepository = stockProductRepository;
    }

    @Override
    @Transactional
    public void addProductTranslation(CmsProductTranslationDTO cmsProductTranslation) {
        Optional<StockProduct> stockProduct = stockProductRepository.findByStockProductId(cmsProductTranslation.getStockProductId());
        stockProduct.ifPresent(sp -> {
            sp.addTranslation(StockProductTranslationMapper.map(cmsProductTranslation));
            stockProductRepository.save(sp);
        });
    }
}
