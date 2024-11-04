package utils;

import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertionUtil {

  public static void assertStatusCode(Response response, int statusCode) {
    assertThat(response.statusCode())
        .withFailMessage(
            String.format(
                "Request is failed due to %s : %s. Expected: %s , Actual: %s",
                response.statusCode(), response.asString(), statusCode, response.statusCode()))
        .isEqualTo(statusCode);
  }

  public static void assertSize(
      List<Map<String, Object>> actualResponse, List<Map<String, String>> expectedResponse) {
    assertThat(actualResponse)
        .withFailMessage(
            String.format(
                "Actual list of users has different size than expected list of users. Actual: %s , Expected: %s ",
                actualResponse.size(), expectedResponse.size()))
        .hasSameSizeAs(expectedResponse);
  }

  public static void assertListEquality(
      List<Map<String, Object>> actualResponse, List<Map<String, String>> expectedResponse) {
    assertThat(actualResponse)
        .withFailMessage(
            "Expected Response: %s doesn't match with actual Response: %s",
            expectedResponse, actualResponse)
        .isEqualTo(expectedResponse);
  }

  public static void assertStringEquality(String actualMessage, String expectedMessage) {
    assertThat(actualMessage)
        .withFailMessage(
            "Expected Response: %s doesn't match with actual Response: %s",
            expectedMessage, actualMessage)
        .isEqualTo(expectedMessage);
  }
}
