package com.tusofia.project.carshop.database.repository;

import com.tusofia.project.carshop.database.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
