package vn.hoidanit.laptopshop.controller.client;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;

@Controller
public class HomePageController {
    private final ProductService productService;

    public HomePageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getMethodName(Model model) {
        List<Product> prs = productService.getAllProducts();
        model.addAttribute("listProduct", prs);
        return "client/homepage/index";

    }

    @GetMapping("/403forbiden")
    public String getAccessDeny(Model model) {
        List<Product> prs = productService.getAllProducts();
        model.addAttribute("listProduct", prs);
        return "client/auth/accessdeny";
    }

}
