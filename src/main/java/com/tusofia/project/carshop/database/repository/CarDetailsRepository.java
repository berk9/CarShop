package com.tusofia.project.carshop.database.repository;

import com.tusofia.project.carshop.database.entity.car.CarDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDetailsRepository extends JpaRepository<CarDetails, Long> {
}
