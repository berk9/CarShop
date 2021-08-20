package com.tusofia.project.carshop.service;


import com.tusofia.project.carshop.dto.binding.CarBindingModel;
import com.tusofia.project.carshop.dto.binding.PromotionBindingModel;
import com.tusofia.project.carshop.dto.binding.OrderBindingModel;
import com.tusofia.project.carshop.dto.binding.auth.UserServiceModel;
import com.tusofia.project.carshop.dto.view.CarDetailsViewModel;
import com.tusofia.project.carshop.dto.view.CartCarViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class CartService {

    private final ModelMapper modelMapper;
    private final UserDetailsServiceImpl userDetailsService;
    private final PromotionService promotionService;

    public CartService(HttpSession httpSession, ModelMapper modelMapper, UserDetailsServiceImpl userDetailsService, PromotionService promotionService) {
        this.modelMapper = modelMapper;
        this.userDetailsService = userDetailsService;
        this.promotionService = promotionService;
    }

    public void initializeCart(HttpSession session) {
        if (session.getAttribute("shopping-cart") == null) {
            session.setAttribute("shopping-cart", new LinkedList<>());
        }
    }

    public List<CartCarViewModel> retrieveCart(HttpSession session) {
        this.initializeCart(session);
        return (List<CartCarViewModel>) session.getAttribute("shopping-cart");
    }

    public void addOneCarToCart(CarBindingModel carDTO, HttpSession session){
        this.initializeCart(session);
        CarDetailsViewModel car = this.modelMapper
                .map(carDTO, CarDetailsViewModel.class);
        CartCarViewModel cartCarViewModel = new CartCarViewModel();
        cartCarViewModel.setCarDetailsViewModel(car);
        cartCarViewModel.setQuantity(1); //one because there is counter in addItemToCart method and it will collect all the same cars
        var cart = this.retrieveCart(session);
        this.addItemToCartView(cartCarViewModel, cart);
    }

    public void addOneCarToCart(CarBindingModel carDTO, int quantity , HttpSession session){
        this.initializeCart(session);
        CarDetailsViewModel car = this.modelMapper
                .map(carDTO, CarDetailsViewModel.class);
        CartCarViewModel cartCarViewModel = new CartCarViewModel();
        cartCarViewModel.setCarDetailsViewModel(car);
        cartCarViewModel.setQuantity(quantity); //one because there is counter in addItemToCart method and it will collect all the same cars
        var cart = this.retrieveCart(session);
        this.addItemToCartView(cartCarViewModel, cart);
    }

    public void addListOfCarsToCart(List<CarBindingModel> cars, HttpSession session){
        for (CarBindingModel carDTO : cars){
            addOneCarToCart(carDTO,session);
        }
    }

    public void addItemToCartView(CartCarViewModel cartCarViewModel, List<CartCarViewModel> cartCarViewModelList) {
        for (CartCarViewModel item : cartCarViewModelList) {
            if (item.getCarDetailsViewModel().getId().equals(cartCarViewModel.getCarDetailsViewModel().getId())) {
                item.setQuantity(item.getQuantity() + cartCarViewModel.getQuantity());
                return;
            }
        }
        cartCarViewModelList.add(cartCarViewModel);
    }

    public BigDecimal calcTotal(List<CartCarViewModel> cart) {
        BigDecimal result = new BigDecimal(0);
        for (CartCarViewModel item : cart) {
            result = result.add(item.getCarDetailsViewModel().getPrice().multiply(new BigDecimal(item.getQuantity())));
        }
        return result;
    }

    public void removeItemFromCart(Long id, List<CartCarViewModel> cart) {
        cart.removeIf(c -> c.getCarDetailsViewModel().getId().equals(id));
    }

    public OrderBindingModel prepareOrder(List<CartCarViewModel> cart, String customer, String comment, BigDecimal price) {
        OrderBindingModel orderBindingModel = this.mapToOrder(customer,comment,price);
        List<CarBindingModel> cars = new ArrayList<>();
        for (CartCarViewModel item : cart) {
            CarBindingModel carDTO = this.modelMapper.map(item.getCarDetailsViewModel(), CarBindingModel.class);

            for (int i = 0; i < item.getQuantity(); i++) {
                cars.add(carDTO);
            }
        }
        orderBindingModel.setCars(cars);
        return orderBindingModel;
    }

    //should check here if i remove the price from constructor and just add it from the promotion ;)
    public OrderBindingModel prepareOrderFromPromotion(Long promotionId, String comment, BigDecimal price, String customer){
        OrderBindingModel orderBindingModel = this.mapToOrder(customer,comment,price);
        PromotionBindingModel promotion = this.promotionService.findById(promotionId);
        orderBindingModel.setCars(promotion.getCars());
        return orderBindingModel;
    }

    //I think that this should not be here.
    public boolean checkIfEmailConfirmed(String username){
        return this.userDetailsService.loadUserByUsername(username).getEmailConfirmed();
    }

    private OrderBindingModel mapToOrder(String customer, String comment, BigDecimal price){
        OrderBindingModel orderBindingModel = new OrderBindingModel();
        orderBindingModel.setComment(comment);
        orderBindingModel.setTotalPrice(price);
        orderBindingModel.setCustomer(this.modelMapper.map(this.userDetailsService.loadUserByUsername(customer), UserServiceModel.class));
        return orderBindingModel;
    }
}
