package pl.mg.search.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mg.search.gateway.strapi.command.GenerateCsvCommand;
import pl.mg.search.gateway.strapi.command.GenerateImageCommand;
import pl.mg.search.gateway.strapi.command.ImportProductsCommand;
import pl.mg.search.gateway.strapi.service.StrapiService;

@RestController
@RequestMapping(value = "/strapi")
@Slf4j
public class StrapiImportController {

    private final StrapiService strapiService;

    public StrapiImportController(StrapiService strapiService) {
        this.strapiService = strapiService;
    }

    //generacja obrazków testowych z IDkami jak w CSV - katalog z czerwca z bazy
    @PostMapping(value = "/generateImages", name = "generateImages")
    public ResponseEntity<Void> generateImages(@RequestBody GenerateImageCommand command) {
        log.debug("generateImages");
        strapiService.generateImages(command);
        return ResponseEntity.ok().build();
    }

    /*
        generacja CSV do importu do Strapi na podstawie prostego CSV z bazy.
        CSV powinien zawierać wszystkie wymagane wersje językowe - jeśli jakiejś wersji jezykowej nie będzie to musimy
        ustawiona domyślna wersja dla danego kraju.
     */
    @PostMapping(value = "/generateCsv", name = "generateCsv")
    public ResponseEntity<Void> generateCsv(@RequestBody GenerateCsvCommand command) {
        log.debug("generateCsv");
        strapiService.generateCsvToImport(command);
        return ResponseEntity.ok().build();
    }

    //import produktów do strapi BS z docelowego formatu CSV z tłumaczeniami i przypisaniem
    @PostMapping(value = "/importProducts", name = "importProducts")
    public ResponseEntity<Void> importProducts(@RequestBody ImportProductsCommand command) {
        log.debug("importProducts");
        strapiService.importProducts(command);
        return ResponseEntity.ok().build();
    }

}
