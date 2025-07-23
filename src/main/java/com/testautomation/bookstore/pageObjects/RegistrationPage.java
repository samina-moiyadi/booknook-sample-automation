package com.testautomation.bookstore.pageObjects;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.testautomation.bookstore.abstractComponents.AbstractComponents;

public class RegistrationPage extends AbstractComponents{
	
	/* constructor creation*/
    WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	
    
	/*web element creation*/
	
	//genderMale
	@FindBy(id = "gender-male")
	WebElement genderMale;
	
	//genderFemale
	@FindBy(id = "gender-female")
	WebElement genderFemale;
	
	//firstName
	@FindBy(id = "FirstName")
	WebElement firstName;
	
	//lastName
	@FindBy(id = "LastName")
	WebElement lastName;
	
	//userEmail
	@FindBy(id = "Email")
	WebElement userEmail;
	
	//userPassword
    @FindBy(id = "Password")
    WebElement userPassword;

    //confirmPassword
    @FindBy(id = "ConfirmPassword")
    WebElement confirmPassword;
    
    //registerButton
    @FindBy(css = "input[name='register-button']")
    WebElement registerButton;
    
    //continueButton
    @FindBy(css = "input[value='Continue']")
    WebElement continueButton;
    
    //registration complete text
    @FindBy(css = ".result")
    WebElement regComplete;
    
    
    /*Error WebElement creation*/
    //field validation error list
    @FindBy(css = ".field-validation-error")
    List<WebElement> errorElements;
    
    //validation summary error (duplicate email error)
    @FindBy(css = ".validation-summary-errors")
    WebElement errorValSum;
    
	
	/*method creation*/
	
	//select gender method
	public void selectGender(String gender) {
		if(gender != null) {
			if(gender.equalsIgnoreCase("Male"))
				genderMale.click();
			else if(gender.equalsIgnoreCase("Female"))
				genderFemale.click();
		}
	}
	
	//enter first name method
	public void enterFirstName(String fName) {
		if (fName != null)
			firstName.sendKeys(fName);
		else
			firstName.sendKeys("");
	}
	
	//enter last name method
	public void enterLastName(String lName) {
		if (lName != null)
			lastName.sendKeys(lName);
		else
			lastName.sendKeys("");
	}
	
	//enter email method
	public void enterEmail(String email) {
		if (email != null)
			userEmail.sendKeys(email);
		else
			userEmail.sendKeys("");
	}
	
	//enter password method
    public void enterPassword(String password) {
		if (password != null)
			userPassword.sendKeys(password);
		else
			userPassword.sendKeys("");
    }

    //enter confirm password method
    public void enterConfirmPassword(String confirmPwd) {
		if (confirmPwd != null)
			confirmPassword.sendKeys(confirmPwd);
		else
			confirmPassword.sendKeys("");
    }
    
    //click register button method
    public void clickRegister() {
        registerButton.click();
    }
    
    //click continue button method
    public void clickContinue() {
    	continueButton.click();
    }
    
    //complete Registration method
    public void completeRegistration(String gender, String fName, String lName, String email, String password, String confirmPwd) {
    	goToRegistrationPage();
    	selectGender(gender);
    	enterFirstName(fName);
        enterLastName(lName);
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(confirmPwd);
        clickRegister();     
    }
    
    //get registration complete text method
    public String getRegComText() {
    	waitForElementToAppear(continueButton); 
    	String regComText = regComplete.getText();
    	continueButton.click();
    	return regComText;
    }
    
    //get field level error messages method
    public List<String> getAllErrorMessages() {
    	List<String> errorMessages = errorElements.stream().map(WebElement::getText).filter(text -> !text.isEmpty()).collect(Collectors.toList());
    	return errorMessages;
    }
    
    //get duplicate email id error message method
    public String getDuplicateEmailError() {
    	waitForElementToAppear(errorValSum);
    	return errorValSum.getText();
    }

    //logging off and back to registration page after registration method
	public void navigateToRegisterPage() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.logout();
		goToRegistrationPage();
		
	}
}
