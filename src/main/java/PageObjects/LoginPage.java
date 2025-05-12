package PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
	
	/*web element creation*/
	
	//loginEmail
	@FindBy(css =".email")
	WebElement loginEmail;
	
	//loginPassword
	@FindBy(css =".password")
	WebElement loginPassword;
	
	//loginButton
	@FindBy(css =".login-button")
	WebElement loginButton;
	
	
	/*method creation*/
	
	//enter login email
	public void enterLoginEmail(String email) {
		loginEmail.sendKeys(email);
	}
	
	//enter login password
	public void enterLoginPassword(String password) {
		loginPassword.sendKeys(password);
	}
	
    //click log in button
    public void clickLogin() {
    	loginButton.click();
    }
    
    //login
    public void login(String email, String password) {
    	enterLoginEmail(email);
    	enterLoginPassword(password);
    	clickLogin();
    }
}
