package khan.mobile.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HtmlController {

    // base url
    @RequestMapping("/")
    public String defaultHome() {
        return "redirect:/user/login";
    }

    @RequestMapping("/home")
    public String home() {
        log.info("home Controller");
        return "home";
    }

    @RequestMapping("/user/login")
    public String login() {
        log.info("login Controller");
        return "loginPages/loginForm";
    }

    @RequestMapping("/user/signup")
    public String signup() {
        log.info("login Controller");
        return "loginPages/signupForm";
    }

    @RequestMapping("user/find_email")
    public String findEmail() {
        log.info("find_email Controller");
        return "loginPages/findEmailForm";
    }

    @RequestMapping("user/find_password")
    public String findPassword() {
        log.info("find_password Controller");
        return "loginPages/findPasswordForm";
    }

    @RequestMapping("/product")
    public String product() {
        log.info("product Controller");
        return "productPages/product";
    }

    @RequestMapping("/product/detail")
    public String productDetail() {
        log.info("productDetail Controller");
        return "productPages/productDetail";
    }
}
