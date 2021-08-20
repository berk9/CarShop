package com.tusofia.project.carshop.web.controller;

import com.tusofia.project.carshop.dto.binding.PromotionAddBindingModel;
import com.tusofia.project.carshop.service.PromotionService;
import com.tusofia.project.carshop.service.CarService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/promotions")
public class PromotionController {
    private final PromotionService promotionService;
    private final CarService carService;

    public PromotionController(PromotionService promotionService, CarService carService) {
        this.promotionService = promotionService;
        this.carService = carService;
    }

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public String allPromotions(Model model){
        model.addAttribute("promotions", this.promotionService.findAllValidPromotions()) ;
        return "/promotion/allPromotions";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addPromotion(Model model){
        model.addAttribute("promotionInputForm", new PromotionAddBindingModel());
        model.addAttribute("allCars", this.carService.findAll());
        return "/promotion/createPromotion";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String save
            (@Valid @ModelAttribute("promotionInputForm") PromotionAddBindingModel promotionDTO,
             BindingResult bindingResult,
             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "redirect:/promotion/add";
        }
        this.promotionService.createPromotion(promotionDTO);
        return "redirect:/promotion";
    }
}
