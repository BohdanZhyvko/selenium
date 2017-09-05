package com.example.selenium.ide.tests;

import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DollarCourse {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@Test
	public void testCourseUSD() {
		System.setProperty("webdriver.gecko.driver",
				"E:\\Lv-256.ATQC\\drivers\\win_x64\\geckodriver-v0.18.0-win64\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get("https://kurs.com.ua/");
		assertEquals(driver.findElement(By.linkText("USD")).getText(), "USD");
		String selling = driver.findElement(By.xpath("//div[@id='elBoardSummary']/table/tbody/tr/td[4]/span"))
				.getText();
		String buying = driver.findElement(By.cssSelector("span.ipsKurs_rate")).getText();
		System.out.println("Curse of USD: \n" + "Buying: " + buying + ", Selling: " + selling);
		driver.quit();
	}
}
