package com.tusofia.project.carshop;

import com.tusofia.project.carshop.database.entity.Category;
import com.tusofia.project.carshop.database.entity.Role;
import com.tusofia.project.carshop.database.entity.User;
import com.tusofia.project.carshop.database.repository.CategoryRepository;
import com.tusofia.project.carshop.database.repository.RoleRepository;
import com.tusofia.project.carshop.database.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CarShopApplicationInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public CarShopApplicationInit(UserRepository userRepository, CategoryRepository categoryRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if(categoryRepository.count() ==0){
            Category sedan = new Category();
            sedan.setName("Sedans");
            Category suv = new Category();
            suv.setName("SUVs and Crossovers");
            Category station = new Category();
            station.setName("Station Wagons");
            Category hatchback = new Category();
            hatchback.setName("Hatchbacks");

            categoryRepository.save(sedan);
            categoryRepository.save(suv);
            categoryRepository.save(station);
            categoryRepository.save(hatchback);
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
            admin.setEmailConfirmed(true);
            admin.setPhoneNumberConfirmed(false);
            admin.setName("admin");
            admin.setSurname("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setCity("Asenovgrad");
            admin.setAddress("Marica 11");

            admin.setRoles(Set.of(adminRole, customerRole, employeeRole));
            userRepository.save(admin);

            // ---- CUSTOMER ------
            User customer = new User();
            customer.setEmail("customer@example.com");
            customer.setUsername("customer");
            customer.setEmailConfirmed(false);
            customer.setPhoneNumberConfirmed(false);
            customer.setName("customer");
            customer.setSurname("customer");
            customer.setPassword(passwordEncoder.encode("customer"));
            customer.setCity("Asenovgrad");
            customer.setAddress("Marica 11");

            customer.setRoles(Set.of(customerRole));
            userRepository.save(customer);


            // ----- EMPLOYEE ------
            User employee = new User();
            employee.setEmail("employee@example.com");
            employee.setUsername("employee");
            employee.setEmailConfirmed(false);
            employee.setPhoneNumberConfirmed(false);
            employee.setName("employee");
            employee.setSurname("employee");
            employee.setPassword(passwordEncoder.encode("employee"));
            employee.setCity("Asenovgrad");
            employee.setAddress("Marica 11");

            employee.setRoles(Set.of(employeeRole, customerRole));
            userRepository.save(employee);
        }

    }
}
