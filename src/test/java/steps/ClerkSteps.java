package steps;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.gpo.utils.ConfigReader;
import org.json.simple.JSONObject;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ClerkSteps {

    public static Response response;
    public static RequestSpecification request;
    private JSONObject jsonBody;

    private Random random = new Random();

    private int min = 10000;
    private int max = 9999999;
    private int increment = 10;
    private int numberOfPossibleValues = ((max - min) / increment) + 1;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private String createdUser;

    private static final String BASE_URL = new ConfigReader().initializeProperties().getProperty("url");

    @Given("headers added to payload")
    public void i_set_Headers_for_request(io.cucumber.datatable.DataTable dt) {
        List<Map<String, String>> list = dt.asMaps(String.class, String.class);
        for (int i = 0; i < list.size(); i++) {
            // request= given().param(list.get(i).get("KEY"), list.get(i).get("VALUE"));
            request = given().headers(list.get(i).get("HEADER"), list.get(i).get("VALUE"));
        }
    }

    @SuppressWarnings("unchecked")
    @Given("Make request for Single working class hero with below details")
    public void request_body_for_Single_working_class_hero_with_below_details(
            io.cucumber.datatable.DataTable dataTable) {

        List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
        for (int i = 0; i < list.size(); i++) {

            jsonBody = new JSONObject();
            int randomIndex = random.nextInt(numberOfPossibleValues);
            int randomNumber = min + (randomIndex * increment);

            createdUser = "natid-" + randomNumber;
            jsonBody.put("natid", createdUser);
            jsonBody.put("name", list.get(i).get("name"));
            jsonBody.put("gender", list.get(i).get("gender"));
            jsonBody.put("birthDate", list.get(i).get("birthDate"));
            jsonBody.put("deathDate", list.get(i).get("deathDate"));
            jsonBody.put("salary", list.get(i).get("salary"));
            jsonBody.put("taxPaid", list.get(i).get("taxPaid"));
            jsonBody.put("browniePoints", list.get(i).get("browniePoints"));

            System.out.println("JSON object is : " + jsonBody.toJSONString());
        }
        request.body(jsonBody);
    }

    @SuppressWarnings("unchecked")
    @Given("Make request for Single working class hero with invalid details")
    public void request_body_for_Single_working_class_hero_with_invalid_details(
            io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
        for (int i = 0; i < list.size(); i++) {
            jsonBody = new JSONObject();
            jsonBody.putAll(list.get(i));
            System.out.println("JSON object is : " + jsonBody.toJSONString());
        }
        request.body(jsonBody);
    }

    @SuppressWarnings("unchecked")
    @Given("Make request for Single working class hero with existing natid details")
    public void requestBodyForSingleWorkingClassHeroWithExistingNatid(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
        for (int i = 0; i < list.size(); i++) {
            jsonBody = new JSONObject();
            jsonBody.put("natid", list.get(i).get("natid"));
            jsonBody.put("name", list.get(i).get("name"));
            jsonBody.put("gender", list.get(i).get("gender"));
            jsonBody.put("birthDate", list.get(i).get("birthDate"));
            jsonBody.put("deathDate", list.get(i).get("deathDate"));
            jsonBody.put("salary", list.get(i).get("salary"));
            jsonBody.put("taxPaid", list.get(i).get("taxPaid"));
            jsonBody.put("browniePoints", list.get(i).get("browniePoints"));

            System.out.println("JSON object is : " + jsonBody.toJSONString());
        }
        request.body(jsonBody);
    }

    @Given("user establish a database connection")
    public void establishDatabaseConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "user", "userpassword");
            statement = connection.createStatement();
        } catch (Exception ex) {
            System.out.println("DB Connection issue" + ex);
        }
    }

    @When("user execute query {string}")
    public void executeQuery(String query) {
        try {
            resultSet = statement.executeQuery(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Then("user should receive a non empty resultset")
    public void verifySingleUser() throws SQLException {
        resultSet.next();
        assertTrue(true);
    }

    @After
    public void closeDatabaseConnection() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @When("Request Post action Sent To {string}")
    public void post_request_sent_to(String string) {
        response = request.when().post(BASE_URL + string);

        System.out.println("Response :  " + response.asString());
    }

    @When("Request GET action Sent To {string}")
    public void i_send_GET_request_to(String string) {
        response = request.when().get(BASE_URL + string);

        System.out.println("Response :  " + response.asString());
    }

    @Then("verify status code is {int}")
    public void the_status_code_is(Integer statusCode) {
        response.then().assertThat().statusCode(statusCode);
    }

    @Then("verify status code is should be either of {int} and {int}")
    public void theStatusCodeShouldBeEither(int status1, int status2) {
        assertTrue(response.getStatusCode() == status1 || response.getStatusCode() == status2);
    }

    @Then("verify status code for invalid natid is {int}")
    public void the_status_code_for_invalid_natid_is(Integer statusCode) {
        response.then().assertThat().statusCode(statusCode);
    }

    @Then("verify status code for invalid field is {int}")
    public void the_status_code_for_invalid_field_is(Integer statusCode) {
        response.then().assertThat().statusCode(statusCode);
    }

    @Then("Response message contains {string}")
    public void response_body_has(String responseMessage) {
        response.then().assertThat().log().body().toString().contains(responseMessage);
    }

}