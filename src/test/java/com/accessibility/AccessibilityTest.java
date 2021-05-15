package com.accessibility;

import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.deque.axe.AXE;
import io.github.bonigarcia.wdm.WebDriverManager;

public class AccessibilityTest {

	WebDriver driver;
	private static final URL scriptURL = AccessibilityTest.class.getResource("/axe.min.js");

	@BeforeTest
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("http://edflux-int-app-01.springernature.com:8000/EducationApplication/signin.jsp");
	}

	@Test
	public void accessibilityTest() {

		JSONObject responseJSON = new AXE.Builder(driver, scriptURL).analyze();
		JSONArray violations = responseJSON.getJSONArray("violations");
		
		if(violations.length() == 0) {
			System.out.println("No Errors");
		}
		
		else {
				AXE.writeResults("accessibilityTest", responseJSON);
				Assert.assertTrue(false, AXE.report(violations));
		}
	}

	// Ensure we close the WebDriver after finishing
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
