package pl.mg.search.stock.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cms_product_translation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockProductTranslation {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;

    @Column(name = "stock_product_id")
    private long stockProductId;

    private String language;

    private String title;

    private String description;

}
