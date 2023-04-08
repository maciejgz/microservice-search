package pl.mg.search.stock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.mg.search.stock.domain.StockProduct;
import pl.mg.search.stock.domain.StockProductRepository;

import java.math.BigDecimal;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
class StockServiceDbTests {

    @Autowired
    private PostgreSQLContainer<?> container;
    @Autowired
    private StockProductRepository repository;

    @BeforeEach
    public void cleanUp() {
        repository.deleteAll();
    }

    private void insertRandomData() {
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 1000; i++) {
            StockProduct product = new StockProduct();
            product.setId(i + 2);
            product.setStockProductId(random.nextInt());
            product.setPrice(BigDecimal.valueOf(random.nextDouble()));
            product.setCategory("Category " + (i % 10 + 1));

            repository.save(product);
        }
    }

    @Test
    void testContainer() {
        assertTrue(container.isRunning());
    }

    @Test
    void testSave() {
        insertRandomData();
        StockProduct product = new StockProduct(1L, 1L, new BigDecimal(12), "test");
        repository.save(product);
        assertTrue(repository.findById(1L).isPresent());
    }

    @Test
    void testEmptyTable() {
        assertFalse(repository.findById(1L).isPresent());
    }

}
