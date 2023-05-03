package com.example.SecurityApp.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "car_brand_table")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "Brand name cannot be blank")
    @Column(name = "brand")
    private String brandName;

    @OneToMany(mappedBy = "brand")
    private List<Car> cars;

    public Brand() {

    }

    public Brand(int id, String brandName) {
        this.id = id;
        this.brandName = brandName;
    }

    // getters and setters for all fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    // toString method
    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", name='" + brandName + '\'' +
                '}';
    }
}
