package com.vehiclerental.vehicletype;

import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.enums.VehicleType;

public class ElectricVehicle extends Vehicle {

    public ElectricVehicle(int id, String brand, String model, boolean available) {
        super(id, brand, model, VehicleType.ELECTRIC, available);
    }

    public boolean batteryCheck(int batteryPercentage) {
        return batteryPercentage >= 20;
    }
}