package com.example.SecurityApp.service;



import com.example.SecurityApp.models.Brand;
import com.example.SecurityApp.models.Car;
import com.example.SecurityApp.models.Category;
import com.example.SecurityApp.models.Person;
import com.example.SecurityApp.repository.BrandRepository;
import com.example.SecurityApp.repository.CarRepository;
import com.example.SecurityApp.repository.CategoryRepository;
import com.example.SecurityApp.repository.PeopleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private CarRepository carRepository;
    @Mock
    private PeopleRepository peopleRepository;
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CarService carService;

    private Category category;
    private Brand brand;
    private Car car;
    private Person person;

    @BeforeEach
    public void setUp() {
        category = new Category();
        category.setName("Sedan");

        brand = new Brand();
        brand.setBrandName("Toyota");

        person = new Person();
        person.setUsername("testuser");

        car = new Car();
        car.setId(1);
        car.setBrand(brand);
        car.setCategory(category);
        car.setPhoto("/images/toyota.jpg");
        car.setStartDate("2023-05-02");
        car.setEndDate("2023-05-03");
    }

    @Test
    public void getCarsByCategoryTest() {
        List<Car> carList = new ArrayList<>();
        carList.add(car);
        when(carRepository.findByCategoryAndOwnerIsNull(category)).thenReturn(carList);

        List<Car> result = carService.getCarsByCategory(category);

        assertEquals(1, result.size());
        assertEquals(car, result.get(0));
        verify(carRepository).findByCategoryAndOwnerIsNull(category);
    }

    @Test
    public void saveCarTest() {
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(category));
        when(brandRepository.findByBrandName(brand.getBrandName())).thenReturn(Optional.of(brand));

        carService.saveCar(car, brand, category);

        verify(categoryRepository).findByName(category.getName());
        verify(brandRepository).findByBrandName(brand.getBrandName());
        verify(carRepository).save(car);
    }

    @Test
    public void updateTest() {
        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(category));
        when(brandRepository.findByBrandName(brand.getBrandName())).thenReturn(Optional.of(brand));

        carService.update(car, brand, category);

        verify(categoryRepository).findByName(category.getName());
        verify(brandRepository).findByBrandName(brand.getBrandName());
        verify(carRepository).save(car);
    }

    @Test
    public void deleteCarTest() {
        carService.deleteCar(car);

        verify(carRepository).delete(car);
    }

    @Test
    public void deleteCarByIdTest() {
        int id = 1;

        carService.deleteCarById(id);

        verify(carRepository).deleteById(id);
    }

    @Test
    public void getCarsByOwnerTest() {
        List<Car> carList = new ArrayList<>();

        carList.add(car);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(person.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(peopleRepository.findByUsername(person.getUsername())).thenReturn(Optional.of(person));
        when(carRepository.findAllByOwner(person)).thenReturn(carList);

        List<Car> result = carService.getCarsByOwner();

        assertEquals(1, result.size());
        assertEquals(car, result.get(0));
        verify(peopleRepository).findByUsername(person.getUsername());
        verify(carRepository).findAllByOwner(person);
    }

    @Test
    public void getCarsByOwnerIdTest() {
        int ownerId = 1;
        List<Car> carList = new ArrayList<>();
        carList.add(car);

        when(peopleRepository.findById(ownerId)).thenReturn(Optional.of(person));
        when(carRepository.findAllByOwner(person)).thenReturn(carList);

        List<Car> result = carService.getCarsByOwnerId(ownerId);

        assertEquals(1, result.size());
        assertEquals(car, result.get(0));
        verify(peopleRepository).findById(ownerId);
        verify(carRepository).findAllByOwner(person);
    }

    @Test
    public void assignTest() {
        Integer id = 1;

        when(peopleRepository.findByUsername(person.getUsername())).thenReturn(Optional.of(person));
        when(carRepository.findAllById(id)).thenReturn(Optional.of(car));

        Car bookedCar = new Car();
        bookedCar.setStartDate("2023-05-02");
        bookedCar.setEndDate("2023-05-04");

        carService.assign(id, bookedCar);

        verify(peopleRepository).findByUsername(person.getUsername());
        verify(carRepository).findAllById(id);
        assertEquals(person, car.getOwner());
        assertEquals(bookedCar.getStartDate(), car.getStartDate());
        assertEquals(bookedCar.getEndDate(), car.getEndDate());
    }

    @Test
    public void releaseTest() {
        int id = 1;

        when(carRepository.findAllById(id)).thenReturn(Optional.of(car));

        carService.release(id);

        verify(carRepository).findAllById(id);
        assertEquals(null, car.getOwner());
        assertEquals(null, car.getStartDate());
        assertEquals(null, car.getEndDate());
    }

    @Test
    public void findAllByIdTest() {
        int id = 1;

        when(carRepository.findAllById(id)).thenReturn(Optional.of(car));

        Car result = carService.findAllById(id);

        assertEquals(car, result);
        verify(carRepository).findAllById(id);
    }

    // Add more tests for other methods here
}
