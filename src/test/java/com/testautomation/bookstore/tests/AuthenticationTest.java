package com.testautomation.bookstore.tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
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
	
	@BeforeMethod(onlyForGroups = "Registration")
	public void navigateToRegistration() {
	    registrationPage = abstractComponents.goToRegistrationPage();
	}
	
	@Test(description = "Register a user with valid data",
			dataProvider = "getValidRegistrationData", 
			groups = {"Registration"}, 
			retryAnalyzer = (com.testautomation.bookstore.testComponents.Retry.class))
	public void registerValidUserTest(Map<String, String> input) throws IOException {
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
		String filePath = System.getProperty("user.dir") + "//src//test//java//com//testautomation//bookstore//data//authenticationData//ValidRegistrationData.json";
	    return getTestDataFromJson(filePath);
	}
	
	@Test(description = "Field-level registration validations (namely, first name, last name, email, password, confirm password validations) via data-driven tests",
			dataProvider = "getInvalidRegistrationData", groups = {"Registration"})
	public void registerInvalidUserTest(Map<String, String> input, List<String> expectedErrors) throws IOException {
		Reporter.log("Input data: " + input.toString(), true);
		
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
			String filePath = System.getProperty("user.dir") + "//src//test//java//com//testautomation//bookstore//data//authenticationData//InvalidRegistrationData.json";
			return getStructuredTestData(filePath, "input", "expectedErrors");
		}
		
	@Test(description = "Register a user with duplicate email id",
			dataProvider = "getDuplicateEmailData", groups = {"Registration"})
	public void registerWithDuplicateEmailTest(Map<String, String> input) {
	    String email = "test" + System.currentTimeMillis() + "@example.com";
	    
	    registrationPage.completeRegistration(
				input.get("gender"),
				input.get("fName"),
				input.get("lName"),
				email,
				input.get("password"),
				input.get("confirmPwd"));

	    registrationPage.navigateToRegisterPage();
	    
	    registrationPage.completeRegistration(
				input.get("gender"),
				input.get("fName"),
				input.get("lName"),
				email,
				input.get("password"),
				input.get("confirmPwd"));

	    String actualError = registrationPage.getDuplicateEmailError();
	    Assert.assertEquals(actualError, "The specified email already exists");
	}
	
	//Data provider method for registration with duplicate email id
	@DataProvider
	public Object[][] getDuplicateEmailData() throws IOException {
		String filePath = System.getProperty("user.dir") + "//src//test//java//com//testautomation//bookstore//data//authenticationData//DuplicateRegistrationData.json";
	    return getTestDataFromJson(filePath);
	}
	
	
	/*login test cases*/
	
	@BeforeMethod(onlyForGroups = "Login")
	public void navigateToLogin() {
	    loginPage = abstractComponents.goToLoginPage();
	}
	
	@Test(description = "Login a user with valid email and password",
			dataProvider = "getValidLoginData", groups = {"Login"})
	public void loginValidUserTest(Map<String, String> input) throws IOException {
		loginPage = abstractComponents.goToLoginPage();
		loginPage.login(
				input.get("email"),
				input.get("password")
		);
		Assert.assertEquals(loginPage.getEmailTextOnSuccess(), input.get("email"));
	}
	
	//data provider method for login with valid data
	@DataProvider
	public Object[][] getValidLoginData() throws IOException {
		String filePath = System.getProperty("user.dir") + "//src//test//java//com//testautomation//bookstore//data//authenticationData//ValidLoginData.json";
	    return getTestDataFromJson(filePath);
	}
	
	@Test(description = "Login a user with invalid email (field level validation)",
			dataProvider = "getInvalidEmailLoginData", groups = {"Login"})
	public void invalidEmailFormat_showsFieldErrorTest(Map<String, String> input) throws IOException {
		loginPage = abstractComponents.goToLoginPage();
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
		String filePath = System.getProperty("user.dir") + "//src//test//java//com//testautomation//bookstore//data//authenticationData//InvalidEmailLoginData.json";
	    return getTestDataFromJson(filePath);
	}
	
	@Test(description = "Form-level login validations (namely, email, password) via data-driven tests",
			dataProvider = "getInvalidLoginData", groups = {"Login"})
	public void invalidLoginData_showsFormLevelErrorsTest(Map<String, String> input, List<String> expectedErrors) throws IOException {
		loginPage = abstractComponents.goToLoginPage();
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
		String filePath = System.getProperty("user.dir") + "//src//test//java//com//testautomation//bookstore//data//authenticationData//InvalidLoginData.json";
		return getStructuredTestData(filePath, "input", "expectedErrors");
	}
	
	@Test(description = "Logout successfully",
			groups = {"Login"})
	public void testLogout() {
		loginPage = abstractComponents.goToLoginPage();	
		loginPage.login("ABone@email.com","A1Bone");
		String actualText = loginPage.logout();
		Assert.assertEquals(actualText, "Log in");
	}
	
}
