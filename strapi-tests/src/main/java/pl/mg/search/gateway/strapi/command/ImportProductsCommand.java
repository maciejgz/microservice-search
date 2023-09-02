package pl.mg.search.gateway.strapi.command;

import lombok.Data;

@Data
public class ImportProductsCommand {
    private String csvFilePath;
}
