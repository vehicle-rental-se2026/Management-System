package com.vehiclerental.vehicletype;

import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.enums.VehicleType;
/**
 * The Van class represents a van available
 * for rental in the system.
 */
public class Van extends Vehicle {

    public Van(int id, String brand, String model, boolean available) {
        super(id, brand, model, VehicleType.VAN, available);
    }
}