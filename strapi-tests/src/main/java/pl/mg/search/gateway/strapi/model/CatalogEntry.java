package pl.mg.search.gateway.strapi.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CatalogEntry {

    @CsvBindByName(column = "code")
    private String code;
    @CsvBindByName(column = "name")
    private String name;
    @CsvBindByName(column = "description")
    private String description;
    @CsvBindByName(column = "short_description")
    private String shortDescription;

}
