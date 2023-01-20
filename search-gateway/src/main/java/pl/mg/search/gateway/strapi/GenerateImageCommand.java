package pl.mg.search.gateway.strapi;

import lombok.Data;

@Data
public class GenerateImageCommand {

    private String imageFilePath;
    private String csvFilePath;

}
