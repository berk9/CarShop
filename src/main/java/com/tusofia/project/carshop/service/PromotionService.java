package com.tusofia.project.carshop.service;

import com.tusofia.project.carshop.database.entity.Car;
import com.tusofia.project.carshop.database.entity.Promotion;
import com.tusofia.project.carshop.database.repository.PromotionRepository;
import com.tusofia.project.carshop.dto.binding.PromotionAddBindingModel;
import com.tusofia.project.carshop.dto.binding.PromotionBindingModel;
import com.tusofia.project.carshop.exception.PromotionNotFoundException;
import com.tusofia.project.carshop.util.TimeUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final CarService carService;
    private final ModelMapper modelMapper;

    public PromotionService(PromotionRepository promotionRepository, CarService carService, ModelMapper modelMapper) {
        this.promotionRepository = promotionRepository;
        this.carService = carService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Promotion createPromotion(PromotionAddBindingModel promotionDTO) {
        BigDecimal oldPrice = new BigDecimal(0);
        Promotion promotion = this.modelMapper.map(promotionDTO, Promotion.class);
        promotion.setValidUntil(TimeUtil.parseDateToTime(promotionDTO.getValidUntil()));
        List<Car> cars = new ArrayList<>();

        for(String carId: promotionDTO.getCars()){
            Car car = this.modelMapper.map(
                    this.carService.findById(Long.parseLong(carId)), Car.class);
            oldPrice = oldPrice.add(car.getPrice()); //collecting the prices from all the cars
            cars.add(car);
        }
        promotion.setCars(cars);
        promotion.setOldPrice(oldPrice);
        this.promotionRepository.save(promotion);
        return promotion;
    }

    //On the other hand, hardDelete method deletes the whole object without chance of putting the object back to our project.
    @Transactional
    public void hardDelete(Long carId) {
        this.promotionRepository.deleteById(carId);
    }

    public List<PromotionBindingModel> findAllValidPromotions(){
        return this.promotionRepository.findAll()
                .stream()
                .filter(promotion -> promotion.getValidUntil().isAfter(LocalDateTime.now()))
//                .filter(offer -> offer.getProducts()
//                        .stream()
//                        .filter(p -> p.getActivity().equals(true))
//                        .collect(Collectors.toList()))
                .map(promotion -> this.modelMapper.map(promotion, PromotionBindingModel.class))
                .collect(Collectors.toList());
    }

    public PromotionBindingModel findById(Long promotionId){
        return this.promotionRepository.findById(promotionId)
                .map(promotion -> this.modelMapper.map(promotion, PromotionBindingModel.class))
                .orElseThrow(() -> new PromotionNotFoundException("Promotion with this id was not found"));
    }

}
