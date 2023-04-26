package pl.mg.search.gateway.strapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.bean.*;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import pl.mg.search.gateway.strapi.command.GenerateCsvCommand;
import pl.mg.search.gateway.strapi.command.GenerateImageCommand;
import pl.mg.search.gateway.strapi.command.ImportProductsCommand;
import pl.mg.search.gateway.strapi.model.*;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class StrapiServiceImpl implements StrapiService {

    protected static final String RESULT_DIRECTORY = "C:\\Users\\maciej\\result\\";

    private static final boolean OVERWRITE_EXISTING_ENTRIES = true;

//    private static final String CMS_URL = "http://localhost:1337";
    //private static final String CMS_URL = "https://cms-dev-betterstyle.eversis.com";
    private static final String CMS_URL = "https://cmsbt1.betterstyle.eu";

    //header
    protected static final String AUTHORIZATION_HEADER = "Bearer 54c8d08d7a60d0428a1ff37165995882c48667e98f554c5ff71fbb012ff5e4e6b74f922efd9c4a4a552a7685a780290f0a38d27d26763e31c61e303365190ea6dbdc74ef168ee962d02115f8745bcb2af64cd6789d3cf7cc68701b429db97cb77d48667a418411d26f4d7a6d4f13abdf9f963135086c89045bcdb6dbe3f07ac1";

    @Override
    public void generateImages(GenerateImageCommand command) {
        List<CatalogEntry> catalogEntries = listCsvCatalogEntries(command.getCsvFilePath());
        catalogEntries.forEach(catalogEntry -> log.debug(catalogEntry.toString()));

        Path imagePath = Path.of(command.getImageFilePath());

        for (CatalogEntry entry : catalogEntries) {
            try {
                Files.deleteIfExists(Path.of(RESULT_DIRECTORY + entry.getCode().trim() + "-1.jpg"));
                Path result1 = Files.copy(imagePath, Path.of(RESULT_DIRECTORY + entry.getCode().trim() + "-1.jpg"));
                Files.deleteIfExists(Path.of(RESULT_DIRECTORY + entry.getCode().trim() + "-2.jpg"));
                Path result2 = Files.copy(imagePath, Path.of(RESULT_DIRECTORY + entry.getCode().trim() + "-2.jpg"));
                Files.deleteIfExists(Path.of(RESULT_DIRECTORY + entry.getCode().trim() + "-3.jpg"));
                Path result3 = Files.copy(imagePath, Path.of(RESULT_DIRECTORY + entry.getCode().trim() + "-3.jpg"));
                Files.deleteIfExists(Path.of(RESULT_DIRECTORY + entry.getCode().trim() + "-add1.pdf"));
                Path result4 = Files.copy(imagePath, Path.of(RESULT_DIRECTORY + entry.getCode().trim() + "-add1.pdf"));
                log.debug("1: " + result1);
                log.debug("2: " + result2);
                log.debug("3: " + result3);
                log.debug("4: " + result4);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void generateCsvToImport(GenerateCsvCommand command) {
        List<CatalogEntry> catalogEntries = listCsvCatalogEntries(command.getCsvFilePath());
        catalogEntries.forEach(catalogEntry -> log.debug(catalogEntry.toString()));
        List<ProductEntry> entries = catalogEntries.stream()
                .map(catalogEntry ->
                        ProductEntry.builder()
                                .code(catalogEntry.getCode())
                                .mainImage(catalogEntry.getCode() + "-1.jpg")
                                .images(catalogEntry.getCode() + "-1.jpg," + catalogEntry.getCode() + "-2.jpg,"
                                        + catalogEntry.getCode() + "-3.jpg")
                                .additionalFiles(catalogEntry.getCode() + "-add1.pdf")
                                .titleDe(catalogEntry.getName())
                                .descriptionDe(catalogEntry.getDescription())
                                .slugDe(catalogEntry.getName().toLowerCase().replace(" ", "-"))
                                .shortDescriptionDe(catalogEntry.getShortDescription())
                                .region(new String[]{"germany"})
                                .build()
                ).toList();
        try {
            Files.deleteIfExists(Path.of(RESULT_DIRECTORY + "bs_products_cms_import_small.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (FileWriter writer = new FileWriter(
                Path.of(RESULT_DIRECTORY + "bs_products_cms_import_small.csv").toFile())) {
            StatefulBeanToCsv<ProductEntry> sbc = new StatefulBeanToCsvBuilder<ProductEntry>(writer)
                    .withQuotechar('\"')
                    .withSeparator(';')
                    .build();
            sbc.write(entries);

        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        }
    }

    private String findImages(String productCode) {
        log.debug(productCode);
        List<String> result = new ArrayList<>();
        Optional<String> prod1 = findImageId(productCode + "-1.jpg");
        Optional<String> prod2 = findImageId(productCode + "-2.jpg");
        Optional<String> prod3 = findImageId(productCode + "-3.jpg");
        prod1.ifPresent(result::add);
        prod2.ifPresent(result::add);
        prod3.ifPresent(result::add);
        return !result.isEmpty() ? String.join(",", result) : "";
    }

    private Optional<String> findImageId(String imageName) {
        String result = null;
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(CMS_URL + "/api/upload/files?filters[name][$eq]=" + imageName.trim()))
                    .header("Authorization",
                            AUTHORIZATION_HEADER)
                    .GET()
                    .build();
            HttpResponse<String> res = client.send(request, BodyHandlers.ofString());
            String regex = ".*\"id\": ?(\\d+),.*";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(res.body());
            if (matcher.matches()) {
                result = matcher.group(1);
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public void importProducts(ImportProductsCommand command) {
        List<ProductEntry> products;
        Path myPath = Path.of(command.getCsvFilePath());
        try (BufferedReader br = Files.newBufferedReader(myPath,
                StandardCharsets.UTF_8)) {

            HeaderColumnNameMappingStrategy<ProductEntry> strategy
                    = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(ProductEntry.class);

            CsvToBean<ProductEntry> csvToBean = new CsvToBeanBuilder<ProductEntry>(br)
                    .withMappingStrategy(strategy)
                    .withSeparator(';')
                    .withQuoteChar('\"')
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            products = csvToBean.parse();
            products.forEach(productEntry -> log.debug(productEntry.toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HttpClient client = HttpClient.newBuilder().build();

        for (ProductEntry product : products) {
            try {
                String slug = toSlug(toSlug(product.getTitleDe()));
                ProductCmsModel cmsModel = ProductCmsModel.builder()
                        .title(product.getTitleDe())
                        .description(product.getDescriptionDe())
                        .shortDescription(product.getShortDescriptionDe())
                        .productCode(product.getCode())
                        .slug(StringUtils.isNotBlank(product.getSlugDe()) ? toSlug(product.getSlugDe())
                                : slug)
                        .region(new String[]{"germany"})
                        .build();
                if (OVERWRITE_EXISTING_ENTRIES) {
                    this.findProduct(product.getCode()).ifPresent(this::deleteProduct);
                }
                if (StringUtils.isNotBlank(product.getMainImage())) {
                    Optional<String> image = findImageId(product.getMainImage());
                    image.ifPresent(s -> cmsModel.setMainImage(new Image(Integer.parseInt(s))));
                }
                if (StringUtils.isNotBlank(product.getImages())) {
                    String[] split = product.getImages().split(",");
                    List<Image> imageees = new ArrayList<>();
                    for (int i = 0; i < split.length; i++) {
                        Optional<String> image = findImageId(split[i]);
                        image.ifPresent(s -> imageees.add(new Image(Integer.parseInt(s))));
                    }
                    if (CollectionUtils.isNotEmpty(imageees)) {
                        Image[] array = new Image[imageees.size()];
                        cmsModel.setImages(imageees.toArray(array));
                    }
                }
                if (StringUtils.isNotBlank(product.getAdditionalFiles())) {
                    String[] split = product.getAdditionalFiles().split(",");
                    List<Image> adds = new ArrayList<>();
                    for (int i = 0; i < split.length; i++) {
                        Optional<String> image = findImageId(split[i]);
                        image.ifPresent(s -> adds.add(new Image(Integer.parseInt(s))));
                    }
                    if (CollectionUtils.isNotEmpty(adds)) {
                        Image[] array = new Image[adds.size()];
                        cmsModel.setAdditionalFiles(adds.toArray(array));
                    }
                }
                ProductCmsData data = new ProductCmsData(cmsModel);
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(CMS_URL + "/api/products"))
                        .headers("Content-Type", "application/json")
                        .header("Authorization",
                                AUTHORIZATION_HEADER)
                        .POST(BodyPublishers.ofString((new ObjectMapper()).writeValueAsString(
                                data
                        )))
                        .build();

                HttpResponse<String> res = client.send(request, BodyHandlers.ofString());

                log.debug("status code: " + res.statusCode() + "body" + res.body());
                //get ID of created product
                String regex = ".*\"id\": ?(\\d+),.*";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(res.body());
                /*if (matcher.matches()) {
                    String id = matcher.group(1);
                    ProductCmsLocalization localizationData = new ProductCmsLocalization();
                    localizationData.setLocale("pl");
                    localizationData.setSlug(toSlug(product.getSlugPl()));
                    localizationData.setTitle(product.getTitlePl());
                    localizationData.setDescription(product.getDescriptionPl());
                    if (StringUtils.isNotBlank(product.getImages())) {
                        String[] split = product.getImages().split(",");
                        Image[] reee2 = new Image[split.length];
                        for (int i = 0; i < split.length; i++) {
                            Optional<String> image = findImageId(split[i]);
                            if (image.isPresent()) {
                                reee2[i] = new Image(Integer.parseInt(image.get()));
                            }
                        }
                        localizationData.setImages(reee2);
                    }

                    if (StringUtils.isNotBlank(product.getAdditionalFiles())) {
                        String[] split = product.getAdditionalFiles().split(",");
                        Image[] reee2 = new Image[split.length];
                        for (int i = 0; i < split.length; i++) {
                            Optional<String> image = findImageId(split[i]);
                            if (image.isPresent()) {
                                reee2[i] = new Image(Integer.parseInt(image.get()));
                            }
                        }
                        localizationData.setAdditionalFiles(reee2);
                    }
                    HttpRequest localizationRequest = HttpRequest.newBuilder()
                            .uri(new URI(CMS_URL + "/api/products/" + id + "/localizations"))
                            .headers("Content-Type", "application/json")
                            .header("Authorization",
                                    AUTHORIZATION_HEADER)
                            .POST(BodyPublishers.ofString((new ObjectMapper()).writeValueAsString(
                                    localizationData
                            )))
                            .build();

                    HttpResponse<String> resLocale = client.send(localizationRequest, BodyHandlers.ofString());
                }*/
            } catch (URISyntaxException | IOException | InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static String toSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    @Override
    public void deleteProducts() {
        //TODO implement
    }

    @Override
    public void deleteProduct(String id) {
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(CMS_URL + "/api/products/" + id))
                    .headers("Content-Type", "application/json")
                    .header("Authorization",
                            AUTHORIZATION_HEADER)
                    .DELETE()
                    .build();

            HttpResponse<String> res = client.send(request, BodyHandlers.ofString());
            log.debug(res.body());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public Optional<String> findProduct(String code) {
        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(CMS_URL + "/api/products?locale=de&filters[region][$containsi]=germany&filters[productCode][$eq]=" + code))
                    .headers("Content-Type", "application/json")
                    .header("Authorization",
                            AUTHORIZATION_HEADER)
                    .GET()
                    .build();

            HttpResponse<String> res = client.send(request, BodyHandlers.ofString());
            String regex = ".*\"id\": ?(\\d+),.*";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(res.body());
            if (matcher.matches()) {
                String id = matcher.group(1);
                log.debug("Found product with code: " + code + " with id: " + id);
                return Optional.of(id);
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    private List<CatalogEntry> listCsvCatalogEntries(String catalogCsvPath) {
        List<CatalogEntry> ls = new ArrayList<>();

        try (Reader reader = Files.newBufferedReader(Path.of(catalogCsvPath))) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                List<String[]> result = csvReader.readAll();
                for (String[] strings : result) {
                    ls.add(new CatalogEntry(strings[0], strings[1], strings[2], strings[3]));
                }
            }
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
        return ls;
    }

}
