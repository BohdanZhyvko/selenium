package com.example.selenium.ide.tests;


import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NewTest {
	WebDriver driver;
	
	@BeforeClass
	public void setUp(){
		System.setProperty("webdriver.gecko.driver",
				"E:\\Lv-256.ATQC\\drivers\\win_x64\\geckodriver-v0.18.0-win64\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void testDayclass() {
		driver.get("http://regres.herokuapp.com/");
		driver.findElement(By.linkText("Забули пароль?")).click();
		driver.findElement(By.linkText("Зареєструватися")).click();
		driver.quit();

		driver.quit();
	}
}
