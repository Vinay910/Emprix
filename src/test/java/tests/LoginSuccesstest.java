package tests;


import org.testng.annotations.Test;


public class LoginSuccesstest extends TestBase {

	@Test
	public void loginSucessTest()
	{
		String userName,password;
		userName=prop.getProperty("USERNAME");
		password=prop.getProperty("PASSWORD");
		loginPage.loginToEmprix(userName,password);
	}
}
