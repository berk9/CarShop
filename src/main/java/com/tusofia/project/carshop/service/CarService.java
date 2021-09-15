package com.tusofia.project.carshop.service;

import com.tusofia.project.carshop.database.entity.car.Brand;
import com.tusofia.project.carshop.database.entity.car.Car;
import com.tusofia.project.carshop.database.entity.Category;
import com.tusofia.project.carshop.database.entity.car.FuelType;
import com.tusofia.project.carshop.database.repository.CarRepository;
import com.tusofia.project.carshop.dto.binding.CarBindingModel;
import com.tusofia.project.carshop.dto.binding.CarDetailsBindingModel;
import com.tusofia.project.carshop.exception.CarNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public List<CarBindingModel> findRecommendedCars(CarDetailsBindingModel bindingModel) {
        return  filterCar(carRepository.findAll().stream(), bindingModel)
                .map(car -> this.modelMapper.map(car, CarBindingModel.class))
                .collect(Collectors.toList());
    }

    private Stream<Car> filterCar(Stream<Car> carStream, CarDetailsBindingModel bindingModel) {
        return carStream.filter(car -> filterCarType(car, bindingModel))
                .filter(car -> filterCategory(car, bindingModel))
                .filter(car -> filterBrand(car, bindingModel))
                .filter(car -> filterFuel(car, bindingModel));
    }

    private boolean filterCarType(Car car, CarDetailsBindingModel bindingModel){
        return car.getCarDetails().getCarType().equals(bindingModel.getCarType());
    }

    private boolean filterCategory(Car car, CarDetailsBindingModel bindingModel){
        return car.getCategory().getName().equals(bindingModel.getCategoryType().getCategoryName());
    }

    private boolean filterBrand(Car car, CarDetailsBindingModel bindingModel){
        if(isBrandChecked(bindingModel)){
            return car.getCarDetails().getBrand().equals(bindingModel.getBrand());
        }
        return true;
    }

    private boolean isBrandChecked(CarDetailsBindingModel model){
        return !model.getBrand().equals(Brand.OTHER);
    }

    private boolean filterFuel(Car car, CarDetailsBindingModel bindingModel){
        if(isFuelChecked(bindingModel)){
            return car.getCarDetails().getFuelType().equals(bindingModel.getFuelType());
        }
        return true;
    }

    private boolean isFuelChecked(CarDetailsBindingModel model){
        return !model.getFuelType().equals(FuelType.NOT_INTERESTED);
    }

}
