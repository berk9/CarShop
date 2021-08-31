package com.tusofia.project.carshop.service;

import com.tusofia.project.carshop.database.entity.car.Brand;
import com.tusofia.project.carshop.database.entity.car.Car;
import com.tusofia.project.carshop.database.entity.Category;
import com.tusofia.project.carshop.database.repository.CarRepository;
import com.tusofia.project.carshop.dto.binding.CarBindingModel;
import com.tusofia.project.carshop.dto.binding.CarRecommendationBindingModel;
import com.tusofia.project.carshop.exception.CarNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    public CarService(CarRepository carRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Car createCar(CarBindingModel carBindingModel) {
        Car car = this.modelMapper.map(carBindingModel, Car.class);
        return this.carRepository.save(car);
    }

    @Transactional
    public Car activateCar(Long activateId) {
        Car car = this.carRepository.findById(activateId)
                .orElseThrow(() -> new CarNotFoundException("No car with the given id was found!"));

        if (car.getActivity()) {
            car.setActivity(false);
        } else car.setActivity(true);

        this.carRepository.save(car);
        return car;
    }

    @Transactional
    public void editCar(Long carId, CarBindingModel carDTO) {
        Car car = this.carRepository
                .findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Car with this id was not found!"));

        car.setName(carDTO.getName());
        car.setActivity(carDTO.getActivity());
        car.setDescription(carDTO.getDescription());
        car.setPrice(carDTO.getPrice());
        car.setImgSrc(carDTO.getImgSrc());
        car.setCategory(this.modelMapper.map(carDTO.getCategory(), Category.class));
        this.carRepository.save(car);
    }

    //On the other hand, hardDelete method deletes the whole object without chance of putting the object back to our project.
    //Added @PreRemove method in entity class, in order to delete the object from another tables as well.
    @Transactional
    public void hardDelete(Long carId) {
        this.carRepository.deleteById(carId);
    }

    public List<CarBindingModel> findAll() {
        return this.carRepository.
                findAll()
                .stream()
                .map(car -> this.modelMapper.map(car, CarBindingModel.class))
                .collect(Collectors.toList());
    }

    public List<CarBindingModel> findAllByCategory(String categoryName) {
        return this.findAll()
                .stream()
                .filter(car -> car.getCategory().getName().equalsIgnoreCase(categoryName))
                .collect(Collectors.toList());
    }

    public CarBindingModel findById(Long carId) {
        return this.carRepository.findById(carId)
                .map(car -> this.modelMapper.map(car, CarBindingModel.class))
                .orElseThrow(() -> new CarNotFoundException("Car with this id was not found"));
    }

    public List<CarBindingModel> findRecommendedCars(CarRecommendationBindingModel bidingModel) {
        return carRepository.findAll()
                .stream()
                .filter(car -> car.getCarDetails().getCarType()
                        .equals(bidingModel.getCarDetailsBindingModel().getCarType()))
                .filter(car -> car.getCategory().equals(bidingModel.getCategory()))
                .filter(car -> car.getCarDetails().getBrand().equals(bidingModel.getCarDetailsBindingModel().getBrand()))
                .map(car -> this.modelMapper.map(car, CarBindingModel.class))
                .collect(Collectors.toList());
    }

    private boolean isBrandChecked(CarRecommendationBindingModel model){
        return model.getCarDetailsBindingModel().getBrand().equals(Brand.OTHER);
    }

    //TODO: you can use the forEach() function from the stream and use manually created methods to filter
    //TODO: also you can add NONE value in the types and check weather the client is interested in filtering this value

}
