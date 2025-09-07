package com.food_delivery.DishPatch.services;

import com.food_delivery.DishPatch.models.Driver;
import com.food_delivery.DishPatch.repositories.DriverRepository;
import org.springframework.stereotype.Service;

@Service
public class DriverService {
    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public void createDriver(Driver driver){
        driverRepository.save(driver);
    }
}
