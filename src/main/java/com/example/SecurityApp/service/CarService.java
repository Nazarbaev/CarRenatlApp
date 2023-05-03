package com.example.SecurityApp.service;

import com.example.SecurityApp.models.Brand;
import com.example.SecurityApp.models.Car;
import com.example.SecurityApp.models.Category;
import com.example.SecurityApp.models.Person;
import com.example.SecurityApp.repository.BrandRepository;
import com.example.SecurityApp.repository.CarRepository;
import com.example.SecurityApp.repository.CategoryRepository;
import com.example.SecurityApp.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CarService {


    private CarRepository carRepository;



    private PeopleRepository peopleRepository;

    private BrandRepository brandRepository;
    private CategoryRepository categoryRepository;


    @Autowired
    public CarService(CarRepository carRepository, PeopleRepository peopleRepository, BrandRepository brandRepository, CategoryRepository categoryRepository) {
        this.carRepository = carRepository;
        this.peopleRepository = peopleRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Car> getCarsByCategory(Category category) {
        return carRepository.findByCategoryAndOwnerIsNull(category);


    }


    @Transactional
    public void saveCar(Car car, Brand brand, Category category) {

        Category category1 =categoryRepository.findByName(category.getName()).get();
        Brand brand1 = brandRepository.findByBrandName(brand.getBrandName()).get();

         car.setBrand(brand1);
         car.setCategory(category1);

         String photo = car.getPhoto();

         car.setPhoto("/images/" + photo );

        carRepository.save(car);
    }

    @Transactional
    public void update(Car c1,Brand b1, Category cat){



        Category category1 =categoryRepository.findByName(cat.getName()).get();
        Brand brand1 = brandRepository.findByBrandName(b1.getBrandName()).get();

        c1.setBrand(brand1);
        c1.setCategory(category1);

        String photo = c1.getPhoto();

        c1.setPhoto("/images/" + photo );

        carRepository.save(c1);

    }



    public void deleteCar(Car car) {


        carRepository.delete(car);
    }

     @Transactional
    public void deleteCarById(int id){

        carRepository.deleteById(id);
    }

    public List<Car> getCarsByOwner(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        Person  CurrentUser = peopleRepository.findByUsername(currentUserName).get();
        // Get the user object from the authentication object

        List<Car> cars =  carRepository.findAllByOwner(CurrentUser);

        cars.forEach(car -> {
            String dateStr = car.getEndDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateStr, formatter);
            LocalDate today = LocalDate.now();

            if (today.isAfter(date)) {
                car.setExpired(true);
            }

        });
        return cars;


    }

    public List<Car> getCarsByOwnerId(int id){
        Person person = peopleRepository.findById(id).get();

        List<Car> cars = carRepository.findAllByOwner(person);

        cars.forEach(car -> {
            String dateStr = car.getEndDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateStr, formatter);
            LocalDate today = LocalDate.now();

            if (today.isAfter(date)) {
                car.setExpired(true);
            }

        });
       return cars;
    }


    @Transactional
    public void assign(Integer id, Car bookedCar) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

         Person  CurrentUser = peopleRepository.findByUsername(currentUserName).get();
        // Get the user object from the authentication object




        carRepository.findAllById(id).ifPresent(
                car -> {
                    car.setOwner(CurrentUser);
                    car.setStartDate(bookedCar.getStartDate());
                    car.setEndDate(bookedCar.getEndDate());
                }
        );









    }
    @Transactional
    public void release(int id){
        carRepository.findAllById(id).ifPresent(
                car -> {
                    car.setOwner(null);
                    car.setStartDate(null);
                    car.setEndDate(null);

        });
    }

    public Car findAllById(int id){
       return carRepository.findAllById(id).get();
    }


}
