package com.vehiclerental.stepdefinitions;

import com.vehiclerental.service.LoginService;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

public class LoginSteps {

    private final LoginService loginService = new LoginService();

    private String username;
    private String password;
    private boolean loginResult;

    @Given("the manager username is {string}")
    public void the_manager_username_is(String username) {
        this.username = username;
    }

    @And("the manager password is {string}")
    public void the_manager_password_is(String password) {
        this.password = password;
    }

    @When("the manager logs in")
    public void the_manager_logs_in() {
        loginResult = loginService.login(username, password);
    }

    @Then("the login should be successful")
    public void the_login_should_be_successful() {
        assertTrue(loginResult);
    }

    @Then("the login should fail")
    public void the_login_should_fail() {
        assertFalse(loginResult);
    }
}