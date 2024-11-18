package steps;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.gpo.utils.ConfigReader;

public class ExternalServiceSteps {

    public static Response response;
    public static RequestSpecification request;
    private static final String BASE_URL = new ConfigReader().initializeProperties().getProperty("url");

    @Given("Headers added to ExternalService Request")
    public void i_set_Headers_for_request(io.cucumber.datatable.DataTable dt) {
        List<Map<String, String>> list = dt.asMaps(String.class, String.class);
        for (int i = 0; i < list.size(); i++) {
            request = given().headers(list.get(i).get("HEADER"), list.get(i).get("VALUE"));
        }
    }

    @When("send GET request to ExternalService {string}")
    public void sendGetReqToExternalSvc(String string) {
        response = request.when().get(BASE_URL + string);
        System.out.println("Response :  " + response.asString());
    }

    @Then("ExternalService response status code is {int}")
    public void externalSvcRespStatusCode(int statusCode) {
        response.then().assertThat().statusCode(statusCode);
    }

    @Then("Response body contains {string} and {string}")
    public void response_body_has(String natid, String status) {
        String respStr = response.getBody().asString();
        assertTrue(respStr.contains(natid) && respStr.contains(status));
    }

    @Then("Response body contains {string}")
    public void response_body_contains(String message) {
        response.then().assertThat().log().body().toString().contains(message);
    }

    @Then("verify response body format {string} and {string}")
    public void validate_response_body(String natid, String status)
            throws JsonMappingException, JsonProcessingException {
        String json = response.getBody().asString();

        JsonObject jsonObj = JsonParser.parseString(json).getAsJsonObject();
        JsonObject messageObj = jsonObj.getAsJsonObject("message");
        String data = messageObj.get("data").getAsString();
        String owsSts = messageObj.get("status").getAsString();
        assertTrue(natid.equalsIgnoreCase(data) && status.equalsIgnoreCase(owsSts));

        assertTrue("Message object should contain 'data' and 'status' fields", jsonObj.has("message"));
        assertTrue("Data field should contain 'natid' in the format 'natid-<number>'", data.matches("natid-\\d+"));
        assertTrue("Status field should be 'OWE'", "OWE".equalsIgnoreCase(status));
        assertTrue("Response should contain 'timestamp' field", jsonObj.has("timestamp"));
        String timestamp = jsonObj.get("timestamp").getAsString();
        assertTrue("Timestamp should be in the format", timestamp.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d+)?"));
    }

    @Then("verify response body schema structure {string} and {string}")
    public void validate_response_schema_structure(String natid, String status)
            throws JsonMappingException, JsonProcessingException {
        String json = response.getBody().asString();
        JsonObject jsonObj = JsonParser.parseString(json).getAsJsonObject();
        JsonObject messageObj = jsonObj.getAsJsonObject("message");
        String data = messageObj.get("data").getAsString();
        String owsSts = messageObj.get("status").getAsString();
        assertTrue(natid.equalsIgnoreCase(data) && status.equalsIgnoreCase(owsSts));
        response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/externalsvc-api-schema.json"));
    }

}