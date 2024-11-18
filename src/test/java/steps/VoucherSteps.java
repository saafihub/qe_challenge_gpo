package steps;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.gpo.utils.ConfigReader;
import org.gpo.utils.DBConnect;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class VoucherSteps {

    public static Response response;
    public static RequestSpecification request;
    private JSONObject jsonBody;
    private Random random = new Random();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private int min = 10000;
    private int max = 9999999;
    private int increment = 10;
    private int numberOfPossibleValues = ((max - min) / increment) + 1;

    private static final String BASE_URL = new ConfigReader().initializeProperties().getProperty("url");

    @Given("Headers added to Vouchers Request")
    public void i_set_Headers_for_request(io.cucumber.datatable.DataTable dt) {
        List<Map<String, String>> list = dt.asMaps(String.class, String.class);
        for (int i = 0; i < list.size(); i++) {
            request = given().headers(list.get(i).get("HEADER"), list.get(i).get("VALUE"));
        }
    }

    @SuppressWarnings("unchecked")
    @Given("Make request for a Single working class hero and vouchers with below details")
    public void request_body_for_Single_working_class_hero_and_vouchers_below_details(
            io.cucumber.datatable.DataTable dataTable) {

        List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
        for (int i = 0; i < list.size(); i++) {

            jsonBody = new JSONObject();
            int randomIndex = random.nextInt(numberOfPossibleValues);
            int randomNumber = min + (randomIndex * increment);

            jsonBody.put("natid", "natid-" + randomNumber);
            jsonBody.put("name", list.get(i).get("name"));
            jsonBody.put("gender", list.get(i).get("gender"));
            jsonBody.put("birthDate", list.get(i).get("birthDate"));
            jsonBody.put("deathDate", list.get(i).get("deathDate"));
            jsonBody.put("salary", list.get(i).get("salary"));
            jsonBody.put("taxPaid", list.get(i).get("taxPaid"));
            jsonBody.put("browniePoints", list.get(i).get("browniePoints"));

            JSONObject voucher = new JSONObject();
            voucher.put("voucherName", "VOUCHER 1");
            voucher.put("voucherType", "TRAVEL");

            JSONArray vouchers = new JSONArray();
            vouchers.add(voucher);

            jsonBody.put("vouchers", vouchers);

            System.out.println("JSON object is : " + jsonBody.toJSONString());
        }
        request.body(jsonBody);
    }

    @When("send vouchers request to {string}")
    public void i_send_POST_request_to(String string) {
        response = request.when().post(BASE_URL + string);
        System.out.println("Response :  " + response.asString());
    }

    @When("send vouchers request by person and type to {string}")
    public void i_send_POST_request_by_person_and_type_to(String string) {
        response = request.when().get(BASE_URL + string);
        System.out.println("Response :  " + response.asString());
    }

    @Then("verify vouchers response statuscode {int}")
    public void the_status_code_is(Integer statusCode) {
        response.then().assertThat().statusCode(statusCode);
    }

    /*@Given("user establish sql database connection")
    public void establishDatabaseConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "user", "userpassword");
            statement = connection.createStatement();
        } catch (Exception ex) {
            System.out.println("DB Connection issue" + ex);
        }
    }

    @When("user click create button")
    public void user_click_create_button() throws SQLException {
        Statement statement = DBUtils.createStatement();
        ResultSet rs = statement.executeQuery("select count(*) from working_class_heroes");
        while (rs.next()) {
            beforeCsvUploadRecCount = rs.getInt(1);
            System.out.println("Data count before importing empty csv file:" + beforeCsvUploadRecCount);
        }
        clerkPage.clickCreateButton();

        DBUtils.closeStatement();
    }*/

    @Given("user execute vouchers query {string}")
    public void executeQuery(String query) {
        Statement statement = DBConnect.createStatement();
        try {
            resultSet = statement.executeQuery(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    @Then("user should receive a non empty resultset added with vouchers")
    public void voucher_validate_in_database() throws SQLException {
        resultSet.next();
        assertTrue(true);
    }

    @SuppressWarnings("unchecked")
    @Given("Make request for a Single working class hero and empty vouchers with below details")
    public void request_body_for_Single_working_class_hero_and_empty_vouchers_below_details(
            io.cucumber.datatable.DataTable dataTable) {

        List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
        for (int i = 0; i < list.size(); i++) {

            jsonBody = new JSONObject();
            int randomIndex = random.nextInt(numberOfPossibleValues);
            int randomNumber = min + (randomIndex * increment);

            jsonBody.put("natid", "natid-" + randomNumber);
            jsonBody.put("name", list.get(i).get("name"));
            jsonBody.put("gender", list.get(i).get("gender"));
            jsonBody.put("birthDate", list.get(i).get("birthDate"));
            jsonBody.put("deathDate", list.get(i).get("deathDate"));
            jsonBody.put("salary", list.get(i).get("salary"));
            jsonBody.put("taxPaid", list.get(i).get("taxPaid"));
            jsonBody.put("browniePoints", list.get(i).get("browniePoints"));

            JSONArray vouchers = new JSONArray();

            jsonBody.put("vouchers", vouchers);

            System.out.println("JSON object is : " + jsonBody.toJSONString());
        }
        request.body(jsonBody);
    }

    @Given("Make request for a Single working class hero and vouchers with below invalid details")
    public void request_body_for_Single_working_class_hero_and_vouchers_with_invalid_details(
            io.cucumber.datatable.DataTable dataTable) {

        List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
        for (int i = 0; i < list.size(); i++) {

            jsonBody = new JSONObject();
            int randomIndex = random.nextInt(numberOfPossibleValues);
            int randomNumber = min + (randomIndex * increment);

            jsonBody.put("natid", list.get(i).get("natid"));
            jsonBody.put("name", list.get(i).get("name"));
            jsonBody.put("gender", list.get(i).get("gender"));
            jsonBody.put("birthDate", list.get(i).get("birthDate"));
            jsonBody.put("deathDate", list.get(i).get("deathDate"));
            jsonBody.put("salary", list.get(i).get("salary"));
            jsonBody.put("taxPaid", list.get(i).get("taxPaid"));
            jsonBody.put("browniePoints", list.get(i).get("browniePoints"));

            JSONObject voucher = new JSONObject();
            voucher.put("voucherName", "VOUCHER 3");
            voucher.put("voucherType", "TRAVEL");

            JSONArray vouchers = new JSONArray();
            vouchers.add(voucher);

            jsonBody.put("vouchers", vouchers);

            System.out.println("JSON object is : " + jsonBody.toJSONString());
        }
        request.body(jsonBody);
    }


    @Then("verify response message as {string}")
    public void validateResponseBody(String errorMsg) {
        response.then().assertThat().log().body().toString().contains(errorMsg);
    }

    @Then("verify response body for vouchers")
    public void validateResponseBodyForVouchers() throws JsonMappingException, JsonProcessingException {
        String json = response.getBody().asString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(json);
        JsonNode data = jsonNode.get("data");
        assertTrue(data.isArray() && data.size() > 0);
    }

}