package com.DEMO1.demo.controller;

import com.DEMO1.demo.common.enumeration.Gender;
import com.DEMO1.demo.model.User;
import com.DEMO1.demo.repository.UserRepository;
import com.DEMO1.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class AppController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String viewHomePage() {
        return "index.html";
    }

    @GetMapping("/register")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("genders", Gender.values());
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegistration(User user, Model model) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            model.addAttribute("user", user);
            model.addAttribute("genders", Gender.values());
            model.addAttribute("errorMessage", "Username is already taken. Please choose a different one.");
            return "signup_form";
        }
        userRepository.save(user);
        return "register_success";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginHandler(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            User user = userService.getUserByUserName(username);
            if (user.getPassword().equals(password)) {
                model.addAttribute("user", user);
                return "profile";
            } else {
                model.addAttribute("error", "Invalid Username or password");
                return "login";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/profile")
    public String showProfilePage(@RequestParam Long id, Model model) throws Exception {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
            model.addAttribute("genders", Gender.values());
            return "profile";
        }
        model.addAttribute("errorMessage", "User not found.");
        return "errorPage";
    }

    @PostMapping("/update")
    public String updatedUserProfile(@ModelAttribute User updatedUser, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.updateUserProfile(updatedUser);
            redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
            return "redirect:/profile?id=" + user.getId(); // Redirect back to profile page
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating profile. Please try again.");
            return "redirect:/profile?id=" + updatedUser.getId(); // Redirect back to profile with error
        }
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUserById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Deleted Successfully");
            return "redirect:/";  // Redirect to home page or login page after deletion
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}


