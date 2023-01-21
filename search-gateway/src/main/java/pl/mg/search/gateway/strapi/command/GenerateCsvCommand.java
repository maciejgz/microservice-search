package pl.mg.search.gateway.strapi.command;

import lombok.Data;

@Data
public class GenerateCsvCommand {
    private String csvFilePath;
}
