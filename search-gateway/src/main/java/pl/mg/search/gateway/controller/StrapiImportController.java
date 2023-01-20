package pl.mg.search.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mg.search.gateway.strapi.GenerateImageCommand;
import pl.mg.search.gateway.strapi.StrapiService;

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
    public ResponseEntity<Void> generateImages(@RequestBody GenerateImageCommand command)  {
        log.debug("generateImages");
        strapiService.generateImages(command);
        return ResponseEntity.ok().build();
    }

    //generacja CSV do importu na podstawie prostego CSV z bazy

    //import produktów do strapi BS z docelowego formatu CSV z tłumaczeniami

}
