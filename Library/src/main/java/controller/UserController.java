package controller;

import Model.User;
import Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Adminin qeydiyyat formu
    @GetMapping("/register")
    public String registerForm() {
        return "admin-register";  // `admin-register.html` saytı qaytarır
    }

    // Admini qeydiyyatdan keçir
    @PostMapping("/register")
    public String registerAdmin(@RequestParam String name, @RequestParam String password) {
        User admin = new User(name, password, "ADMIN");
        userService.createUser(admin);
        return "redirect:/admin/login";  // Qeydiyyat bitdikdən sonra login səhifəsinə yönləndir
    }
}

