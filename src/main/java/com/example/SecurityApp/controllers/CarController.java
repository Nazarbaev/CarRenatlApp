package com.example.SecurityApp.controllers;

import com.example.SecurityApp.models.Car;
import com.example.SecurityApp.models.Category;
import com.example.SecurityApp.service.CarService;
import com.example.SecurityApp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

import java.util.List;

@Controller

public class CarController {


    private final CarService carService;
    private final CategoryService categoryService;

     @Autowired
    public CarController(CarService carService, CategoryService categoryService) {
        this.carService = carService;
         this.categoryService = categoryService;
     }

    @GetMapping("/category/{categoryName}")  //@PathVariable("id") int id
    public String showCarListByCategory(@PathVariable("categoryName") String categoryName, Model model) throws IOException {

        Category category = categoryService.getCategoryByName(categoryName);
        if (category == null) {
            // handle category not found error
            return "Could find this kind of category";
        }
        List<Car> cars = carService.getCarsByCategory(category);




        model.addAttribute("cars", cars);
        model.addAttribute("car",new Car());

        return "user/categoryList";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@ModelAttribute("car")   Car selectedCar, @PathVariable("id") int id) {

        carService.assign(id,selectedCar);

        return "redirect:/myList";
    }


    @GetMapping("/myList")
    public String showCarListByCategory( Model model) throws IOException {


        List<Car> cars = carService.getCarsByOwner();


        model.addAttribute("cars", cars);


        return "user/myList";
    }

    @PatchMapping("/{id}/release")
    public String deleteCarFromMyList(@PathVariable("id") int id) {
        carService.release(id);
        return "redirect:/myList";
    }


}
