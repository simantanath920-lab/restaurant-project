package com.simanta.restaurant_backend.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simanta.restaurant_backend.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{

    boolean existsByNameIgnoreCase(String name);
    List<Category> findByIsAvailableTrue();
}
