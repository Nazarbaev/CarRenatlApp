package com.example.SecurityApp.repository;

import com.example.SecurityApp.models.Brand;
import com.example.SecurityApp.models.Car;
import com.example.SecurityApp.models.Category;
import com.example.SecurityApp.models.Person;
import com.example.SecurityApp.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class CarRepositoryTest {


    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CarRepository carRepository;

    @Test
    public void findByCategoryAndOwnerIsNullTest() {
        Category category = new Category();
        category.setName("Sedan");
        Brand brand = new Brand();
        brand.setBrandName("Toyota");




        category = entityManager.persistAndFlush(category);
        entityManager.persist(brand);
        Car car = new Car();
        car.setCategory(category);
        car.setType("Camry");
        car.setDescription("Fast car");
        car.setBrand(brand);
        car.setStartDate("2023-05-23");
        car.setEndDate("2023-05-23");




        car = entityManager.persistAndFlush(car);

        List<Car> cars = carRepository.findByCategoryAndOwnerIsNull(category);

        assertThat(cars).isNotEmpty();
        assertThat(cars.get(0)).isEqualTo(car);
    }
    @Test
    public void testFindAllById() {
        Category category = new Category();
        category.setName("Sedan");
        Brand brand = new Brand();
        brand.setBrandName("Toyota");

        entityManager.persistAndFlush(category);
        entityManager.persist(brand);

        Car car = new Car();
        car.setCategory(category);
        car.setBrand(brand);

        car.setType("Corolla");
        car.setDescription("small car with AC, 1.6 engine");
        car.setStartDate("2023-05-23");
        car.setEndDate("2023-05-26");
        // Set car properties...

        Car savedCar = carRepository.save(car);

        Optional<Car> foundCar = carRepository.findAllById(savedCar.getId());

        assertThat(foundCar).isPresent();
        assertThat(foundCar.get()).isEqualTo(savedCar);
    }
    @Test
    public void testFindAllByOwner() {
        Category category = new Category();
        category.setName("Sedan");
        entityManager.persistAndFlush(category);
        Person person = new Person();
        person.setUsername("Sam");
        person.setRole("ROLE_USER");
        person.setEmail("sam@gmail.com");
        person.setPassword("qweqweqw");
        entityManager.persist(person);

        Brand brand = new Brand();
        brand.setBrandName("BMW");

        entityManager.persist(brand);

        Car car1 = new Car();
        car1.setType("f20");
        car1.setDescription("black car with AC, 1.6 engine");
        car1.setStartDate("2023-05-23");
        car1.setEndDate("2023-05-26");
        car1.setBrand(brand);
        car1.setOwner(person);
        car1.setCategory(category);
        Car savedCar1 = carRepository.save(car1);


        Car car2 = new Car();
        car2.setType("ssd");
        car2.setDescription("white car with AC, 1.6 engine");
        car2.setStartDate("2023-05-23");
        car2.setEndDate("2023-05-26");
        car2.setBrand(brand);
        car2.setOwner(person);
        car2.setCategory(category);
        entityManager.persist(car2);
        Car savedCar2 = carRepository.save(car2);

        List<Car> cars = carRepository.findAllByOwner(person);

        assertThat(cars).isNotNull();
        assertThat(cars).hasSize(2);
        assertThat(cars).contains(savedCar1, savedCar2);
    }

    // Add more tests for other methods in the CarRepository
}
