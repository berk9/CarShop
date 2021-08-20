package com.tusofia.project.carshop.database.repository;

import com.tusofia.project.carshop.database.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
