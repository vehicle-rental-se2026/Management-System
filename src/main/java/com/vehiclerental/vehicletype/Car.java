package com.vehiclerental.vehicletype;

import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.enums.VehicleType;

public class Car extends Vehicle {

    public Car(int id, String brand, String model, boolean available) {
        super(id, brand, model, VehicleType.CAR, available);
    }
}