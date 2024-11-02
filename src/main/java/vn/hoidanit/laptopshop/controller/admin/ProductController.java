package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadService;

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

    // detail
    @GetMapping("/admin/product/detail/{id}")
    public String getProductDetail(Model model, @PathVariable long id) {
        Product prs = this.productService.getProductByid(id).get();
        model.addAttribute("product", prs);
        model.addAttribute("id", id);
        return "/admin/product/detail";

    }

    // update
    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable long id) {
        Optional<Product> currentProduct = this.productService.getProductByid(id);
        model.addAttribute("product", currentProduct.get());
        return "/admin/product/update";

    }

    @PostMapping("/admin/product/update")
    public String PostUpdateProduct(Model model, @ModelAttribute("product") @Valid Product dayladata,
            BindingResult newUserbindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {

        if (newUserbindingResult.hasErrors()) {
            return "/admin/product/update";
        }
        Product currentProduct = this.productService.getProductByid(dayladata.getId()).get();
        if (currentProduct != null) {
            if (!file.isEmpty()) {
                // update new image
                String img = this.uploadService.handleUpload(file, "product");
                currentProduct.setImage(img);
            }
            currentProduct.setName(currentProduct.getName());
            currentProduct.setDetailDesc(currentProduct.getDetailDesc());
            currentProduct.setShortDesc(currentProduct.getShortDesc());
            currentProduct.setPrice(currentProduct.getPrice());
            currentProduct.setFactory(currentProduct.getFactory());
            currentProduct.setQuantity(currentProduct.getQuantity());

            this.productService.handleSaveProduct(currentProduct);
        }
        return "admin/product";
    }

    // delete
    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable long id) {
        model.addAttribute("Id", id);
        model.addAttribute("product", new Product());
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String postdeleteProduct(Model model, @ModelAttribute("product") Product prs) {
        this.productService.deleteProduct(prs.getId());
        return "redirect:/admin/product";
    }

}
