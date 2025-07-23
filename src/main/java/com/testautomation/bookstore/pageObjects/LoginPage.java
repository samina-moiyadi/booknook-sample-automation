package com.testautomation.bookstore.pageObjects;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.testautomation.bookstore.abstractComponents.AbstractComponents;

public class LoginPage extends AbstractComponents{
	
	/* constructor creation*/
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    /*object creation*/
    HomePage homePage;
	
	/*web element creation*/
	
	//loginEmail
	@FindBy(css =".email")
	WebElement loginEmail;
	
	//loginPassword
	@FindBy(css =".password")
	WebElement loginPassword;
	
	//submitLoginButton
	@FindBy(css =".login-button")
	WebElement submitLoginButton;
	
	//verification email text
	@FindBy(xpath ="//div[@class='header-links']/ul/li//a[@class='account']")
	WebElement emailVerify;
	
	//logoutButton
	@FindBy(css=".ico-logout")
	WebElement logoutButton;
	
	//loginButton
	@FindBy(css=".ico-login")
	WebElement loginButton;
	
    /*Error WebElement creation*/
    //field validation error
    @FindBy(css = ".field-validation-error")
    WebElement emailError;
    
    //validation summary error-span
    @FindBy(xpath = "//div[@class='validation-summary-errors']/span")
    WebElement spanLoginError;
    
    //validation summary error-li list
    @FindBy(xpath = "//div[@class='validation-summary-errors']/ul/li")
    List<WebElement> liLoginError;
    
    
	/*method creation*/
	
	//enter login email method
	public void enterLoginEmail(String email) {
		if (email != null)
			loginEmail.sendKeys(email);
		else
			loginEmail.sendKeys("");
	}
	
	//enter login password method
	public void enterLoginPassword(String password) {
		if (password != null)
			loginPassword.sendKeys(password);
		else
			loginPassword.sendKeys("");
	}
	
    //click log in button method
    public void clickLogin() {
    	submitLoginButton.click();
    }
    
    //login method
    public HomePage login(String email, String password) {
    	goToLoginPage();
    	enterLoginEmail(email);
    	enterLoginPassword(password);
    	clickLogin();
    	homePage = new HomePage(driver);
    	return homePage;
    }
    
    //get email id text for successful login verification method
    public String getEmailTextOnSuccess() {
    	waitForElementToAppear(emailVerify);
    	return emailVerify.getText();
    }
    
    //get wrong email id entered error message method
    public String getWrongEmailError() {
    	return emailError.getText();
    }
    
    //get span (form level validation) error message method
    public String getSpanLoginError() {
    	return spanLoginError.getText();
    }
    
    //get all li (form level validation) error message method
    public List<String> getLiLoginError() {
    	List<String> errorLiLoginList = liLoginError.stream().map(WebElement::getText).filter(text -> !text.isEmpty()).collect(Collectors.toList());
    	return errorLiLoginList;
    }
    
    //logout method
    public String logout() {
    	waitForElementToAppear(logoutButton);
    	logoutButton.click();
    	waitForElementToAppear(loginButton);
    	return loginButton.getText();
    }
    
}
