package pl.mg.search.stock.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "stock_product")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockProduct {

    @Id
    private long id;

    @Column(name = "stock_product_id")
    private long stockProductId;

    private BigDecimal price;

    private String category;
}
