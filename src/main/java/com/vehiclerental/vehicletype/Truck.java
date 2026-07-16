package com.vehiclerental.vehicletype;

import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.enums.VehicleType;

public class Truck extends Vehicle {

    public Truck(int id, String brand, String model, boolean available) {
        super(id, brand, model, VehicleType.TRUCK, available);
    }

    public boolean hasSpecialLicense(boolean hasLicense) {
        return hasLicense;
    }
}