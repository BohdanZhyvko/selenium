package com.example.selenium.ide.tests.regres;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SubclassDeleteTest {
	private WebDriver driver;
	private String baseUrl;
	private WebDriverWait wait;

	@BeforeClass
	public void setUp() {
		System.setProperty("webdriver.gecko.driver", "resources\\geckodriver-v0.18.0-win64\\geckodriver.exe");
		driver = new FirefoxDriver();
		wait = new WebDriverWait(driver, 10);
		baseUrl = "http://regres.herokuapp.com/";
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get(baseUrl);
		driver.findElement(By.id("login")).clear();
		driver.findElement(By.id("login")).sendKeys("registrator");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("registrator");
		driver.findElement(By.xpath("//*[@id='loginForm']//button[@type='submit']")).click();
		driver.findElement(By.xpath("//*[@id='navigationbar']//a[@href = '/registrator/resourcetypes/show-res-types']"))
				.click();
		driver.findElement(By.xpath("//*[@id='changeLanguage']/*[@value = 'en']")).click();
		// add new subclass
		driver.findElement(By.linkText("Add new subclass")).click();
		driver.findElement(By.name("typeName")).clear();
		driver.findElement(By.name("typeName")).sendKeys("Test");
		driver.findElement(By.id("clickmeshow")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("myparam0")));
		driver.findElement(By.id("myparam0")).clear();
		driver.findElement(By.id("myparam0")).sendKeys("123450");
		driver.findElement(By.id("myparam1")).clear();
		driver.findElement(By.id("myparam1")).sendKeys("123450");
		driver.findElement(By.xpath("//*[@value = 'linearParameters']"));
		driver.findElement(By.id("valid")).click();
	}

	@Test
	public void testConfirmMessageAppear() {
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//td[text() = 'земельний']/following-sibling::*//*[@href]"))).click();
		driver.findElement(By.xpath("//td[text() = 'земельний']/following-sibling::*//*[@href]")).click();

		assertEquals(driver.findElement(By.className("bootbox-body")).getText(),
				"Do you really want to delete this class?");
	}

	@Test
	public void testCancelDelettingByX() {
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//td[text() = 'земельний']/following-sibling::*//*[@href]"))).click();
		// driver.findElement(By.xpath("//td[text() =
		// 'земельний']/following-sibling::*/div/a")).click();
		driver.findElement(By.xpath("//div[@class='bootbox-body']/preceding-sibling::button")).click();
		assertEquals(driver.findElement(By.xpath("//*[@id='datatable']//tr[1]/td[1]")).getText(), "земельний");
	}

	@Test
	public void testCancelDelettingByCancelButton() {
		driver.findElement(By.xpath("//td[text() = 'Зайці']/following-sibling::*/div/a")).click();
		driver.findElement(By.xpath("//button[text() = 'Cancel']")).click();

		assertEquals(driver.findElement(By.xpath("//*[@id='datatable']//tr[2]/td[1]")).getText(), "Зайці");
	}

	@Test
	public void testDeleteByOkButton() {

		driver.findElement(By.xpath("//td[text() = 'Test']/following-sibling::*/div/a")).click();
		driver.findElement(By.xpath("//button[text() = 'OK']")).click();

		assertFalse(isElementPresent(By.partialLinkText("Test")));
	}

	@Test // existing class is not deleted if resources already exist
	public void testCancelDelettingBySystem() {
		driver.findElement(By.xpath("//td[text() = 'земельний']/following-sibling::*//*[@href]")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text() = 'OK']"))).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("bootbox-body")));
		assertEquals(driver.findElement(By.className("bootbox-body")).getText(),
				"This subclass cannot be deleted because resources already exist");
		driver.findElement(By.xpath("//button[text() = 'OK']")).click();
		assertEquals(driver.findElement(By.xpath("//*[@id='datatable']//tr[1]/td[1]")).getText(), "земельний");
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
