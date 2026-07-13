package com.vehiclerental.repository;

import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.vehicletype.*;

import java.util.ArrayList;
import java.util.List;

public class VehicleRepository {

    private final List<Vehicle> vehicles;

    public VehicleRepository() {

        vehicles = new ArrayList<>();

        vehicles.add(new Car(1, "Toyota", "Corolla", true));
        vehicles.add(new Car(2, "Honda", "Civic", true));

        vehicles.add(new Motorcycle(3, "Yamaha", "R1", true));
        vehicles.add(new Motorcycle(4, "Honda", "CBR600", true));

        vehicles.add(new Van(5, "Mercedes", "Sprinter", true));
        vehicles.add(new Van(6, "Ford", "Transit", true));

        vehicles.add(new Truck(7, "Volvo", "FH16", true));
        vehicles.add(new Truck(8, "MAN", "TGX", true));

        vehicles.add(new ElectricVehicle(9, "Tesla", "Model 3", true));
        vehicles.add(new ElectricVehicle(10, "Tesla", "Model Y", true));

        vehicles.add(new Car(11, "BMW", "320i", true));
        vehicles.add(new Car(12, "Audi", "A4", true));

        vehicles.add(new ElectricVehicle(13, "Hyundai", "Ioniq 5", true));

        vehicles.add(new Truck(14, "Scania", "R500", true));

        vehicles.add(new Van(15, "Toyota", "Hiace", true));
    }

    public List<Vehicle> getAllVehicles() {
        return vehicles;
    }
}