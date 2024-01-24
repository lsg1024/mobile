package khan.mobile.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class htmlController {

    @RequestMapping("/")
    public String home() {
        log.info("home Controller");
        return "home";
    }

    @RequestMapping("/product")
    public String product() {
        log.info("product Controller");
        return "productPages/product";
    }
}
