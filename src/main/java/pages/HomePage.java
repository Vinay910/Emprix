package pages;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class HomePage {
	
	static Logger logger=LogManager.getLogger(HomePage.class.getName());
	private WebDriver driver;
	public HomePage(WebDriver driver)
	{
		this.driver=driver;
	}
}
