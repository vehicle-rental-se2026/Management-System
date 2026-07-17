package com.vehiclerental.domain;
/**
 * The Manager class represents the system manager.
 * It stores the manager's login credentials and
 * authentication status.
 */
public class Manager {

    private String username;
    private String password;
    private boolean loggedIn;

    public Manager()
    {
    }

    public Manager(String username, String password) {
        this.username = username;
        this.password = password;
        this.loggedIn = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

}