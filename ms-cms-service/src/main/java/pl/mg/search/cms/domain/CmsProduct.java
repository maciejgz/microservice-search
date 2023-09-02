package pl.mg.search.cms.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cms_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CmsProduct {

    @Id
    private long id;

    @Column(name = "stock_product_id")
    private long stockProductId;

    private String title;

    @Column(name = "description", length = 5000)
    private String description;

    @OneToMany(cascade = jakarta.persistence.CascadeType.ALL,
               orphanRemoval = true
    )
    @JoinColumn(name = "cms_product_id")
    private Set<CmsProductTranslation> translations;

    public void addTranslation(CmsProductTranslation translation) {
        if (translations == null) {
            translations = new HashSet<>();
        }
        this.translations.add(translation);
    }

}
