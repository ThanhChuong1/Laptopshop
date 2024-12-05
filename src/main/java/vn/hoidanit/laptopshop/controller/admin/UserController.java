package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UploadService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {
    // DI : dependency injection
    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UploadService uploadService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    // @RequestMapping("/")
    // public String getHomePage(Model model) {
    // String test = this.userService.handleHello();
    // List<User> arrays =
    // this.userService.getUserByEmail("namechuong19@gmail.com");
    // System.out.println(arrays);
    // model.addAttribute("eric", test);
    // return "hello";
    // }
    // GET INDEX + detail
    @RequestMapping("/admin/user/index")
    public String getIndex(Model model) {
        List<User> users = this.userService.getAllUser();
        model.addAttribute("user1", users);
        return "admin/user/index";
    }

    @RequestMapping("/admin/user/index/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        User users = this.userService.getuserbyId(id);
        // System.out.println("check userid" + users);
        model.addAttribute("user", users);
        return "admin/user/detail";
    }

    // UPDATE
    @RequestMapping("/admin/user/update/{id}")
    public String getUserUpdatePage(Model model, @PathVariable long id) {
        User users = this.userService.getuserbyId(id);
        // System.out.println("check userid" + users);
        model.addAttribute("user", users);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String PostUpdateUser(Model model, @ModelAttribute("user") User dayladata,
            @RequestParam("hoidanitFile") MultipartFile file) {
        User currentUser = this.userService.getuserbyId(dayladata.getId());
        if (currentUser != null) {
            currentUser.setFullName(currentUser.getFullName());
            currentUser.setAddress(currentUser.getAddress());
            currentUser.setPhone(currentUser.getPhone());
            currentUser.setRole(currentUser.getRole());
            String avatar = this.uploadService.handleUpload(file, "avatar");
            currentUser.setAvatar(avatar);
            this.userService.handleSaveUser(currentUser);
        }

        // System.out.println("check userid" + users);
        return "admin/user/index";
    }

    // CREATE
    @GetMapping("/admin/user/create")
    public String create(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping(value = "/admin/user/create")
    public String createUser(Model model, @ModelAttribute("newUser") @Valid User dayladata,
            BindingResult newUserbindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {
        List<FieldError> errors = newUserbindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>" + error.getField() + "-" + error.getDefaultMessage());
        }
        // validate
        if (newUserbindingResult.hasErrors()) {
            return "/admin/user/create";
        }

        String avatar = this.uploadService.handleUpload(file, "avatar");
        String hashPassword = this.passwordEncoder.encode(dayladata.getPassword());
        dayladata.setRole(this.userService.getRoleByName(dayladata.getRole().getName()));
        dayladata.setAvatar(avatar);
        dayladata.setPassword(hashPassword);
        this.userService.handleSaveUser(dayladata);// day la luu user
        return "redirect:/admin/user/index";
    }

    // Delete
    @GetMapping("/admin/user/delete/{id}")
    public String getDeletePage(Model model, @PathVariable long id) {
        model.addAttribute("userId", id);
        model.addAttribute("newUser", new User());
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postdeleteUser(Model model, @ModelAttribute("newUser") User newUser) {
        this.userService.deleUser(newUser.getId());
        return "redirect:/admin/user/index";
    }

}

// @RestController
// public class UserController {
// //DI : dependency injection
// private UserService userService;

// public UserController(UserService userService) {
// this.userService = userService;
// }

// @GetMapping("/")
// public String getHomePage() {
// return this.userService.handleHello();
// }

// }
