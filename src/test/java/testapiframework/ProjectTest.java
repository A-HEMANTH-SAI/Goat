package testapiframework;
import static io.restassured.RestAssured.given;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.annotations.Test;

import genericutils.BaseAPIClass;
import genericutils.CreateProjectPojo;
import genericutils.EndPointsLibrary;
import genericutils.IConstants;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.response.Response;
import junit.framework.Assert;

public class ProjectTest extends BaseAPIClass{
	@Test
	public void createProjectTest1() throws SQLException {
		//create using API
		CreateProjectPojo cpp=new CreateProjectPojo("Elon Musk","Space X"+ju.randomNum(0, 1000),"On going",100);
		Response r=given().spec(reqSpec).body(cpp)
		.when().post(EndPointsLibrary.createProject);
		String expData=rau.getJsonPathData(r, "projectId");
		r.then().assertThat().statusCode(201);
		Reporter.log("Project created successfully using API",true);
		
		//verify using GUI
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		driver.get(IConstants.Base_URI);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input[@id='usernmae']")).sendKeys("rmgyantra");
		driver.findElement(By.xpath("//input[@id='inputPassword']")).sendKeys("rmgy@9999");
		driver.findElement(By.xpath("//button[.='Sign in']")).click();
		driver.findElement(By.xpath("//li/child::a[.='Projects']")).click();
		List<WebElement> allProjId=driver.findElements(By.xpath("//td[1]"));
		boolean flag=false;
		for(WebElement t:allProjId)
			if(t.getText().equalsIgnoreCase(expData)) {
				flag=true;
				break;
			}
		driver.quit();
		Assert.assertTrue(flag);
		Reporter.log("Project verified On GUI",true);
		
		
		//Verify in the DB
		String verifiedData=dbu.validateDBData("select * from project;", 1,expData );
		Assert.assertEquals(verifiedData, expData);
		Reporter.log("Project verified in DB"+"\n"+"TC is Pass",true);
	}
	
	@Test
	
	public void createProjectTest2() throws SQLException {
		//Create project using GUI
				WebDriverManager.chromedriver().setup();
				WebDriver driver=new ChromeDriver();
				driver.get(IConstants.Base_URI);
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
				driver.findElement(By.xpath("//input[@id='usernmae']")).sendKeys("rmgyantra");
				driver.findElement(By.xpath("//input[@id='inputPassword']")).sendKeys("rmgy@9999");
				driver.findElement(By.xpath("//button[.='Sign in']")).click();
				driver.findElement(By.xpath("//li/child::a[.='Projects']")).click();
				
				driver.findElement(By.xpath("//span[.='Create Project']")).click();
				String projectName="Lite"+ju.randomNum(0, 1000);
				Reporter.log("***"+projectName+"***",true);
				driver.findElement(By.xpath("//input[@name='projectName']")).sendKeys(projectName);
				//driver.findElement(By.xpath("//input[@name='teamSize']")).sendKeys("10");

				Select s=new Select(driver.findElement(By.xpath("//label[.='Project Status ']/following-sibling::select")));
				s.selectByVisibleText("Created");
				driver.findElement(By.xpath("//input[@name='createdBy']")).sendKeys("Jeff");
				driver.findElement(By.xpath("//input[@value='Add Project']")).submit();
				String projectID=driver.findElement(By.xpath("//td[.='"+projectName+"']/parent::tr/child::td[1]")).getText();
				driver.quit();
				Reporter.log("Created the Project using GUI",true);
				
				//GET project using API
				given().spec(reqSpec).pathParam("projectID", projectID)
				.when().get(EndPointsLibrary.getSingleProject+"{projectID}")
				.then().log().all();	
				Reporter.log("Got the project using API",true);
				
				//Verify project in DB
			String verifiedData=dbu.validateDBData("select * from project;", 1, projectID);
			Assert.assertEquals(verifiedData, projectID);
			Reporter.log("Verified in DB",true);
		
	}
}
