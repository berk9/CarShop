package com.tusofia.project.carshop.service;

import com.tusofia.project.carshop.database.entity.Order;
import com.tusofia.project.carshop.database.entity.User;
import com.tusofia.project.carshop.database.repository.OrderRepository;
import com.tusofia.project.carshop.dto.binding.OrderBindingModel;
import com.tusofia.project.carshop.dto.view.OrderDetailsViewModel;
import com.tusofia.project.carshop.dto.view.OrderHistoryViewModel;
import com.tusofia.project.carshop.dto.view.CarDetailsViewModel;
import com.tusofia.project.carshop.event.ApprovedOrderPublisher;
import com.tusofia.project.carshop.exception.OrderNotFoundException;
import com.tusofia.project.carshop.util.TimeUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ApprovedOrderPublisher orderPublisher;
    private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;

    public OrderService(OrderRepository orderRepository, ApprovedOrderPublisher orderPublisher, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.orderPublisher = orderPublisher;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Order addOrderForApproval(OrderBindingModel orderBindingModel){
        Order pendingOrder = this.modelMapper.map(orderBindingModel, Order.class);
        pendingOrder.setApproved(false);
        pendingOrder.setCustomer(this.modelMapper.map(orderBindingModel.getCustomer(), User.class));
        this.orderRepository.save(pendingOrder);
        return pendingOrder;
    }

    @Transactional
    public void declineOrder(Long orderId){
        Order order  = this.orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with this id does not exist!"));

        order.setApproved(true);
        order.setSuccessful(false);

        this.orderRepository.save(order);
    }

    @Transactional
    public void confirmOrder(Long orderId, String waitingTime){
        Order order = this.orderRepository
                .findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with this id does not exist!"));

        order.setApproved(true);
        order.setSuccessful(true);
        order.setWaitingTime(LocalDate.parse(waitingTime));

        this.orderRepository.save(order);
    }

    public List<OrderBindingModel> findAllOrdersForApproval(){
        return this.findAll()
                .stream()
                .filter(o -> o.getApproved().equals(false))
                .collect(Collectors.toList());
    }

    public List<OrderBindingModel> findAll() {
        return this.orderRepository.findAll()
                .stream()
                .map(o -> this.modelMapper.map(o, OrderBindingModel.class))
                .collect(Collectors.toList());
    }

    public List<OrderHistoryViewModel> findOrdersByCustomer(String username) {
         return this.orderRepository.findAll()
                .stream()
                .filter(o -> o.getCustomer().getUsername().equals(username))
                .filter(o -> o.getApproved().equals(true)) //the order should be approved in order to show in the history !
                .map(o -> this.modelMapper.map(o, OrderHistoryViewModel.class))
                .collect(Collectors.toList());
    }

    public OrderDetailsViewModel findOrderDetailsById(Long orderId){
        Order order = this.orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with this id has not been found!"));

        OrderDetailsViewModel orderDetailsViewModel = this.modelMapper.map(order, OrderDetailsViewModel.class);
        orderDetailsViewModel.setCars(
                order.getCars()
                .stream()
                .map(o -> this.modelMapper.map(o, CarDetailsViewModel.class))
                .collect(Collectors.toList())
        );
        //Checks if the customer has added some comment to the order, and if not, puts this comment below in order to give the employee additional info.
        if(orderDetailsViewModel.getComment().isEmpty()){
            orderDetailsViewModel.setComment("You do not have any comments about this order!");
        }

        return orderDetailsViewModel;
    }

    public OrderBindingModel findById(Long orderId) {
        return this.orderRepository.findById(orderId)
                .map(o -> this.modelMapper.map(o, OrderBindingModel.class))
                .orElseThrow(() -> new OrderNotFoundException("Order with this id has not been found!"));
    }
}

