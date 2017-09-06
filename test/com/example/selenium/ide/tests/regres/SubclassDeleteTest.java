package com.example.selenium.ide.tests.regres;

import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SubclassDeleteTest {
	private WebDriver driver;
	private String baseUrl;

	@BeforeClass
	public void setUp() {
		System.setProperty("webdriver.gecko.driver",
				"D:\\Lv-256.ATQC\\resurces\\drivers\\geckodriver-v0.18.0-win64\\geckodriver.exe");
		driver = new FirefoxDriver();
		baseUrl = "http://regres.herokuapp.com/";
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		driver.get(baseUrl);
		driver.findElement(By.id("login")).sendKeys("registrator");
		driver.findElement(By.id("password")).sendKeys("registrator");
		driver.findElement(By.xpath("//*[@id='loginForm']//button[@type='submit']")).click();
		driver.findElement(By.xpath("//*[@id='navigationbar']//a[@href = '/registrator/resourcetypes/show-res-types']"))
				.click();
		driver.findElement(By.xpath("//*[@id='changeLanguage']/*[@value = 'en']")).click();
	}

	@Test
	public void testConfirmMessageAppear() {
		driver.findElement(By.xpath("//td[text() = 'Зайці']/following-sibling::*/div/a")).click();

		assertEquals(driver.findElement(By.className("bootbox-body")).getText(),//("//button/following-sibling::div[@class='bootbox-body']")).getText(),
				"Do you really want to delete this class?");
	}

	@Test
	public void testCancelDelettingByX() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//td[text() = 'земельний']/following-sibling::*/div/a")).click();
		driver.findElement(By.xpath("//div[@class='bootbox-body']/preceding-sibling::button")).click();
		assertEquals(driver.findElement(By.xpath("//*[@id='datatable']//tr[1]/td[1]")).getText(), "земельний");
	}

	@Test
	public void testCancelDelettingByCancelButton() {
		driver.findElement(By.xpath("//td[text() = 'Зайці']/following-sibling::*/div/a")).click();
		driver.findElement(By.xpath("//button[text() = 'Cancel']")).click();

		assertEquals(driver.findElement(By.xpath("//*[@id='datatable']//tr[2]/td[1]")).getText(), "Зайці");
	}

	@Test // existing class is not deleted if resources already exist
	public void testCancelDelettingByOKConfirmMessageAppear() {
		driver.findElement(By.xpath("//td[text() = 'земельний']/following-sibling::*/div/a")).click();
		driver.findElement(By.xpath("//button[text() = 'OK']")).click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(driver.findElement(By.className("bootbox-body")).getText(),//(By.xpath("//button[text() ='×']/following-sibling::div")).getText(),
				"This subclass cannot be deleted because resources already exist");
		driver.findElement(By.xpath("//button[text() = 'OK']")).click();
		assertEquals(driver.findElement(By.xpath("//*[@id='datatable']//tr[1]/td[1]")).getText(), "земельний");
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
