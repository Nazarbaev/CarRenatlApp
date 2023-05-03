package com.example.SecurityApp.service;

import com.example.SecurityApp.models.Category;
import com.example.SecurityApp.models.Person;
import com.example.SecurityApp.repository.CarRepository;
import com.example.SecurityApp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    private CategoryRepository categoryRepository;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategoryByName(String name){
        Optional<Category> category = categoryRepository.findByName(name);
        if(category.isEmpty()){
            throw new UsernameNotFoundException("User not found!");
        }
        return category.orElse(null);

    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
}
