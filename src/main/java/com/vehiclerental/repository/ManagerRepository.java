package com.vehiclerental.repository;

import com.vehiclerental.domain.Manager;

public class ManagerRepository {

    private final Manager manager;

    public ManagerRepository() {
        manager = new Manager("admin", "1234");
    }

    public Manager getManager() {
        return manager;
    }
}