package com.tusofia.project.carshop.web.controller;

import com.tusofia.project.carshop.database.entity.car.Car;
import com.tusofia.project.carshop.dto.binding.CarBindingModel;
import com.tusofia.project.carshop.dto.binding.CarDetailsBindingModel;
import com.tusofia.project.carshop.service.CategoryService;
import com.tusofia.project.carshop.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/cars")
public class CarController {

    private static final Logger log = LoggerFactory.getLogger(CarController.class);
    private final CarService carService;
    private final CategoryService categoryService;

    public CarController(CarService carService, CategoryService categoryService) {
        this.carService = carService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String viewCars(Model model){
        model.addAttribute("sedans", this.carService.findAllByCategory("Sedans"));
        model.addAttribute("suvs", this.carService.findAllByCategory("SUVs and Crossovers"));
        model.addAttribute("stations", this.carService.findAllByCategory("Station Wagons"));
        model.addAttribute("hatchbacks", this.carService.findAllByCategory("Hatchbacks"));
        return "car/index";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addCar(CarBindingModel carBindingModel, Model model) {
        model.addAttribute("categoryTypes", this.categoryService.findAll());
        return "car/createCar";
    }

    @GetMapping("/recommend")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String getQuestions(CarDetailsBindingModel carBindingModel, Model model) {
        model.addAttribute("categoryTypes", this.categoryService.findAll());
        return "carDetails/quiz";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editCar(@PathVariable("id") Long carId, Model model) {
        model.addAttribute("carEditForm", this.carService.findById(carId));
        model.addAttribute("categoryTypes", this.categoryService.findAll());
        return "car/edit";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String save
    (@Valid CarBindingModel carBindingModel,
     BindingResult bindingResult,
     Model model) {

        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getAllErrors().toString());
            model.addAttribute("categoryTypes", this.categoryService.findAll());
            return "car/createCar";
        }

        this.carService.createCar(carBindingModel);
        return "redirect:/cars";
    }

    @PostMapping("/recommend")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String recommendCar
            (@Valid CarDetailsBindingModel carBindingModel,
             BindingResult bindingResult,
             Model model) {

        if (bindingResult.hasErrors()) {
            //TODO: Handle error and redirect to the form again
        }

        model.addAttribute("recommendedCars", this.carService.findRecommendedCars(carBindingModel));

        return "carDetails/recommendedCars";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editCarConfirm(@Valid @ModelAttribute("carEditForm") CarBindingModel carDTO,
                                 @PathVariable("id") Long id,
                                 BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "car/edit";
        }
        this.carService.editCar(id, carDTO);
        return "redirect:/cars";
    }

    @PostMapping("/activate")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public String activate(@ModelAttribute(name="activateId") Long activateId) {
        this.carService.activateCar(activateId);
        return "redirect:/cars";
    }

    //Does DeleteMapping works only on restful applications
    //IN HARD DELETE, WE DELETE THE WHOLE ENTITY OBJECT, WITHOUT CHANCE OF RETURNING IT BACK!
    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String hardDelete(@ModelAttribute(name="deleteId") Long deleteId) {
        this.carService.hardDelete(deleteId);
        return "redirect:/cars";
    }
}
