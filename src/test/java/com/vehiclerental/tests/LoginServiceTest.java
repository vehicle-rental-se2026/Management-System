package com.vehiclerental.tests;

import com.vehiclerental.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

    private LoginService loginService;

    @BeforeEach
    void setUp() {
        loginService = new LoginService();
    }

    @Test
    void testValidLogin() {

        boolean result = loginService.login("admin", "1234");

        assertTrue(result);
        assertTrue(loginService.isLoggedIn());

    }

    @Test
    void testInvalidLogin() {

        boolean result = loginService.login("admin", "1111");

        assertFalse(result);
        assertFalse(loginService.isLoggedIn());

    }

    @Test
    void testLogout() {

        loginService.login("admin", "1234");

        loginService.logout();

        assertFalse(loginService.isLoggedIn());

    }

}