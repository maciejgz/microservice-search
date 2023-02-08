package pl.mg.search.gateway.strapi.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntry {

    @CsvBindByName(column = "code")
    private String code;
    @CsvBindByName(column = "images")
    private String images;
    @CsvBindByName(column = "additionalFiles")
    private String additionalFiles;
    @CsvBindByName(column = "title_de")
    private String titleDe;
    @CsvBindByName(column = "description_de")
    private String descriptionDe;
    @CsvBindByName(column = "slug_de")
    private String slugDe;
    @CsvBindByName(column = "title_pl")
    private String titlePl;
    @CsvBindByName(column = "description_pl")
    private String descriptionPl;
    @CsvBindByName(column = "slug_pl")
    private String slugPl;
    @CsvBindByName(column = "region")
    private String[] region;

}
