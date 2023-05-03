package com.example.SecurityApp.repository;

import com.example.SecurityApp.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand,Integer> {
  Optional<Brand> findByBrandName(String name);
}
