package com.Spree;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Refresh_Token {
  @Test
  public void Refresh_Token1() {
	 

			RestAssured.baseURI = "https://demo.spreecommerce.org";
			RequestSpecification request = RestAssured.given();

			JSONObject requestParams = new JSONObject();
			requestParams.put("grant_type", "password");
			requestParams.put("username", "lti@spree.com");
			requestParams.put("password", "spree123");
			// Add a header stating the Request body is a JSON
			request.header("Content-Type", "application/json");
			request.body(requestParams.toJSONString());
			// POST the Response
			Response response = request.request(Method.POST,"/spree_oauth/token");
			//Response response = request.request(Method.POST,"/spree_oauth/token");
			response.prettyPrint();
			int statusCode = response.getStatusCode();
			// System.out.println(response.asString());
			Assert.assertEquals(statusCode, 200);

			JsonPath jsonPathEvaluator = response.getBody().jsonPath();
			String outh_token = jsonPathEvaluator.get("access_token").toString();
			System.out.println("oAuth Token is =>  " + outh_token);

		}
  
}

