package JUnitPackage;

import org.junit.*;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;

public class JUnitExample {
	WebDriver driver;

	@Before
	public void setUp() throws Exception {
		driver = new ChromeDriver();
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
		driver.manage().window().maximize();
		driver.get("http://webdriver.io");
		System.out.println("SetUp complete");
	}

	@Test
	public void Click() throws Exception {
		try {
			SelectLinkText("API");
			
			sendInfo("Click");
			
			testUrlCustomWord("Click");
			
			SelectLinkText("Protocols");
			
			CompareExpected();
			
			TimeUnit.MINUTES.sleep(1);
			System.out.println("Execution complete");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void TestSearchSnapshot() throws Exception {
		try {
			sendInfo("takeHeapSnapshot");
			
			testUrl("takeHeapSnapshot");
			
			CompareExpected();
			
			TimeUnit.MINUTES.sleep(1);
			System.out.println("Execution complete");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void TestRemoveRecent() throws Exception {
		try {
			sendInfo("Click");
			testUrlCustomWord("Click");
			RemoveRecent();
			TimeUnit.MINUTES.sleep(500);
			System.out.println("Execution complete");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void RemoveRecent() {
		WebElement searchBoxLabel = driver.findElement(By.cssSelector(".DocSearch-Button"));
		searchBoxLabel.click();
		
		WebDriverWait wait = new WebDriverWait(driver, 3);
		WebElement searchRes = wait.until(ExpectedConditions.elementToBeClickable(By.id("docsearch-item-0")));
		if (searchRes.isDisplayed()) {
			WebElement removeItem = driver.findElement(By.xpath("//*[@id=\"docsearch-item-0\"]/a/div/div[4]/button"));
			removeItem.click();
			Assert.assertTrue(searchRes.isDisplayed());
		}
	}

	public void CompareExpected() {
		String[] expected = { "", "WebDriver Protocol", "Appium", "Mobile JSON Wire Protocol", "Chromium", "Firefox",
				"Sauce Labs", "Selenium Standalone", "JSON Wire Protocol" };

		for (int index = 1; index <= 8; index++) {
			WebElement d = driver.findElement(
					By.xpath("//*[@id=\"__docusaurus\"]/div[3]/div/aside/div/nav/ul/li[3]/ul/li[" + index + "]/a"));
			Assert.assertTrue(expected[index].equals(d.getAttribute("innerText")));
		}
	}

	public void SelectLinkText(String linkText) {
		WebElement protocolEl = driver.findElement(By.linkText(linkText));
		protocolEl.click();
	}

	public void sendInfo(String searchWord) {
		WebElement searchBoxLabel = driver.findElement(By.cssSelector(".DocSearch-Button"));
		searchBoxLabel.click();
		
		WebDriverWait wait = new WebDriverWait(driver, 3);
		WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.id("docsearch-input")));
		searchBox.sendKeys(searchWord);

		wait.until(ExpectedConditions.textToBePresentInElementValue(searchBox, searchWord));
		WebElement searchRes = wait.until(ExpectedConditions.elementToBeClickable(By.id("docsearch-item-0")));
		searchRes.click();

	}

	public void testUrlCustomWord(String searchWord) {
		String URL = driver.getCurrentUrl();
		Assert.assertEquals(URL, "https://webdriver.io/docs/api/element/" + searchWord.toLowerCase() + "/");
	}

	public void testUrl(String searchWord) {
		String URL = driver.getCurrentUrl();
		Assert.assertTrue(URL.contains(searchWord.toLowerCase()));
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

}
