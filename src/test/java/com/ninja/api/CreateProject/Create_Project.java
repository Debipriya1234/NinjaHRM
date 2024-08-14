package com.ninja.api.CreateProject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.ninza.hrm.api.baseClass.BaseAPIClass;

public class Create_Project  extends BaseAPIClass{
	@Test
	
	public void createProjectTest() throws Throwable {
		String BASEURI= fLib.getDataFromPropertiesFile("BASEUri");
		ChromeDriver driver=new ChromeDriver();
		driver.get(BASEURI);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.findElement(By.id("username")).sendKeys("rmgyantra");
		driver.findElement(By.name("password")).sendKeys("rmgy@9999");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//a[text()='Projects']")).click();
		driver.findElement(By.xpath("//button/span[text()='Create Project']")).click();
		driver.findElement(By.xpath("//div/input[@name='projectName']")).sendKeys("Dollibar123");
		driver.findElement(By.name("createdBy")).sendKeys("Debipriya");
		WebElement ele=driver.findElement(By.xpath("//label[@class='col-form-label']/following-sibling::select"));
		Select s=new Select(ele);
		s.selectByVisibleText("Created");
		
	}

}
