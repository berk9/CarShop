package com.tusofia.project.carshop;

import com.tusofia.project.carshop.database.entity.Category;
import com.tusofia.project.carshop.database.entity.Role;
import com.tusofia.project.carshop.database.entity.User;
import com.tusofia.project.carshop.database.entity.car.*;
import com.tusofia.project.carshop.database.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Component
public class CarShopApplicationInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CarDetailsRepository carDetailsRepository;
    private final CarRepository carRepository;

    public CarShopApplicationInit(UserRepository userRepository, CategoryRepository categoryRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, CarDetailsRepository carDetailsRepository, CarRepository carRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.carDetailsRepository = carDetailsRepository;
        this.carRepository = carRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if(carDetailsRepository.count() == 0) {

            Category sedan = new Category();
            sedan.setName("Sedans");
            Category suv = new Category();
            suv.setName("SUVs and Crossovers");
            Category station = new Category();
            station.setName("Station Wagons");
            Category hatchback = new Category();
            hatchback.setName("Hatchbacks");

            categoryRepository.saveAndFlush(sedan);
            categoryRepository.saveAndFlush(suv);
            categoryRepository.saveAndFlush(station);
            categoryRepository.saveAndFlush(hatchback);

            CarDetails carDetailsRS6 = new CarDetails();
            carDetailsRS6.setCarType(CarType.SPORT);
            carDetailsRS6.setBrand(Brand.AUDI);
            carDetailsRS6.setEngineType(EngineType.PETROL);
            carDetailsRS6.setCreatedOn(LocalDateTime.now());

            Car car = new Car();
            car.setActivity(true);
            car.setCategory(station);
            car.setPrice(BigDecimal.valueOf(500000));
            car.setDescription("The best station wagon in the world.");
            car.setImgSrc("images/cars/audi-rs6-station.png");
            car.setCarDetails(carDetailsRS6);
            car.setName("Audi RS6");
            car.setCreatedOn(LocalDateTime.now());

            CarDetails carDetailsRS3 = new CarDetails();
            carDetailsRS3.setCarType(CarType.SPORT);
            carDetailsRS3.setCreatedOn(LocalDateTime.now());
            carDetailsRS3.setBrand(Brand.AUDI);
            carDetailsRS3.setEngineType(EngineType.PETROL);

            Car car2 = new Car();
            car2.setActivity(true);
            car2.setCategory(station);
            car2.setPrice(BigDecimal.valueOf(300000));
            car2.setDescription("The best station wagon in the world (smaller).");
            car2.setImgSrc("images/cars/audi-rs6-station.png");
            car2.setCarDetails(carDetailsRS3);
            car2.setName("Audi RS3");
            car2.setCreatedOn(LocalDateTime.now());

            carDetailsRepository.saveAndFlush(carDetailsRS3);
            carDetailsRepository.saveAndFlush(carDetailsRS6);

            carRepository.saveAndFlush(car);
            carRepository.saveAndFlush(car2);
        }

        if(userRepository.count() == 0){


            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            Role customerRole = new Role();
            customerRole.setName("ROLE_CUSTOMER");
            roleRepository.save(customerRole);

            Role employeeRole = new Role();
            employeeRole.setName("ROLE_EMPLOYEE");
            roleRepository.save(employeeRole);


            //---- ADMIN ----
            User admin = new User();
            admin.setEmail("admin@example.com");
            admin.setUsername("admin");
            admin.setName("admin");
            admin.setSurname("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setCity("Sofia");
            admin.setAddress("Studentski grad 59");

            admin.setRoles(Set.of(adminRole, customerRole, employeeRole));
            userRepository.save(admin);

            // ---- CUSTOMER ------
            User customer = new User();
            customer.setEmail("customer@example.com");
            customer.setUsername("customer");
            customer.setName("customer");
            customer.setSurname("customer");
            customer.setPassword(passwordEncoder.encode("customer"));
            customer.setCity("Sofia");
            customer.setAddress("Studentski grad 59");

            customer.setRoles(Set.of(customerRole));
            userRepository.save(customer);


            // ----- EMPLOYEE ------
            User employee = new User();
            employee.setEmail("employee@example.com");
            employee.setUsername("employee");
            employee.setName("employee");
            employee.setSurname("employee");
            employee.setPassword(passwordEncoder.encode("employee"));
            employee.setCity("Sofia");
            employee.setAddress("Studentski grad 59");

            employee.setRoles(Set.of(employeeRole, customerRole));
            userRepository.save(employee);
        }

    }
}
