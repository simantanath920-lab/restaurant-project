package com.simanta.restaurant_backend.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simanta.restaurant_backend.model.Category;
import com.simanta.restaurant_backend.model.Menu;

@Repository
public interface  MenuRepository extends  JpaRepository<Menu,Long>{

    List<Menu> findByIsAvailableTrue();
    List<Menu> findByCategoryAndIsAvailableTrue(Category category);
    List<Menu> findByIsVegAndIsAvailableTrue(Boolean isVeg);
    Boolean existsByNameIgnoreCase(String name);
}
