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
    @CsvBindByName(column = "main_image")
    private String mainImage;
    @CsvBindByName(column = "images")
    private String images;
    @CsvBindByName(column = "additional_files")
    private String additionalFiles;
    @CsvBindByName(column = "title_de")
    private String titleDe;
    @CsvBindByName(column = "description_de")
    private String descriptionDe;
    @CsvBindByName(column = "short_description_de")
    private String shortDescriptionDe;
    @CsvBindByName(column = "slug_de")
    private String slugDe;
    @CsvBindByName(column = "region")
    private String[] region;

}
