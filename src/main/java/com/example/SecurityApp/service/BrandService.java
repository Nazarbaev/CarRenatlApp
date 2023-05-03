package com.example.SecurityApp.service;

import com.example.SecurityApp.models.Brand;
import com.example.SecurityApp.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BrandService {



    private BrandRepository brandRepository;


    @Autowired
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Brand> getAllBrands(){
       return brandRepository.findAll();
    }


}
