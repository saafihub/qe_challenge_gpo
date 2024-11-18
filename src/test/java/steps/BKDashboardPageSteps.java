package steps;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import hooks.Application;
import org.junit.Assert;

import org.gpo.Driver;
import org.gpo.pages.BKDashboardPage;
import org.gpo.pages.LoginPage;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.nio.file.*;
public class BKDashboardPageSteps {

    private String bookkeeperHeaderMsg;
    private BKDashboardPage bookkeeperPage;
    private Integer filestatus;
    private Driver driver;
    private LoginPage loginPage;
    private Application application;

    public BKDashboardPageSteps(){
        this.application = new Application();
        this.driver = application.getBaseTest();
        this.loginPage = new LoginPage(driver.getPage());
        this.bookkeeperPage = new BKDashboardPage(driver.getPage());
    }

    @Given("Logged in to application as bookkeeper")
    public void user_has_already_logged_in_to_application(DataTable dataTable) {
        List<Map<String,String>> credList = dataTable.asMaps();
        String userName = credList.get(0).get("username");
        String password = credList.get(0).get("password");

        String url_locate = driver.getProp().getProperty("url");
        driver.getPage().navigate(url_locate+"login");
        bookkeeperPage = loginPage.doBookkeeperLogin(userName, password);
    }

    @Given("user is on bookkeeper dashboard page")
    public void user_is_on_bookkeeper_dashboard_page() {
        System.out.println("user is on bookkeeper dashboard page");
    }

    @Then("user lands on the bookkeeper dashboard page")
    public void user_gets_the_header_of_the_bookkeeper_dashboard_page() {
        bookkeeperHeaderMsg = bookkeeperPage.getBookKeeperHeaderMsg();
        System.out.println("clerk dashboard page header is:" + bookkeeperHeaderMsg);
    }

    @Then("bookkeeper dashboard page header should be {string}")
    public void header_of_the_bookkeeper_dashboard_page_should_be(String expectedHeaderMsg) {
        Assert.assertTrue(bookkeeperHeaderMsg.equalsIgnoreCase(expectedHeaderMsg));
    }

    @Then("verify Generate Tax Relief Egress File button should be displayed")
    public void verify_generate_tax_relief_egress_file_button_should_be_displayed() {
        if(bookkeeperPage.taxReliefFileButtonVisibility()) {
            Assert.assertTrue("genarate tax releif button is enabled",bookkeeperPage.taxReliefFileButtonVisibility());
            System.out.println("genarate tax releif button is enabled");
        }

        else {
            Assert.assertFalse("genarate tax releif button is not enabled",bookkeeperPage.taxReliefFileButtonVisibility());
            System.out.println("genarate tax releif button is not enabled");
        }

    }

    @When("user click on the Generate Tax Relief Egress File button")
    public void user_click_on_the_generate_tax_relief_egress_file_button() {
        bookkeeperPage.taxReliefFileButtonState();
        bookkeeperPage.clickOnTaxReliefFileButton();
    }

    @Then("verify the {string} file is generated")
    public void verify_the_file_is_generated(String string) throws Exception {
        bookkeeperPage.generateFile("src/test/resources/downloads", string);
        Assert.assertTrue("tax reflief file is not generated",bookkeeperPage.taxReliefFileButtonVisibility());
    }

    @Then("verify the total count of records written in {string} file")
    public void verify_test_file_contents(String string) throws IOException {
        String filePath = Paths.get("src/test/resources/downloads", string).toString();
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        String totalRecordsLine = lines.get(lines.size() - 1);
        int totalRecords = Integer.parseInt(totalRecordsLine.trim());

        // Assert the total records
        Assert.assertEquals(totalRecords, lines.size() - 1);
    }

    @When("user generated the file {string}")
    public void verify_the_file_is_available_in_downloads_folder(String string) throws Exception {
        Path filePath = Paths.get("src/test/resources/downloads", string);
        if (Files.exists(filePath)) {
            System.out.println("File "+string+" exists in the 'downloads' directory.");
        } else {
            System.out.println("File "+string+" does not exist in the 'downloads' directory.");
        }
    }

    @Then("verify the total count of records written as 0 in {string} file")
    public void verify_test_empty_file_contents(String string) throws IOException {
        String filePath = Paths.get("src/test/resources/downloads", string).toString();
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        String totalRecordsLine = lines.get(lines.size() - 1);
        int totalRecords = Integer.parseInt(totalRecordsLine.trim());

        // Assert the total records
        Assert.assertEquals(totalRecords, lines.size() - 1);
    }
}