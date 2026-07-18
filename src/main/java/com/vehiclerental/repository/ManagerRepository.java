package com.vehiclerental.repository;

import com.vehiclerental.domain.Manager;
/**
 * The ManagerRepository class manages manager data
 * and provides access to manager information.
 */
public class ManagerRepository {

    private final Manager manager;

    public ManagerRepository() {
        manager = new Manager("admin", "1234");
    }

    public Manager getManager() {
        return manager;
    }
}