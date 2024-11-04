package stepDefs;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import net.minidev.json.JSONObject;
import utils.AssertionUtil;
import utils.RestAssuredUtil;
import wiremock.UserMocks;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static constants.TestConstants.*;
import static org.apache.http.HttpStatus.*;

public class UserSteps extends RestAssuredUtil {
  private final TestContext testContext;
  WireMockServer wireMockServer = new WireMockServer();

  public UserSteps(TestContext testContext) {
    this.testContext = testContext;
  }

  @Given("Application is running")
  public void anAmexApplication() {
    wireMockServer.start();
    configureFor("localhost", 8080);
  }

  @Given("Users have been created in the system")
  public void iCreateUsers() {
    UserMocks.createUserMock();
    var response = sendRequestWithoutBody(METHOD_POST, "users");
    AssertionUtil.assertStatusCode(response, SC_OK);
    testContext.setResponse(response);
  }

  @When("I fetch all the users")
  public void iFetchAllTheUsers() {
    UserMocks.getUsersMock();
    var response = sendRequestWithoutBody(METHOD_GET, "user");
    testContext.setResponse(response);
  }

  @Then("a list of users should be returned with name and email")
  public void aListOfUsersShouldBeReturned(DataTable dataTable) {
    var response = testContext.getResponse();
    AssertionUtil.assertStatusCode(response, SC_OK);
    var responseJson = new JsonPath(response.asString());
    var actualResponse = responseJson.getList("$");
    List<Map<String, Object>> updateActualRes = getUpdatedMaps(actualResponse);
    var expectedResponse = dataTable.asMaps();
    AssertionUtil.assertSize(updateActualRes, expectedResponse);
    AssertionUtil.assertListEquality(updateActualRes, expectedResponse);
  }

  @Given("An user exist in the system without address")
  public void anUserExistInTheSystemWithoutAddress() {
    UserMocks.createUserMock();
  }

  @When("I fetch the address of the user")
  public void iFetchTheAddressOfTheUser() {
    UserMocks.getAddressMock();
    var response = sendRequestWithoutBody(METHOD_GET, "address");
    testContext.setResponse(response);
  }

  @Then("An address should not be returned")
  public void anAddressShouldNotBeReturned() {
    var response = testContext.getResponse();
    AssertionUtil.assertStatusCode(response, SC_NOT_FOUND);
  }

  @When("a request for {string} and {string} is made")
  public void aRequestForAndIsMade(String name, String email) {
    UserMocks.updateUserMock();
    var body = new JSONObject();
    body.put("name", name);
    body.put("email", email);
    var response = sendRequest(METHOD_PUT, "user/update", body);
    testContext.setResponse(response);
  }

  @Then("the message {string} should be returned")
  public void theMessageShouldBeReturned(String message) {
    var response = testContext.getResponse();
    AssertionUtil.assertStatusCode(response, SC_OK);
    var responseJson = new JsonPath(response.asString());
    AssertionUtil.assertStringEquality(responseJson.getString("message"), message);
  }

  @Given("An user exists with id as {int}")
  public void aUserExistsWithId(int id) {
    testContext.setResult(id);
  }

  @When("I delete the user")
  public void iDeleteTheUsers() {
    var id = testContext.getResult();
    var userResponse = testContext.getResponse();
    UserMocks.deleteUserMock();
    var response = sendRequestWithoutBody(METHOD_DELETE, String.format("user/%s", id));
    AssertionUtil.assertStatusCode(response, SC_NO_CONTENT);
  }

  @Then("I should see one user in the system")
  public void iShouldSeeNoUsersInTheSystem(DataTable dataTable) {
    UserMocks.getDeletedUser();
    var response = sendRequestWithoutBody(METHOD_GET, "user");
    AssertionUtil.assertStatusCode(response, SC_OK);
    var responseJson = new JsonPath(response.asString());
    var actualResponse = responseJson.getList("$");
    var expectedResponse = dataTable.asMaps();
    var updatedActualRes = getUpdatedMaps(actualResponse);
    AssertionUtil.assertSize(updatedActualRes, expectedResponse);
    AssertionUtil.assertListEquality(updatedActualRes, expectedResponse);
  }

  private List<Map<String, Object>> getUpdatedMaps(List<Object> actualResponse) {
    return actualResponse.stream()
        .map(obj -> (Map<String, Object>) obj)
        .map(
            map ->
                map.entrySet().stream()
                    .filter(entry -> !entry.getKey().equals("id"))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
        .collect(Collectors.toList());
  }

  @After
  public void destroyMock() {
    wireMockServer.stop();
  }
}
