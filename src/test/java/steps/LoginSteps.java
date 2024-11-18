package steps;

import org.junit.Assert;

import org.gpo.Driver;
import hooks.Application;
import org.gpo.pages.LoginPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

    private static String headerMsg;
    private static String errorMsg;
    private Driver driver;
    private LoginPage loginPage;
    private Application application;

    public LoginSteps(){
        this.application = new Application();
        this.driver = application.getBaseTest();
        this.loginPage = new LoginPage(driver.getPage());
    }

    @Given("user is on login page")
    public void user_is_on_login_page() {
        String url_locate = driver.getProp().getProperty("url");
        driver.getPage().navigate(url_locate+"login");
    }

    @When("user gets the header of the login page")
    public void user_gets_the_header_of_the_login_page() {
        headerMsg = loginPage.getLoginPageHeader();
        System.out.println("Login page header is:" + headerMsg);
    }

    @Then("header of the login page should be {string}")
    public void header_of_the_login_page_should_be(String expectedHeader) {
        Assert.assertTrue(headerMsg.equalsIgnoreCase(expectedHeader));
    }

    @When("user enters username {string}")
    public void user_enters_username(String uname) {
        loginPage.getUserName(uname);
    }

    @When("user enters password {string}")
    public void user_enters_password(String pwd) {
        loginPage.getPassword(pwd);
    }

    @When("user click on Submit button")
    public void user_click_on_submit_button() {
        loginPage.clickOnSubmit();
    }

    @Then("user lands on the error message in the page")
    public void user_gets_the_error_message_in_the_page() {
        errorMsg = loginPage.getErrorMessage();
        System.out.println("Login page header is:" + errorMsg);
    }

    @Then("error message should be {string}")
    public void error_message_should_be(String expectedErrorMsg) {
        Assert.assertTrue(errorMsg.equalsIgnoreCase(expectedErrorMsg));
    }
}