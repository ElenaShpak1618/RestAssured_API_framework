package com.Spree;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Post_Create_Delete_Adress {
//Global Variable , so that we can use in another method.
		String outh_token;
		String id;
		
		@BeforeTest
		public void oAuth_Token() {

			RestAssured.baseURI = "https://demo.spreecommerce.org";
			RequestSpecification request = RestAssured.given();

			JSONObject requestParams = new JSONObject();
			requestParams.put("grant_type", "password");
			requestParams.put("username", "melena@spree.com");
			requestParams.put("password", "Elena123");
			// Add a header stating the Request body is a JSON
			request.header("Content-Type", "application/json");
			request.body(requestParams.toJSONString());
			// POST the Response
			Response response = request.post("/spree_oauth/token");
			//Response response = request.request(Method.POST,"/spree_oauth/token");
			response.prettyPrint();
			int statusCode = response.getStatusCode();
			// System.out.println(response.asString());
			Assert.assertEquals(statusCode, 200);

			JsonPath jsonPathEvaluator = response.getBody().jsonPath();
			outh_token = jsonPathEvaluator.get("access_token").toString();
			System.out.println("oAuth Token is =>  " + outh_token);

		}
		@Test
		public void POST_Create_Address() throws IOException, ParseException, org.json.simple.parser.ParseException {

			RestAssured.baseURI = "https://demo.spreecommerce.org";
			RequestSpecification request = RestAssured.given();

			//Create json object of JSONParser class to parse the JSON data
			  JSONParser jsonparser=new JSONParser();
			  //Create object for FileReader class, which help to load and read JSON file
			  FileReader reader = new FileReader(".\\JSON\\create_address.json");
			  //Returning/assigning to Java Object
			  Object obj = jsonparser.parse(reader);
			  //Convert Java Object to JSON Object, JSONObject is typecast here
			  JSONObject prodjsonobj = (JSONObject)obj;

			// Add a header stating the Request body is a JSON
			request.header("Content-Type", "application/json");
			request.header("Authorization", "Bearer "+outh_token);
			request.body(prodjsonobj);
			// POST the Response
			Response response = request.post("/api/v2/storefront/account/addresses");
			response.prettyPrint();
			int statusCode = response.getStatusCode();
			// System.out.println(response.asString());
			Assert.assertEquals(statusCode, 200);

			JsonPath jsonPathEvaluator = response.getBody().jsonPath();
			String fname = jsonPathEvaluator.get("data.attributes.firstname").toString();
			System.out.println("First Name  =>  " + fname);
			Assert.assertEquals(fname, "Rest");
			id = jsonPathEvaluator.getString("data.id").toString();
			System.out.println(id);
			

		}
		
		@Test(priority=2)
		public void Patch_Update_Address() throws IOException, ParseException, org.json.simple.parser.ParseException {

			RestAssured.baseURI = "https://demo.spreecommerce.org";
			RequestSpecification request = RestAssured.given();

			  JSONParser jsonparser=new JSONParser();
			  //Create object for FileReader class, which help to load and read JSON file
			  FileReader reader = new FileReader(".\\JSON\\update_address.json");
			  //Returning/assigning to Java Object
			  Object obj = jsonparser.parse(reader);
			  //Convert Java Object to JSON Object, JSONObject is typecast here
			  JSONObject prodjsonobj = (JSONObject)obj;

			// Add a header stating the Request body is a JSON
			request.header("Content-Type", "application/json");
			request.header("Authorization", "Bearer "+outh_token);
			request.body(prodjsonobj);
			// POST the Response
			Response response = request.patch("/api/v2/storefront/account/addresses/"+id);
			response.prettyPrint();
			int statusCode = response.getStatusCode();
			// System.out.println(response.asString());
			Assert.assertEquals(statusCode, 200);

			JsonPath jsonPathEvaluator = response.getBody().jsonPath();
			String fname = jsonPathEvaluator.get("data.attributes.firstname").toString();
			System.out.println("First Name  =>  " + fname);
			Assert.assertEquals(fname, "Rest");
  
  }
		@Test(priority=3)
		public void DELETE_Updated_Address() throws IOException, ParseException, org.json.simple.parser.ParseException {

			RestAssured.baseURI = "https://demo.spreecommerce.org";
			RequestSpecification request = RestAssured.given();

			//Create json object of JSONParser class to parse the JSON data
			  JSONParser jsonparser=new JSONParser();
			  //Create object for FileReader class, which help to load and read JSON file
			  FileReader reader = new FileReader(".\\JSON\\update_address.json");
			  //Returning/assigning to Java Object
			  Object obj = jsonparser.parse(reader);
			  //Convert Java Object to JSON Object, JSONObject is typecast here
			  JSONObject prodjsonobj = (JSONObject)obj;

		
			request.header("Content-Type", "application/json");
			request.header("Authorization", "Bearer "+outh_token);
			request.body(prodjsonobj);
		
			Response response = request.delete("/api/v2/storefront/account/addresses/"+id);
			response.prettyPrint();
			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode, 204);


			
		
   }
}


