package pl.mg.ms.gw;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
@Slf4j
public class FallbackController {


    //get method for fallback for stock service
    public String stockServiceFallback() {
        log.info("Fallback for stock service");
        return "Stock service is not available";
    }

    @RequestMapping("/cms")
    public String cmsServiceFallback() {
        log.info("Fallback for stock service");
        return "Stock service is not available";
    }
}
