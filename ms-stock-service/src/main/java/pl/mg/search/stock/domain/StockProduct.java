package pl.mg.search.stock.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

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

    @JsonIgnore
    @OneToMany(cascade = jakarta.persistence.CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "stock_product_id")
    private Set<StockProductTranslation> translations;

    public void addTranslation(StockProductTranslation translation) {
        if (translations == null) {
            translations = Set.of();
        }
        this.translations.add(translation);
    }
}
