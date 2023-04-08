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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
class StockServiceApplicationTests {

    @Autowired
    private PostgreSQLContainer<?> container;
    @Autowired
    private StockProductRepository repository;

/*    @BeforeEach
    public void restartDB() {
        container.stop();
        container
                .withInitScript("initDB.sql")
                .start();
    }*/

    @BeforeEach
    public void cleanUp() {
        repository.deleteAll();
    }

    @Test
    void testContainer() {
        assertTrue(container.isRunning());
    }

    @Test
    void testSave() {
        StockProduct product = new StockProduct(1L, 1L, new BigDecimal(12), "test");
        repository.save(product);
        assertTrue(repository.findById(1L).isPresent());
    }

    @Test
    void testEmptyTable() {
        assertFalse(repository.findById(1L).isPresent());
    }

}
