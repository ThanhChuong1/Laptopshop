package vn.hoidanit.laptopshop.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ItemController {
    @GetMapping("/product/detail/{id}")
    public String getPageProduct(Model model, @PathVariable long id) {
        return "/client/product/detail";
    }
}
