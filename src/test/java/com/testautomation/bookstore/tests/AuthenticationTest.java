package com.testautomation.bookstore.tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.testautomation.bookstore.pageObjects.LoginPage;
import com.testautomation.bookstore.pageObjects.RegistrationPage;
import com.testautomation.bookstore.testComponents.BaseTest;

@Listeners(com.testautomation.bookstore.testComponents.Listeners.class)
public class AuthenticationTest extends BaseTest {

	RegistrationPage registrationPage;
	LoginPage loginPage;
	String duplicateEmail;
	
	/*registration test cases*/
	
	//test for registration with valid data - positive
	@Test(dataProvider = "getValidRegistrationData", groups = {"Registration"}, retryAnalyzer = (com.testautomation.bookstore.testComponents.Retry.class))
	public void registerValidUserTest(Map<String, String> input) throws IOException {
		registrationPage = new RegistrationPage(driver);
		registrationPage.completeRegistration(
				input.get("gender"),
				input.get("fName"),
				input.get("lName"),
				input.get("email"),
				input.get("password"),
				input.get("confirmPwd")
		);
		Assert.assertEquals(registrationPage.getRegComText(), "Your registration completed");
	}
	
	//data provider method for registration with valid data
	@DataProvider
	public Object[][] getValidRegistrationData() throws IOException {
		List<Map<String, Object>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//com//testautomation//bookstore//data//authenticationData//ValidRegistrationData.json");
		Object[][] testData = new Object[data.size()][1];
	    for (int i = 0; i < data.size(); i++) {
	        testData[i][0] = data.get(i);
	    }
	    return testData;
	}
	
	//test for registration with invalid data - negative (field level validation) 
	@Test(dataProvider = "getInvalidRegistrationData", groups = {"Registration"})
	public void registerInvalidUserTest(Map<String, String> input, List<String> expectedErrors) throws IOException {
		registrationPage = new RegistrationPage(driver);
		registrationPage.completeRegistration(
				input.get("gender"),
				input.get("fName"),
				input.get("lName"),
				input.get("email"),
				input.get("password"),
				input.get("confirmPwd")
		);
		List<String> actualErrors = registrationPage.getAllErrorMessages();
		for (String expected : expectedErrors) {
	        Assert.assertTrue(actualErrors.contains(expected), "Expected error not found: " + expected);
	    }
		
	}
	
	//data provider method for registration with invalid data (field level validation)
	@DataProvider
	public Object[][] getInvalidRegistrationData() throws IOException {
			List<Map<String, Object>> testData = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//com//testautomation//bookstore//data//authenticationData//InvalidRegistrationData.json");
		    Object[][] data = new Object[testData.size()][2];

		    for (int i = 0; i < testData.size(); i++) {
		        Map<String, Object> item = testData.get(i);
		        @SuppressWarnings("unchecked")
		        Map<String, String> input = (Map<String, String>) item.get("input");

		        @SuppressWarnings("unchecked")
		        List<String> expectedErrors = (List<String>) item.get("expectedErrors");

		        data[i][0] = input;
		        data[i][1] = expectedErrors;
		    }

		    return data;
		}
		
	//test for registration with invalid data - negative (duplicate email id)
	@Test(groups = {"Registration"})
	public void registerWithDuplicateEmailTest() {
	    registrationPage = new RegistrationPage(driver);
	    String email = "test" + System.currentTimeMillis() + "@example.com";
	    registrationPage.completeRegistration("Female", "XY", "XY", email, "ABCDEF", "ABCDEF");

	    registrationPage.navigateToRegisterPage();
	    registrationPage.completeRegistration("Female", "XY", "XY", email, "ABCDEF", "ABCDEF");

	    String actualError = registrationPage.getDuplicateEmailError();
	    Assert.assertEquals(actualError, "The specified email already exists");
	}
	
	
	/*login test cases*/
	//test for login with valid data - positive
	@Test(dataProvider = "getValidLoginData", groups = {"Login"})
	public void loginValidUserTest(Map<String, String> input) throws IOException {
		loginPage = new LoginPage(driver);
		loginPage.login(
				input.get("email"),
				input.get("password")
		);
		Assert.assertEquals(loginPage.getEmailTextOnSuccess(), input.get("email"));
	}
	
	//data provider method for login with valid data
	@DataProvider
	public Object[][] getValidLoginData() throws IOException {
		List<Map<String, Object>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//com//testautomation//bookstore//data//authenticationData//ValidLoginData.json");
		Object[][] testData = new Object[data.size()][1];
	    for (int i = 0; i < data.size(); i++) {
	        testData[i][0] = data.get(i);
	    }
	    return testData;
	}
	
	//test for login with invalid email - negative
	@Test(dataProvider = "getInvalidEmailLoginData", groups = {"Login"})
	public void invalidEmailFormat_showsFieldErrorTest(Map<String, String> input) throws IOException {
		loginPage = new LoginPage(driver);
		loginPage.login(
				input.get("email"),
				input.get("password")
		);
	    String actualError = loginPage.getWrongEmailError();
	    String expectedError = "Please enter a valid email address.";
	    Assert.assertEquals(actualError, expectedError);
	}
	
	//data provider method for login with invalid email data
	@DataProvider
	public Object[][] getInvalidEmailLoginData() throws IOException {
		List<Map<String, Object>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//com//testautomation//bookstore//data//authenticationData//InvalidEmailLoginData.json");
		Object[][] testData = new Object[data.size()][1];
	    for (int i = 0; i < data.size(); i++) {
	        testData[i][0] = data.get(i);
	    }
	    return testData;
	}
	
	//test for login with invalid login details - negative
	@Test(dataProvider = "getInvalidLoginData", groups = {"Login"})
	public void invalidLoginData_showsFormLevelErrorsTest(Map<String, String> input, List<String> expectedErrors) throws IOException {
		loginPage = new LoginPage(driver);
		loginPage.login(
				input.get("email"),
				input.get("password")
		);
		String actualSpanError = loginPage.getSpanLoginError();
	    String expectedSpanError = "Login was unsuccessful. Please correct the errors and try again.";
		Assert.assertEquals(actualSpanError, expectedSpanError);
		
		List<String> errors = loginPage.getLiLoginError();
		for (String expected : expectedErrors) {
	        Assert.assertTrue(errors.contains(expected), "Expected error not found: " + expected);
	    }
	}
	
	//data provider method for login with empty password data
	@DataProvider
	public Object[][] getInvalidLoginData() throws IOException {
		List<Map<String, Object>> testData = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//com//testautomation//bookstore//data//authenticationData//InvalidLoginData.json");
	    Object[][] data = new Object[testData.size()][2];

	    for (int i = 0; i < testData.size(); i++) {
	        Map<String, Object> item = testData.get(i);
	        @SuppressWarnings("unchecked")
	        Map<String, String> input = (Map<String, String>) item.get("input");

	        @SuppressWarnings("unchecked")
	        List<String> expectedErrors = (List<String>) item.get("expectedErrors");

	        data[i][0] = input;
	        data[i][1] = expectedErrors;
	    }
	    return data;
	}
	
	//test for successful logout
	@Test(groups = {"Login"})
	public void testLogout() {
		loginPage = new LoginPage(driver);
		loginPage.login("ABone@email.com","A1Bone");
		String actualText = loginPage.logout();
		Assert.assertEquals(actualText, "Log in");
	}
	
}
