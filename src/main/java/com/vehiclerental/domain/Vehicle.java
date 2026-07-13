package com.vehiclerental.domain;

import com.vehiclerental.enums.VehicleType;

public class Vehicle {

    private int id;
    private String brand;
    private String model;
    private VehicleType vehicleType;
    private boolean available;

    public Vehicle() {
    }

    public Vehicle(int id, String brand, String model,
                   VehicleType vehicleType, boolean available) {

        this.id = id;
        this.brand = brand;
        this.model = model;
        this.vehicleType = vehicleType;
        this.available = available;
    }


    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public boolean isAvailable() {
        return available;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }


}