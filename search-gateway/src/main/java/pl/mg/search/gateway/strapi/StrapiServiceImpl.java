package pl.mg.search.gateway.strapi;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StrapiServiceImpl implements StrapiService {

    @Override
    public void generateImages(GenerateImageCommand command) {
        List<CatalogEntry> catalogEntries = listCsvEntries(command.getCsvFilePath());
        catalogEntries.forEach(catalogEntry -> log.debug(catalogEntry.toString()));

        Path imagePath = Path.of(command.getImageFilePath());

        for (CatalogEntry entry : catalogEntries) {
            try {
                Path result = Files.copy(imagePath, Path.of("C:\\Users\\maciej\\result\\" + entry.getCode().trim() + ".jpg"));
                log.debug(result.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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
