package com.Spree;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Create_Account_Using_API_Login_withThat_Account_Using_UI {
	
	WebDriver driver;

		
		String id;
		String email;
		String outh_token;
	
		
		
		@Test(priority = 1)
		public void POST_Create_Account() throws IOException, ParseException, org.json.simple.parser.ParseException {

			RestAssured.baseURI = "https://demo.spreecommerce.org";
			RequestSpecification request = RestAssured.given();

			//Create json object of JSONParser class to parse the JSON data
			  JSONParser jsonparser=new JSONParser();
			  //Create object for FileReader class, which help to load and read JSON file
			  FileReader reader = new FileReader(".\\JSON\\create_account.json");
			  //Returning/assigning to Java Object
			  Object obj = jsonparser.parse(reader);
			  //Convert Java Object to JSON Object, JSONObject is typecast here
			  JSONObject prodjsonobj = (JSONObject)obj;

			// Add a header stating the Request body is a JSON
			request.header("Content-Type", "application/json");
			request.header("Authorization", "Bearer "+outh_token);
			request.body(prodjsonobj);
			// POST the Response
			Response response = request.post("/api/v2/storefront/account");
			response.prettyPrint();
			int statusCode = response.getStatusCode();
			// System.out.println(response.asString());
			Assert.assertEquals(statusCode, 200);

			JsonPath jsonPathEvaluator = response.getBody().jsonPath();
			email = jsonPathEvaluator.get("data.attributes.email").toString();
			System.out.println(" Name  =>  " + email);
//			Assert.assertEquals(email, "");
			id = jsonPathEvaluator.getString("data.id").toString();
			System.out.println(id);
		}
			
			@Test(priority = 2)
			public void login_Account() throws InterruptedException {
				WebDriverManager.chromedriver().setup();
				//ChromeOptions option = new ChromeOptions();
				//option.setHeadless(false);
				//option.addArguments("incognito");
				driver = new ChromeDriver();
				driver.get("https://demo.spreecommerce.org/");
				driver.manage().window().maximize();
				driver.findElement(By.xpath("//*[@id='account-button']")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//*[text()='LOGIN']")).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//*[@id='spree_user_email']")).sendKeys(email);
				Thread.sleep(2000);
				driver.findElement(By.xpath("//*[@name='spree_user[password]']")).sendKeys("Nick123");
				Thread.sleep(2000);
				driver.findElement(By.xpath("//*[@name='commit']")).click();
				Thread.sleep(2000);
//				driver.findElement(By.xpath("//dd[text()='nick1882@spree.com']")).isDisplayed();
//				driver.close();
				
			}
			
			 @Test(priority = 3)
			  public void Get_Retrive_Account() {
				  
				  
					  RestAssured.baseURI = "https://demo.spreecommerce.org";
					  RequestSpecification httpRequest = RestAssured.given();
					  //Response response = httpRequest.get();
					  Response response = httpRequest.request(Method.GET,"/api/v2/storefront/account");
					  
						// Now let us print the body of the message to see what response
					  // we have recieved from the server
					  String responseBody = response.getBody().asString();
					  System.out.println("Response Body is =>  " + responseBody);
					  // Status Code Validation
					  int statusCode = response.getStatusCode();
					  System.out.println("Status code is =>  " + statusCode);
					  Assert.assertEquals(200, statusCode);
				 
					  // First get the JsonPath object instance from the Response interface
					 // Assert.assertEquals(responseBody.contains("UNITED STATES") /*Expected value*/, true /*Actual Value*/);
						// Verify using JSON Path Value
//
//						JsonPath js = new JsonPath(response.asString());
//						String iso_name = js.get("data.attributes.iso_name");
//						System.out.println(iso_name);
//						Assert.assertEquals(iso_name, "");
					   

		}
			   
			
			@Test(priority = 4)
			public void Patch_Update_Account() throws IOException, ParseException, org.json.simple.parser.ParseException {

				RestAssured.baseURI = "https://demo.spreecommerce.org";
				RequestSpecification request = RestAssured.given();
				
				  JSONParser jsonparser=new JSONParser();
				  FileReader reader = new FileReader(".\\JSON\\update_account.json");
				  //Returning/assigning to Java Object
				  Object obj = jsonparser.parse(reader);
				  //Convert Java Object to JSON Object, JSONObject is typecast here
				  JSONObject prodjsonobj = (JSONObject)obj;

				// Add a header stating the Request body is a JSON
				request.header("Content-Type", "application/json");
				request.header("Authorization", "Bearer "+outh_token);
				request.body(prodjsonobj);
				// POST the Response
				Response response = request.patch("/api/v2/storefront/account");
				response.prettyPrint();
				int statusCode = response.getStatusCode();
				// System.out.println(response.asString());
				Assert.assertEquals(statusCode, 200);

				JsonPath jsonPathEvaluator = response.getBody().jsonPath();
				String fname = jsonPathEvaluator.get("data.attributes.firstname").toString();
				System.out.println("First Name  =>  " + fname);
				//Assert.assertEquals(fname, "");
				 driver.close();
	           
	  }
			
			
}
