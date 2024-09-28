package vn.hoidanit.laptopshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    // DI : dependency injection
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
    public String PostUpdateUser(Model model, @ModelAttribute("user") User dayladata) {
        User currentUser = this.userService.getuserbyId(dayladata.getId());
        if (currentUser != null) {
            currentUser.setFullName(currentUser.getFullName());
            currentUser.setAddress(currentUser.getAddress());
            currentUser.setPhone(currentUser.getPhone());
            this.userService.handleSaveUser(currentUser);
        }

        // System.out.println("check userid" + users);
        return "admin/user/index";
    }

    // CREATE
    @RequestMapping("/admin/user/create")
    public String create(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    // @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    // public String createUser(Model model, @ModelAttribute("newUser") User
    // dayladata) {
    // this.userService.handleSaveUser(dayladata);

    // }

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
