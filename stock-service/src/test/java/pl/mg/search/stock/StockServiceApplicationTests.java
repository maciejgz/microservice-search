package pl.mg.search.stock;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Testcontainers
class StockServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:13-alpine"))
                    .withDatabaseName("test")
                    .withUsername("test")
                    .withPassword("test");

    static {
        POSTGRESQL_CONTAINER.start();
    }

    @Bean
    public DataSource dataSource() {
        System.out.println("AAAAA: " + POSTGRESQL_CONTAINER.getJdbcUrl());
        return DataSourceBuilder.create()
                .url(POSTGRESQL_CONTAINER.getJdbcUrl())
                .username(POSTGRESQL_CONTAINER.getUsername())
                .password(POSTGRESQL_CONTAINER.getPassword())
                .build();
    }

    @Test
    void test() {
        assertTrue(POSTGRESQL_CONTAINER.isRunning());
    }

}
