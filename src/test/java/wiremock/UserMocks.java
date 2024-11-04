package wiremock;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.apache.http.HttpStatus.*;

public class UserMocks {

  public static void createUserMock() {
    stubFor(
        post(urlEqualTo("/users"))
            .willReturn(
                aResponse()
                    .withStatus(SC_OK)
                    .withBody(
                        "[{\"name\":\"steve\","
                            + "\"email\":\"steve@zilch.com\","
                            + "\"id\":\"1\"},{"
                            + "\"name\":\"ken\","
                            + "\"email\":\"ken@zilch.com\","
                            + "\"id\":\"2\"}]")));
  }

  public static void getUsersMock() {
    stubFor(
        get(urlEqualTo("/user"))
            .willReturn(
                aResponse()
                    .withStatus(SC_OK)
                    .withBody(
                        "[{\"name\":\"steve\","
                            + "\"email\":\"steve@zilch.com\","
                            + "\"id\":\"1\"},{"
                            + "\"name\":\"ken\","
                            + "\"email\":\"ken@zilch.com\","
                            + "\"id\":\"2\"}]")));
  }

  public static void getAddressMock() {
    stubFor(get(urlEqualTo("/address")).willReturn(aResponse().withStatus(SC_NOT_FOUND)));
  }

  public static void updateUserMock() {
    var jsonObject1 = new JsonObject();
    jsonObject1.add("name", "clark");
    jsonObject1.add("email", "ken@zilch.com");
    stubFor(
        put(urlEqualTo("/user/update"))
            .withRequestBody(containing(jsonObject1.toString()))
            .willReturn(aResponse().withStatus(SC_OK).withBody("{ \"message\":\"ken updated\"}")));
    var jsonObject2 = new JsonObject();
    jsonObject2.add("name", "steve");
    jsonObject2.add("email", "steve@zilch.com");
    stubFor(
        put(urlEqualTo("/user/update"))
            .withRequestBody(containing(jsonObject2.toString()))
            .willReturn(
                aResponse().withStatus(SC_OK).withBody("{ \"message\":\"user already exists\"}")));
  }

  public static void deleteUserMock() {
    stubFor(delete(urlEqualTo("/user/1")).willReturn(aResponse().withStatus(SC_NO_CONTENT)));
  }

  public static void getDeletedUser() {
    stubFor(
        get(urlEqualTo("/user"))
            .willReturn(
                aResponse()
                    .withStatus(SC_OK)
                    .withBody(
                        "[{\"name\":\"clark\","
                            + "\"email\":\"ken@zilch.com\","
                            + "\"id\":\"2\"}]")));
  }
}
