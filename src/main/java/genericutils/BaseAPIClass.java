package genericutils;

import java.sql.SQLException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseAPIClass {
	
	public DataBaseUtils dbu=new DataBaseUtils();
	public JavaUtils ju=new JavaUtils();
	public RestAssuredUtils rau=new RestAssuredUtils();
	public RequestSpecification reqSpec;
	public ResponseSpecification resSpec;
	
	
	@BeforeSuite
	public void bsConfig() throws SQLException {
		dbu.getDBConnection();
		reqSpec=new RequestSpecBuilder().setBaseUri(IConstants.Base_URI).setContentType(ContentType.JSON).build();
		resSpec=new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
	}
	
	@AfterSuite
	public void asConfig() throws SQLException {
		dbu.closeDBConnection();
	}

}
