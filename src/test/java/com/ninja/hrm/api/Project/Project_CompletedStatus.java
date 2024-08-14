package com.ninja.hrm.api.Project;

import static io.restassured.RestAssured.given;

import java.time.Duration;

import org.hamcrest.Matchers;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.ninza.hrm.api.baseClass.BaseAPIClass;
import com.ninza.hrm.api.pojoclass.ProjectPojo;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Project_CompletedStatus extends BaseAPIClass{
	
	ProjectPojo pojoObj;
	
	@Test
    public void addProjectWithCompletedStatus() throws Throwable {
    	
    	String BASEURI= fLib.getDataFromPropertiesFile("BASEUri");
		String projectName="ERP_"+jLib.getRandomNumber();
		
		//create an object for pojo class
		pojoObj= new ProjectPojo(projectName, "Completed", "Debipriya", 0);
		
		//Verify the projectName and msg and status in API layer
		Response resp= given().contentType(ContentType.JSON)
				              .body(pojoObj)
				              .when()
				              .post("http://49.249.28.218:8091/addProject");
		resp.then().assertThat().log().all().statusCode(201);
		resp.then().body("msg", Matchers.equalTo("Successfully Added"));
		resp.then().assertThat().time(Matchers.lessThan(4000L));
		
		String prjId=resp.jsonPath().get("projectId");
		System.out.println(prjId);
		String status=resp.jsonPath().get("status");
		
		resp.then().assertThat().body("status", Matchers.equalTo(status));
		resp.then().body("projectId", Matchers.equalTo(prjId));
		
		boolean flag=dbLib.executeQueryVerifyAndGetData("select * from project", 5, status);
		Assert.assertTrue(flag,"Project in DB is not verified");
		
		ChromeDriver driver=new ChromeDriver();
		driver.get(BASEURI);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.findElement(By.id("username")).sendKeys("rmgyantra");
		driver.findElement(By.name("password")).sendKeys("rmgy@9999");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//a[text()='Projects']")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.findElement(By.xpath("//div/input[@placeholder='Search by Project Id']")).sendKeys(prjId);
		String txt=driver.findElement(By.xpath("//td[text()='"+prjId+"']/parent::tr[@class='tr']/descendant::td[text()='"+status+"']")).getText();
		Assert.assertEquals(status, txt);
		//push
		//push1
		//push2

    	
    	
    	
    }


}
