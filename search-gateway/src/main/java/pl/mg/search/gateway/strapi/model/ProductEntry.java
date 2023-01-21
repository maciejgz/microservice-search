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
    @CsvBindByName(column = "name_de")
    private String nameDe;
    @CsvBindByName(column = "description_de")
    private String descriptionDe;

    @CsvBindByName(column = "name_pl")
    private String namePl;
    @CsvBindByName(column = "description_pl")
    private String descriptionPl;

}