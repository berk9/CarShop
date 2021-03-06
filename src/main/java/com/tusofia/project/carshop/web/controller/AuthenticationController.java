package com.tusofia.project.carshop.web.controller;

import com.tusofia.project.carshop.dto.binding.auth.UserAddBindingModel;
import com.tusofia.project.carshop.dto.binding.auth.UserServiceModel;
import com.tusofia.project.carshop.dto.view.UserDetailsViewModel;
import com.tusofia.project.carshop.service.UserDetailsServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AuthenticationController {
    private final UserDetailsServiceImpl userService;
    private final ModelMapper modelMapper;

    public AuthenticationController(UserDetailsServiceImpl userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public String login(){
        return "authenticate/login";
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String showRegisterView(Model model) {
        model.addAttribute("userRegisterForm", new UserAddBindingModel());
        return "authenticate/registration";
    }

    @GetMapping("/authentication/profile")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String showProfile(Principal principal, Model model){
        UserDetailsViewModel user = this.modelMapper.map(
                this.userService.loadUserByUsername(principal.getName()) , UserDetailsViewModel.class);

        model.addAttribute("userProfile", user);
        return "authenticate/profile";
    }

    @GetMapping("/authentication/edit")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String editUser(Principal principal, Model model) {
        UserServiceModel user = this.modelMapper.map(
                this.userService.loadUserByUsername(principal.getName()), UserServiceModel.class);

        model.addAttribute("userEditForm", user);
        return "authenticate/edit";
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public String register(@Valid @ModelAttribute("userRegisterForm") UserAddBindingModel user,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "authenticate/registration";
        }
        if (userService.isUsernameAvailable(user.getUsername())) {
            //TODO: Add error message here: "User with that username already exists".
            return "authenticate/registration";
        }

        this.userService.createAndLoginUser(user);
        return "redirect:/home";
    }

    @PostMapping("authentication/edit/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String editUserConfirm(@Valid @ModelAttribute("userEditForm") UserServiceModel userModel,
                                     @PathVariable("id") Long userId,
                                     BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "redirect:authentication/edit";
        }

        this.userService.editUser(userId, userModel);
        return "redirect:/authentication/profile";
    }

}
