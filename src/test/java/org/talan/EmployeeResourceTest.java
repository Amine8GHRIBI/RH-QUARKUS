package org.talan;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
@QuarkusTest 
public class EmployeeResourceTest {
	
	



		
		
		@Test
	    public void testCustomerService() {

	        RestAssured.given()
	                .when().post("/all-employees")
	                .then()
	                .statusCode(415);
	    }
		
		@Test
	    public void testProjectService() {

	        RestAssured.given()
	                .when().get("/Projects")
	                .then()
	                .statusCode(200);
	       
	        
	    }

}
