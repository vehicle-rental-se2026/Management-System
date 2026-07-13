package com.vehiclerental.vehicletype;

import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.enums.VehicleType;

public class Van extends Vehicle {

    public Van(int id, String brand, String model, boolean available) {
        super(id, brand, model, VehicleType.VAN, available);
    }
}