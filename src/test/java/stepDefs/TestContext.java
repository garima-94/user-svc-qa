package stepDefs;

import io.restassured.response.Response;

public class TestContext {
  private Response response;
  private int result;

  public Response getResponse() {
    return response;
  }

  public void setResponse(Response response) {
    this.response = response;
  }

  public int getResult() {
    return result;
  }

  public void setResult(int result) {
    this.result = result;
  }
}
