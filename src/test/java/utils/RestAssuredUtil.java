package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static constants.TestConstants.BASE_URL;
import static utils.FileUtil.getProperty;

public class RestAssuredUtil {
  protected Map<String, String> propertiesMap = getProperty();

  private RequestSpecification setRequest() {
    RestAssured.baseURI = propertiesMap.get(BASE_URL);
    return RestAssured.given().relaxedHTTPSValidation();
  }

  public Response sendRequest(String method, String endpoint, Object body) {
    RequestSpecification requestSpecification = setRequest();
    Response response = null;
    switch (method){
      case "POST" -> response = requestSpecification.body(body).post(endpoint);
      case "PUT" -> response =requestSpecification.body(body).put(endpoint);
      case "DELETE" -> response = requestSpecification.body(body).delete(endpoint);
      case "GET" -> response =requestSpecification.body(body).get(endpoint);
    }
    return response;
  }

  public Response sendRequestWithoutBody(String method, String endpoint) {
    RequestSpecification requestSpecification = setRequest();
    Response response = null;
    switch (method){
      case "POST" -> response = requestSpecification.post(endpoint);
      case "DELETE" -> response = requestSpecification.delete(endpoint);
      case "GET" -> response =requestSpecification.get(endpoint);
    }
    return response;
  }
}
