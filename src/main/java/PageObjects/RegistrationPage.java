package PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import AbstractComponents.AbstractComponents;

public class RegistrationPage extends AbstractComponents{
	
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
	
	/*method creation*/
	
	//select gender
	public void selectGender(String gender) {
		if(gender.equalsIgnoreCase("Male")) {
			genderMale.click();
		}
		else if(gender.equalsIgnoreCase("Female")) {
			genderFemale.click();
		}
	}
	
	//enter first name 
	public void enterFirstName(String fName) {
		firstName.sendKeys(fName);
	}
	
	//enter last name 
	public void enterLastName(String lName) {
		lastName.sendKeys(lName);
	}
	
	//enter email
	public void enterEmail(String email) {
		userEmail.sendKeys(email);
	}
	
	//enter password 
    public void enterPassword(String password) {
        userPassword.sendKeys(password);
    }

    //enter confirm passowrd
    public void enterConfirmPassword(String confirmPwd) {
        confirmPassword.sendKeys(confirmPwd);
    }
    
    //click register button
    public void clickRegister() {
        registerButton.click();
    }
    
    //click continue button
    public void clickContinue() {
    	continueButton.click();
    }
    
    //complete Registration
    public void completeRegistration(String gender, String fName, String lName, String email, String password, String confirmPwd) {
    	selectGender(gender);
    	enterFirstName(fName);
        enterLastName(lName);
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(confirmPwd);
        clickRegister();
        waitForElementToAppear(continueButton);
        clickContinue();
    }
}
