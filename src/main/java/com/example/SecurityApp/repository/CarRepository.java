package com.example.SecurityApp.repository;

import com.example.SecurityApp.models.Brand;
import com.example.SecurityApp.models.Car;
import com.example.SecurityApp.models.Category;
import com.example.SecurityApp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car,Integer> {

   List<Car> findByCategoryAndOwnerIsNull(Category category);

   Optional<Car>  findAllById(int id);

   List<Car> findAllByOwner(Person person);


}
