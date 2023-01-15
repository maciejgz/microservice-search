package pl.mg.search.cms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cms_product")
@Data
@NoArgsConstructor
public class CmsProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "stock_product_id")
    private long stockProductId;

    private String title;

    @Column(name = "description", length = 5000)
    private String description;

    public CmsProduct(Long stockProductId, String title, String description) {
        this.stockProductId = stockProductId;
        this.title = title;
        this.description = description;
    }

}
