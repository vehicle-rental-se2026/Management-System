package com.vehiclerental.repository;

import com.vehiclerental.domain.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleRepository {

    private final List<Vehicle> vehicles;

    public VehicleRepository() {

        vehicles = new ArrayList<>();

        vehicles.add(new Vehicle(1, "Toyota", "Corolla", true));
        vehicles.add(new Vehicle(2, "Honda", "Civic", true));
        vehicles.add(new Vehicle(3, "BMW", "X5", true));
        vehicles.add(new Vehicle(4, "Mercedes", "C200", true));
        vehicles.add(new Vehicle(5, "Audi", "A4", true));
        vehicles.add(new Vehicle(6, "Ford", "Focus", true));
        vehicles.add(new Vehicle(7, "Hyundai", "Elantra", true));
        vehicles.add(new Vehicle(8, "Kia", "Sportage", true));
        vehicles.add(new Vehicle(9, "Nissan", "Altima", true));
        vehicles.add(new Vehicle(10, "Chevrolet", "Malibu", true));
        vehicles.add(new Vehicle(11, "Volkswagen", "Golf", false));
        vehicles.add(new Vehicle(12, "Lexus", "RX350", true));
        vehicles.add(new Vehicle(13, "Mazda", "CX-5", true));
        vehicles.add(new Vehicle(14, "Jeep", "Wrangler", true));
        vehicles.add(new Vehicle(15, "Tesla", "Model 3", true));

    }

    public List<Vehicle> getAllVehicles() {
        return vehicles;
    }
}