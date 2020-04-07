package tests;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;

import com.google.common.io.Files;

import pages.HomePage;
import pages.LoginPage;

public class TestBase {

	protected HomePage homePage;
	protected LoginPage loginPage;

	protected SoftAssert sf;
	protected Properties prop;
	protected DesiredCapabilities caps;
	private WebDriver normalDriver;
	private String browserType = "firefox";
	private String userPath;

	private void propLoad() {
		userPath = System.getProperty("user.dir");
		prop = System.getProperties();
		try {
			prop.load(new FileInputStream(new File(userPath + "/src/main/resources/Prop.properties")));
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@BeforeClass
	public void setUp() throws MalformedURLException {
		caps = new DesiredCapabilities();
		propLoad();
		if (browserType.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", userPath + "/src/main/resources/chromedriver.exe");
			normalDriver = new ChromeDriver();
		} else if (browserType.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", userPath + "/src/main/resources/geckodriver.exe");
			normalDriver = new FirefoxDriver();
		} else if (browserType.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", userPath + "/src/main/resources/IEDriverServer.exe");
			caps.setCapability("requireWindowFocus", true);
			caps.setCapability("ignoreProtectedModeSettings", true);
			caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, false);
			normalDriver = new InternetExplorerDriver(caps);
		}
		sf = new SoftAssert();
		goToEmprix();
		normalDriver.manage().window().maximize();
		normalDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		loginPage = new LoginPage(normalDriver);
	}

	private void goToEmprix() {
		normalDriver.get(prop.getProperty("URL"));
	}

	@AfterClass
	public void tearDown() {
		Object[] win = normalDriver.getWindowHandles().toArray();
		for (int i = 0; i < win.length; i++) {
			normalDriver.switchTo().window(win[i].toString());
			normalDriver.close();
		}
	}

	@AfterMethod
	public void captureFailure(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {

			TakesScreenshot camera = (TakesScreenshot) normalDriver;
			File file = camera.getScreenshotAs(OutputType.FILE);
			try {
				long time = System.currentTimeMillis();
				new File(userPath + "/src/main/java/screenshot/" + time).mkdir();
				Files.move(file,
						new File(userPath + "/src/main/java/screenshot/" + time + "/" + result.getName() + ".jpg"));
			} catch (Exception e) {
				e.getMessage();
			}
		}
	}
}
