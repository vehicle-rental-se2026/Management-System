package com.vehiclerental.service;

import com.vehiclerental.domain.Manager;
import com.vehiclerental.repository.ManagerRepository;

public class LoginService {

    private final ManagerRepository managerRepository;

    public LoginService() {
        managerRepository = new ManagerRepository();
    }

    public boolean login(String username, String password) {

        Manager manager = managerRepository.getManager();

        if (manager.getUsername().equals(username)
                && manager.getPassword().equals(password)) {

            manager.setLoggedIn(true);
            return true;
        }

        return false;
    }

    public void logout() {

        Manager manager = managerRepository.getManager();
        manager.setLoggedIn(false);

    }

    public boolean isLoggedIn() {
        return managerRepository.getManager().isLoggedIn();
    }
}