package vn.hoidanit.laptopshop;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HelloController {
    @GetMapping("/")
    public String getMethodName() {
        return ("Hello World update");
    }

    @GetMapping("/user")
    public String userPage() {
        return ("Only User");
    }

    @GetMapping("/admin")
    public String adminPage() {
        return ("Only Admin");
    }

}
