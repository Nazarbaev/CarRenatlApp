package com.example.SecurityApp.repository;

import com.example.SecurityApp.models.Car;
import com.example.SecurityApp.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    Optional<Category> findByName(String name);
}
