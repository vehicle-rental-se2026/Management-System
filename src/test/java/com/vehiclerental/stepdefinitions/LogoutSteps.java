package com.vehiclerental.stepdefinitions;

import com.vehiclerental.service.LoginService;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

public class LogoutSteps {

    private final LoginService loginService = new LoginService();

    @Given("the manager is logged in")
    public void theManagerIsLoggedIn() {
        loginService.login("admin", "1234");
    }

    @When("the manager logs out")
    public void theManagerLogsOut() {
        loginService.logout();
    }

    @Then("the manager should be logged out")
    public void theManagerShouldBeLoggedOut() {
        assertFalse(loginService.isLoggedIn());
    }
}