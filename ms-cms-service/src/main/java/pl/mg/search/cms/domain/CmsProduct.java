package pl.mg.search.cms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cms_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CmsProduct implements Serializable {

    @Id
    private long id;

    @Column(name = "stock_product_id")
    private long stockProductId;

    private String title;

    @Column(name = "description", length = 5000)
    private String description;

    @JsonIgnore
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
