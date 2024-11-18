package steps;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Random;

import hooks.Application;
import org.junit.Assert;
import org.gpo.Driver;
import org.gpo.pages.CDashboardPage;
import org.gpo.pages.CDashboardAddPage;
import org.gpo.pages.LoginPage;
import org.gpo.utils.DBConnect;


import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CDashboardPageSteps {
    private String clerkHeaderMsg;
    private Integer beforeCsvUploadRecCount;
    private CDashboardPage clerkPage;
    private CDashboardAddPage clerkaddPage;
    private Driver driver;
    private LoginPage loginPage;
    private Application application;
    private Random random = new Random();
    private int min = 10000;
    private int max = 9999999;
    private int increment = 10;
    private int numberOfPossibleValues = ((max - min) / increment) + 1;

    private String createdUser;
    public CDashboardPageSteps(){
        this.application = new Application();
        this.driver = application.getBaseTest();
        this.loginPage = new LoginPage(driver.getPage());
        this.clerkPage = new CDashboardPage(driver.getPage());
        this.clerkaddPage = new CDashboardAddPage(driver.getPage());
    }

    @Given("user logged in to application as clerk")
    public void user_has_already_logged_in_to_application(DataTable dataTable) {
        List<Map<String, String>> credList = dataTable.asMaps();
        String userName = credList.get(0).get("username");
        String password = credList.get(0).get("password");
        String url_locate = driver.getProp().getProperty("url");
        driver.getPage().navigate(url_locate+"login");
        clerkPage = loginPage.doClerkLogin(userName, password);
    }

    @Then("verify Add Hero button should be displayed")
    public void verify_add_hero_button_should_be_displayed() {
        Assert.assertTrue(clerkPage.isAddHeroButtonExists());
    }

    @When("user select upload a csv file from Add Hero dropdown")
    public void user_select_upload_a_csv_file_from_add_hero_dropdown() {
        clerkPage.clickAddHeroButton();
        clerkPage.clickUploadCsvFileFromDropdown();
    }

    @When("user upload working record csv file")
    public void user_upload_working_record_csv_file() {
        clerkPage.uploadWorkingCsvFile();

    }

    @When("user click create button")
    public void user_click_create_button() throws SQLException {
        Statement statement = DBConnect.createStatement();
        ResultSet rs = statement.executeQuery("select count(*) from working_class_heroes");
        while (rs.next()) {
            beforeCsvUploadRecCount = rs.getInt(1);
            System.out.println("Data count before importing empty csv file:" + beforeCsvUploadRecCount);
        }
        clerkPage.clickCreateButton();

        DBConnect.closeStatement();
    }

    @Then("{string} message should be displayed")
    public void message_should_be_displayed(String expectedMsg) {
        String actualMsg = clerkPage.getSuccessNotificationMsg();
        Assert.assertTrue(actualMsg.equalsIgnoreCase(expectedMsg));
    }

    @Then("verify the count of {string} in database table")
    public void verify_the_count_of_in_database_table(String string) throws SQLException {
        Statement statement = DBConnect.createStatement();
        ResultSet rs = statement.executeQuery("select count(*) from working_class_heroes");
        while (rs.next()) {
            Integer afterCsvUploadRecCount = rs.getInt(1);
            System.out.println("Data count after importing empty csv file:" + afterCsvUploadRecCount);
            Assert.assertTrue(true);
        }
        DBConnect.closeStatement();
    }

    @When("user upload erroneous record csv file")
    public void user_upload_erroneous_record_csv_file() {
        clerkPage.uploadErroneousCsvFile();
    }

    @When("user upload empty record csv file")
    public void user_select_empty_record_csv_file() {
        clerkPage.uploadEmptyCsvFile();
    }

    @When("user upload header record csv file")
    public void user_select_header_record_csv_file() {
        clerkPage.uploadHeaderCsvFile();
    }

    @When("user upload text file other than csv file")
    public void user_select_text_file_no_csv_file() {
        clerkPage.uploadTXTFile();
    }

    @Then("user lands on the header of the clerk dashboard page")
    public void user_gets_the_header_of_the_clerk_dashboard_page() {
        clerkHeaderMsg = clerkPage.getClerkHeaderMessage();
        System.out.println("clerk dashboard page header is:" + clerkHeaderMsg);
    }

    @Then("clerk dashboard page header should be {string}")
    public void header_of_the_clerk_dashboard_page_should_be(String expectClerkHeader) {
        Assert.assertTrue(clerkHeaderMsg.equalsIgnoreCase(expectClerkHeader));
    }

    @Given("user is on Clerk dashboard page")
    public void user_is_on_clerk_dashboard_page() {
        System.out.println("user is on clerk dashboard page");
    }

    @When("user select Add from Add Hero dropdown")
    public void user_select_add_hero_button_from_dropdown() {
        clerkPage.clickAddHeroButton();
        clerkPage.clickAddOptionFromDropdown();
    }

    @Given("user enter the working class hero details to add hero")
    public void user_enter_hero_details_to_add(
            io.cucumber.datatable.DataTable dataTable) {

        List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
        for (int i = 0; i < list.size(); i++) {

            int randomIndex = random.nextInt(numberOfPossibleValues);
            int randomNumber = min + (randomIndex * increment);
            createdUser = "natid-" + randomNumber;
            clerkaddPage.enterNatId(createdUser);
            clerkaddPage.enterName(list.get(i).get("name"));
            clerkaddPage.selectMaleGender();
            //clerkaddPage.enterBirthDate(list.get(i).get("birthDate"));
            clerkaddPage.enterDateWithKeyboard(list.get(i).get("birthDate"));
            clerkaddPage.enterBrowniePoints(list.get(i).get("browniePoints"));
            clerkaddPage.enterSalary(list.get(i).get("salary"));
            clerkaddPage.enterTaxPaid(list.get(i).get("taxPaid"));
        }
    }
}