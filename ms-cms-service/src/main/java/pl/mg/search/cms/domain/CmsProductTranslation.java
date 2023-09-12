package pl.mg.search.cms.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "cms_product_translation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(CmsProductTranslationListener.class)
public class CmsProductTranslation implements Serializable {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cms_product_id")
    private CmsProduct product;

    private String language;

    private String title;

    private String description;

}
