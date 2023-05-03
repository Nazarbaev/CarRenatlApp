package com.example.SecurityApp.controllers;

import com.example.SecurityApp.models.Brand;
import com.example.SecurityApp.models.Car;
import com.example.SecurityApp.models.Category;
import com.example.SecurityApp.service.BrandService;
import com.example.SecurityApp.service.CarService;
import com.example.SecurityApp.service.CategoryService;
import com.example.SecurityApp.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PeopleService peopleService;

    private final CarService carService;

    private final BrandService brandService;

    private final CategoryService categoryService;
     @Autowired
    public AdminController(PeopleService peopleService, CarService carService, BrandService brandService, CategoryService categoryService) {
        this.peopleService = peopleService;
         this.carService = carService;
         this.brandService = brandService;
         this.categoryService = categoryService;
     }

    @GetMapping()
    public String adminPage(Model model){
         model.addAttribute("people",peopleService.showAll("ROLE_USER"));
        return "admin/admin";
    }


    @GetMapping("/{categoryName}")  //@PathVariable("id") int id
    public String showCarListByCategory(@PathVariable("categoryName") String categoryName, Model model) throws IOException {

        Category category = categoryService.getCategoryByName(categoryName);


        if (category== null) {
            // handle category not found error
            return "Could find this kind of category";
        }
        List<Car> cars = carService.getCarsByCategory(category);




        model.addAttribute("cars", cars);
        model.addAttribute("car",new Car());

        return "admin/adminList";
    }

    @GetMapping("/newCar")
    public String newBook(@ModelAttribute("book") Car car,Model model){
        model.addAttribute("car",car);
        model.addAttribute("brands",brandService.getAllBrands());
        model.addAttribute("catigories", categoryService.getAllCategories());
        model.addAttribute("brand",new Brand());
        model.addAttribute("category",new Category());
        return "admin/newCar";
    }
    @PostMapping("/newCar")
    public String createCar(@ModelAttribute("car")  Car car,
                            @ModelAttribute("brand") Brand brand,
                            @ModelAttribute("category") Category category){






       carService.saveCar(car,brand,category);

         return "redirect:newCar";
    }

    @GetMapping("/{id}/userList")
    public String show(@PathVariable("id") int id, Model model){
         model.addAttribute("cars",carService.getCarsByOwnerId(id));
          return "admin/show";
    }

    @PatchMapping("/{id}/release")
    public String releaseUserCar(@PathVariable("id") int id){
         carService.release(id);
         return "redirect:/admin";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/admin";
    }


    @DeleteMapping("/admin/{id}/delete")
    public String deleteCar(@PathVariable("id") int id) {
        carService.deleteCarById(id);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/show")
    public  String showCar(@PathVariable("id") int id,Model model){
        model.addAttribute("car",carService.findAllById(id));
        model.addAttribute("brands",brandService.getAllBrands());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("brand",new Brand());
        model.addAttribute("category",new Category());
         return "admin/editCar";
    }

    @PatchMapping("/{id}/editCar")
    public String editCar(@PathVariable("id") int id,Car car,Brand brand,Category category ){


        carService.update(car,brand,category);


         return "redirect:/admin";

    }


}
