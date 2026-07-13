package com.vehiclerental.vehicletype;

import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.enums.VehicleType;

public class Motorcycle extends Vehicle {

    public Motorcycle(int id, String brand, String model, boolean available) {
        super(id, brand, model, VehicleType.MOTORCYCLE, available);
    }

    public boolean validateAge(int age) {
        return age >= 18;
    }
}