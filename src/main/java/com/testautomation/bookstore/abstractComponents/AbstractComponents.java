package com.testautomation.bookstore.abstractComponents;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.testautomation.bookstore.pageObjects.LoginPage;
import com.testautomation.bookstore.pageObjects.RegistrationPage;

public class AbstractComponents {
	
    WebDriver driver;

    /*constructor creation*/
    public AbstractComponents(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    /*WebElement creation*/
    
    //register button
    @FindBy(css=".ico-register")
    WebElement register;
    
    //login button
    @FindBy(css=".ico-login")
    WebElement login;
    
	/*method creation*/
	
    //explicit wait for visibility of WebElement method
	public void waitForElementToAppear(WebElement ele)
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(ele));
	}
	
    //explicit wait for visibility of list of WebElements method
	public void waitForElementsToAppear(List<WebElement> eles)
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElements(eles));
	}
	
    //explicit wait for visibility of locator method
	public void waitForLocatorToAppear(By locator, int TimeoutInSeconds)
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(TimeoutInSeconds));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	//go to registration page method
	public RegistrationPage goToRegistrationPage() {
		waitForElementToAppear(register);
		register.click();
		return new RegistrationPage(driver);
	}
	
	//go to login page method
	public LoginPage goToLoginPage() {
		waitForElementToAppear(login);
		login.click();
		return new LoginPage(driver);
	}
}
