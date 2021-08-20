package com.tusofia.project.carshop.web.controller;

import com.tusofia.project.carshop.dto.binding.OrderBindingModel;
import com.tusofia.project.carshop.service.CartService;
import com.tusofia.project.carshop.service.PromotionService;
import com.tusofia.project.carshop.service.OrderService;
import com.tusofia.project.carshop.service.CarService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.security.Principal;

@Controller
@RequestMapping("/cart")
@PreAuthorize("hasRole('CUSTOMER')")
public class CartController {

    private final CarService carService;
    private final PromotionService promotionService;
    private final OrderService orderService;
    private final CartService cartService;

    public CartController(CarService carService, PromotionService promotionService, OrderService orderService, CartService cartService) {
        this.carService = carService;
        this.promotionService = promotionService;
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping("/details")
    public String cartDetails(Model model, HttpSession session) {
        var cart = this.cartService.retrieveCart(session);
        model.addAttribute("totalPrice", this.cartService.calcTotal(cart));
        return "cart/details";
    }

    @GetMapping("/details/promotion/{id}")
    public String cartPromotionDetails(@PathVariable("id") Long promotionId, Model model, HttpSession session){
        model.addAttribute("promotion", this.promotionService.findById(promotionId));
        return "promotionDetails";
    }

    @PostMapping("/addCar")
    public String addToCartConfirm(@ModelAttribute(name= "carId") Long carId, int quantity, HttpSession session) {
        this.cartService.addOneCarToCart(this.carService.findById(carId), quantity, session);
        return "redirect:/cars";
    }

    @PostMapping("/reOrder")
    public String reOrderConfirm(@ModelAttribute(name="orderId") Long orderId, HttpSession session) {
        OrderBindingModel orderBindingModel = this.orderService.findById(orderId);
        this.cartService.addListOfCarsToCart(orderBindingModel.getCars(), session);
        return "redirect:/cart/details";
    }

    @PostMapping("/removeCar")
    public String removeFromCartConfirm(@ModelAttribute(name="deleteId") Long deleteId, HttpSession session) {
        this.cartService.removeItemFromCart(deleteId, this.cartService.retrieveCart(session));
        return "redirect:/cart/details";
    }

    @PostMapping("/checkout")
    public String checkoutConfirm(String comment, BigDecimal price, HttpSession session, Principal principal) {
        var cart = this.cartService.retrieveCart(session);
//        if(!this.cartService.checkIfEmailConfirmed(principal.getName())){
//            return "redirect:/authentication/profile";
//        }

        OrderBindingModel orderBindingModel = this.cartService.prepareOrder(cart, principal.getName(), comment, price);
        this.orderService.addOrderForApproval(orderBindingModel);
        session.removeAttribute("shopping-cart");
        return "redirect:/home";
    }

    @PostMapping("/checkoutPromotion")
    public String checkoutConfirm(Long promotionId, String comment, BigDecimal price, Principal principal) {
        if(!this.cartService.checkIfEmailConfirmed(principal.getName())){
            return "redirect:/authentication/profile";
        }

        OrderBindingModel orderBindingModel = this.cartService.prepareOrderFromPromotion(promotionId, comment, price, principal.getName());
        this.orderService.addOrderForApproval(orderBindingModel);
        return "redirect:/home";
    }
}
