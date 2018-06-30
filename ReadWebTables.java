package webtables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ReadWebTables {

	String url = "file:///Users/kaan/eclipse-workspace/automation-project/src/test/java/webtables/webtable.html";

	WebDriver driver;

	@BeforeClass // runs once for all tests
	public void setUp() {
		System.out.println("Setting up WebDriver in BeforeClass...");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().fullscreen();
	}

	@Test
	public void readScores() {
		driver.get(url);
		//Read whole webtable data and print
		WebElement table = driver.findElement(By.tagName("table"));
		System.out.println(table.getText());
		
		//find out how many rows in the table
		List<WebElement> rows= driver.findElements(By.xpath("//table[@id='worldcup']/tbody/tr"));
		System.out.println("Number of data rows: " + rows.size());
		
		//print all table headers. one by one
		//get all headers into a list
		//use a loop to print out
		String headerPath  = "//table[@id='worldcup']/thead/tr/th";
		List<WebElement> headers = driver.findElements(By.xpath(headerPath));
		
		List<String> expHeaders = Arrays.asList("Team1","Score","Team2");
		List<String> actHeaders = new ArrayList<>();
		
		for (WebElement h : headers) {
			actHeaders.add(h.getText());
		}
		
		SoftAssert softAssert = new SoftAssert();
		
		softAssert.assertEquals(actHeaders, expHeaders);
		
		//write xpath and findelement gettext -> needs to print Egypt
		String egptPath = "//table[@id = 'worldcup']/tbody/tr[3]/td[3]";
		softAssert.assertEquals(driver.findElement(By.xpath(egptPath)).getText(),"Egypt");
		
		//loop it and print all data
		//get number of rows, columns then nested loop
		int rowsCount = driver.findElements(By.xpath("//table[@id='worldcup']/tbody/tr")).size();
		int colsCount = driver.findElements(By.xpath("//table[@id='worldcup']/thead/tr/th")).size();
		
		System.out.println("===============");
		
		for(int rowNum = 1; rowNum <= rowsCount; rowNum++) {
			for(int col = 1; col <= colsCount; col++) {
				String xpath = "//table[@id='worldcup']/tbody/tr["+rowNum+"]/td["+col+"]";
				String tdData = driver.findElement(By.xpath(xpath)).getText();
				System.out.print(tdData +"  \t");
			}
			System.out.println();
		}

		//https://forms.zohopublic.com/murodil/report/Applicants/reportperma/DibkrcDh27GWoPQ9krhiTdlSN4_34rKc8ngubKgIMy8

		softAssert.assertAll();
		
	}

	@Test
	public void applicantsData() {
		driver.get(
				"https://forms.zohopublic.com/murodil/report/Applicants/reportperma/DibkrcDh27GWoPQ9krhiTdlSN4_34rKc8ngubKgIMy8");

		printTableData("reportTab");

	}

	public void printTableData(String id) {
		int rowsCount = driver.findElements(By.xpath("//table[@id='" + id + "']/tbody/tr")).size();
		int colsCount = driver.findElements(By.xpath("//table[@id='" + id + "']/thead/tr/th")).size();

		System.out.println("===============");

		for (int rowNum = 1; rowNum <= rowsCount; rowNum++) {
			for (int col = 1; col <= colsCount; col++) {
				String xpath = "//table[@id='" + id + "']/tbody/tr[" + rowNum + "]/td[" + col + "]";
				String tdData = driver.findElement(By.xpath(xpath)).getText();
				System.out.print(tdData + "  \t");
			}
			System.out.println();
		}
	}
}
