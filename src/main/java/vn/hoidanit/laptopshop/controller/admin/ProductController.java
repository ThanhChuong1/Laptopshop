package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product")
    public String getPageProduct(Model model) {
        List<Product> prs = productService.getAllProducts();
        model.addAttribute("products", prs);
        return "/admin/product/show";
    }

    // create
    @GetMapping("/admin/product/create")
    public String getPageCreateProduct(Model model) {
        model.addAttribute("newProduct", new Product());
        return "/admin/product/create";
    }

    @PostMapping(value = "/admin/product/create")
    public String CreateProduct(@ModelAttribute("newProduct") @Valid Product daylaDaTa,
            BindingResult newUserbindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {
        // List<FieldError> errors = newUserbindingResult.getFieldErrors();
        // for (FieldError error : errors) {
        // System.out.println(">>>>" + error.getField() + "-" +
        // error.getDefaultMessage());
        // }
        // validate
        if (newUserbindingResult.hasErrors()) {
            return "/admin/product/create";
        }
        String product = this.uploadService.handleUpload(file, "product");
        daylaDaTa.setImage(product);
        this.productService.handleSaveProduct(daylaDaTa);
        return "redirect:/admin/product";
    }

}
