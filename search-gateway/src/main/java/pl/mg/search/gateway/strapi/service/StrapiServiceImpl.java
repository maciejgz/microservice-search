package pl.mg.search.gateway.strapi.service;

import com.opencsv.CSVReader;
import com.opencsv.ICSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.mg.search.gateway.strapi.command.GenerateCsvCommand;
import pl.mg.search.gateway.strapi.command.GenerateImageCommand;
import pl.mg.search.gateway.strapi.command.ImportProductsCommand;
import pl.mg.search.gateway.strapi.model.CatalogEntry;
import pl.mg.search.gateway.strapi.model.ProductEntry;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StrapiServiceImpl implements StrapiService {

    protected static final String RESULT_DIRECTORY = "C:\\Users\\maciej\\result\\";

    @Override
    public void generateImages(GenerateImageCommand command) {
        List<CatalogEntry> catalogEntries = listCsvEntries(command.getCsvFilePath());
        catalogEntries.forEach(catalogEntry -> log.debug(catalogEntry.toString()));

        Path imagePath = Path.of(command.getImageFilePath());

        for (CatalogEntry entry : catalogEntries) {
            try {
                Path result1 = Files.copy(imagePath, Path.of(RESULT_DIRECTORY + entry.getCode().trim() + "-1.jpg"));
                Path result2 = Files.copy(imagePath, Path.of(RESULT_DIRECTORY + entry.getCode().trim() + "-2.jpg"));
                Path result3 = Files.copy(imagePath, Path.of(RESULT_DIRECTORY + entry.getCode().trim() + "-3.jpg"));
                log.debug("1: " + result1);
                log.debug("1: " + result2);
                log.debug("1: " + result3);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void generateCsv(GenerateCsvCommand command) {
        List<CatalogEntry> catalogEntries = listCsvEntries(command.getCsvFilePath());
        catalogEntries.forEach(catalogEntry -> log.debug(catalogEntry.toString()));
        List<ProductEntry> entries = catalogEntries.stream()
                .map(catalogEntry ->
                        ProductEntry.builder()
                                .code(catalogEntry.getCode())
                                .nameDe(catalogEntry.getName())
                                .descriptionDe(catalogEntry.getDescription())
                                .namePl("PL " + catalogEntry.getName())
                                .descriptionPl("PL " + catalogEntry.getDescription())
                                .build()
                ).toList();
        try (FileWriter writer = new FileWriter(
                Path.of(RESULT_DIRECTORY + "bs_csv_to_import.csv").toFile())) {
            StatefulBeanToCsv<ProductEntry> sbc = new StatefulBeanToCsvBuilder<ProductEntry>(writer)
                    .withQuotechar('\"')
                    .withSeparator(ICSVWriter.DEFAULT_SEPARATOR)
                    .build();
            sbc.write(entries);

        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void importProducts(ImportProductsCommand command) {
        List<ProductEntry> products = new ArrayList<>();
        Path myPath = Path.of(command.getCsvFilePath());
        try (BufferedReader br = Files.newBufferedReader(myPath,
                StandardCharsets.UTF_8)) {

            HeaderColumnNameMappingStrategy<ProductEntry> strategy
                    = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(ProductEntry.class);

            CsvToBean<ProductEntry> csvToBean = new CsvToBeanBuilder<ProductEntry>(br)
                    .withMappingStrategy(strategy)
                    .withSeparator(',')
                    .withQuoteChar('\"')
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            products = csvToBean.parse();
            products.forEach(productEntry -> log.debug(productEntry.toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //TODO implement import
    }

    private List<CatalogEntry> listCsvEntries(String catalogCsvPath) {
        List<CatalogEntry> ls = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(Path.of(catalogCsvPath))) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                List<String[]> result = csvReader.readAll();
                for (String[] strings : result) {
                    ls.add(new CatalogEntry(strings[0], strings[1], strings[2]));
                }
            }
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
        return ls;
    }

}
