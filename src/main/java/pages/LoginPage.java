package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {

	static Logger logger=LogManager.getLogger(LoginPage.class.getName());
	private WebDriver driver;
	private WebElement userName, password, loginButton;
	public LoginPage(WebDriver driver)
	{
		this.driver=driver;
	}
	public HomePage loginToEmprix(String userName,String password)
	{
		this.userName=driver.findElement(By.xpath(""));
		this.password=driver.findElement(By.xpath(""));
		loginButton=driver.findElement(By.xpath(""));
		this.userName.sendKeys(userName);
		this.password.sendKeys(password);
		loginButton.click();
		return new HomePage(driver);
	}
	
}
