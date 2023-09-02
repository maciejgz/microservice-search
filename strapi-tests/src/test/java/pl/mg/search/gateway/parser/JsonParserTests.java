package pl.mg.search.gateway.parser;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.junit.jupiter.api.Test;
import pl.mg.search.gateway.strapi.model.ProductCms;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonParserTests {

    @Test
    void sampleParserTest() throws IOException, URISyntaxException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL resource = classloader.getResource("productsSearchResult.json");
        String json = Files.readString(Paths.get(resource.toURI()), StandardCharsets.UTF_8);

        Any res = JsonIterator.deserialize(json);
        for (Any data : res.get("data")) {
            ProductCms product = new ProductCms();
            data.bindTo(product);
            System.out.println(product);
        }
    }

    @Test
    void noImagesTest() throws IOException, URISyntaxException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL resource = classloader.getResource("productsSearchResultWithoutImages.json");
        String json = Files.readString(Paths.get(resource.toURI()), StandardCharsets.UTF_8);

        Any res = JsonIterator.deserialize(json);
        for (Any data : res.get("data")) {
            ProductCms product = new ProductCms();
            data.bindTo(product);
            System.out.println(product);
        }
    }

}
