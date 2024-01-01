package genericutils;

import io.restassured.response.Response;

public class RestAssuredUtils {
	
	public String getJsonPathData(Response response,String jsonPath) {
		
		return response.jsonPath().get(jsonPath);
	}

}
