/*package com.example.library.controller;

import com.example.library.Model.User;
import com.example.library.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Admin login səhifəsi
    @GetMapping("login")
    public ModelAndView loginForm() {
        ModelAndView modelAndView = new ModelAndView("/admin/login");
        modelAndView.addObject("user", new User());  // Boş User obyektini modelə əlavə edir
        return modelAndView;  // admin/login.html şablonunu qaytarır
    }

    // Admin login əməliyyatı
    @PostMapping("/login")
    public String loginAdmin(@RequestParam String username,
                             @RequestParam String password,
                             Model model) {
        User admin = userService.findByUsernameAndRole(username, "ADMIN");

        // Əgər istifadəçi varsa və şifrə doğrudursa
        if (admin != null && admin.getPassword().equals(password)) {
            return "/website/Home";  // Uğurlu login olduqda home səhifəsinə yönləndirir
        }

        // Əgər səhvdirsə error mesajını göstərir
        return "admin/admin";  // Yenidən login səhifəsinə qaytarır
    }

    // Admin Home səhifəsi
    @GetMapping("/admin")
    public String adminHome() {
        return "admin/admin";  // admin/home.html şablonunu qaytarır
    }
}
*/


package com.example.library.controller;

import com.example.library.Model.User;
import com.example.library.Service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/login";
    }

    @PostMapping("/login")
    public String loginAdmin(@RequestParam String username,
                             @RequestParam String password,
                             Model model) {
        Optional<User> admin = userService.findByUsernameAndRole(username, "ADMIN");

        if (admin.isPresent() && passwordEncoder.matches(password, admin.get().getPassword())) {
            return "redirect:/admin/home";
        }

        model.addAttribute("error", "Invalid username or password");
        return "admin/login";
    }


    @GetMapping("/home")
    public String websiteHome() {
        return "admin/home"; // This correctly maps to templates/website/home.html
    }

}
