package pl.mg.search.stock;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

@Configuration
public class TestDataSourceConfig {

    @Bean
    public PostgreSQLContainer<?> postgresContainer() {
        PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse("postgres:13-alpine"))
                .withExposedPorts(5432)
                .withDatabaseName("test")
                .withUsername("test")
                .withInitScript("initDB.sql")
                .withPassword("test");
        container.start();
        return container;
    }

    @Bean
    @Primary
    public DataSource dataSource(PostgreSQLContainer<?> postgreSQLContainer) {
        return DataSourceBuilder.create()
                .url(postgreSQLContainer.getJdbcUrl())
                .username(postgreSQLContainer.getUsername())
                .password(postgreSQLContainer.getPassword())
                .driverClassName("org.postgresql.Driver")
                .build();
    }
}
