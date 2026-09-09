package com.simanta.restaurant_backend.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simanta.restaurant_backend.model.Address;
import com.simanta.restaurant_backend.model.User;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long>{

    List<Address> findByUserId(Long id);
    Optional<Address> findByIdAndUser(Long addressId, User user);
    boolean existsByUserId(Long userId);
    Optional<Address> findByUserAndIsDefaultTrue(User user);
}
