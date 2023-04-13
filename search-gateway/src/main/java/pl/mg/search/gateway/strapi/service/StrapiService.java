package pl.mg.search.gateway.strapi.service;

import pl.mg.search.gateway.strapi.command.GenerateCsvCommand;
import pl.mg.search.gateway.strapi.command.GenerateImageCommand;
import pl.mg.search.gateway.strapi.command.ImportProductsCommand;

import java.util.Optional;

public interface StrapiService {

    void generateImages(GenerateImageCommand command);

    void generateCsvToImport(GenerateCsvCommand command);

    void importProducts(ImportProductsCommand command);

    void deleteProducts();

    void deleteProduct(String code);

    Optional<String> findProduct(String code);
}
