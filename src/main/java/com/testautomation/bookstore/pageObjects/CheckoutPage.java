package com.testautomation.bookstore.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.testautomation.bookstore.abstractComponents.AbstractComponents;

public class CheckoutPage extends AbstractComponents{
	
	/* constructor creation*/
    WebDriver driver;
    Actions a = new Actions(driver);

    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	
    
	/*web element creation*/
    
    //billing first name
    @FindBy(id = "BillingNewAddress_FirstName")
    WebElement billingFName;
    
    //billing last name
    @FindBy(id = "BillingNewAddress_LastName")
    WebElement billingLName;
    
    //billing email
    @FindBy(id = "BillingNewAddress_Email")
    WebElement billingEmail;
    
    //billing country
    @FindBy(id = "BillingNewAddress_Email")
    WebElement billingCountry;
    
    //billing city
    @FindBy(id = "BillingNewAddress_City")
    WebElement billingCity;
    
    //billing address1
    @FindBy(id = "BillingNewAddress_Address1")
    WebElement billingAdd1;
    
    //billing address2
    @FindBy(id = "BillingNewAddress_Address2")
    WebElement billingAdd2;
    
    //postal code
    @FindBy(id = "BillingNewAddress_ZipPostalCode")
    WebElement billingPostalCode;
        
    //phone number
    @FindBy(id = "BillingNewAddress_PhoneNumber")
    WebElement billingPhoneNum;
    
    //continue button in Billing address section
    @FindBy(xpath = "//div[@id='billing-buttons-container']/input")
    WebElement billingContinue;
    
    //shipping address field
    @FindBy(id = "shipping-address-select")
    WebElement shippingAdd;
    
    //continue button in shipping address section
    @FindBy(xpath = "//div[@id='shipping-buttons-container']/input")
    WebElement shippingAddContinue;
    
    //ground shipping method button
    @FindBy(css = "input[value*='Ground']")
    WebElement groundShipRadioBtn;
    
    //next day air shipping method button
    @FindBy(css = "input[value*='Next Day Air']")
    WebElement nextDayAirShipRadioBtn;
    
    //2nd day air shipping method button
    @FindBy(css = "input[value*='2nd Day Air']")
    WebElement secDayAirShipRadioBtn;
    
    //continue button in shipping method section
    @FindBy(xpath = "//div[@id='shipping-method-buttons-container']/input")
    WebElement shippingMethodContinue;
    
    //Cash On Delivery radio button
    @FindBy(css = "input[value*='CashOnDelivery']")
    WebElement codPaymentBtn;
    
    //continue button in payment method section
    @FindBy(xpath = "//div[@id='payment-method-buttons-container']/input")
    WebElement paymentMethodContinue;
    
    //payment information text locator
    @FindBy(xpath = "//tr/td/p")
    WebElement paymentInfo;
    
    //continue button in payment info section
    @FindBy(xpath = "//div[@id='payment-info-buttons-container']/input")
    WebElement paymentInfoContinue;
        
    //continue button in confirm order section
    @FindBy(xpath = "//div[@id='confirm-order-buttons-container']/input")
    WebElement confirmOrderBtn;
    
    //confirmation message element
    @FindBy(xpath = "//strong")
    WebElement confirmationMsg;
    
    
	/*method creation*/
	
    //get billing first name method
    public String getBillingFirstName()
	{
    	String fName = billingFName.getText();
    	return fName;
	}
    
    //get billing last name method
    public String getBillingLastName()
	{
    	String lName = billingLName.getText();
    	return lName;
	}
    
    //get billing email method
    public String getBillingEmailId()
	{
    	String email = billingEmail.getText();
    	return email;
	}
    
	//select billing country method
	public void selectBillingCountry(String country)
	{
		billingCountry.click();
		billingCountry.findElement(By.xpath("//option[@value='"+country+"']")).click();		
	}
	
	//enter billing city method
	public void enterBillingCity(String city)
	{
		a.sendKeys(billingCity,city).build().perform();
	}
	
	//enter billing address1 method
	public void enterBillingAddress1(String address1)
	{
		a.sendKeys(billingAdd1,address1).build().perform();
	}
	
	//enter billing address2 method
	public void enterBillingAddress2(String address2)
	{
		a.sendKeys(billingAdd2,address2).build().perform();
	}
	
	//enter billing postal code method
	public void enterBillingPostalCode(String postalCode)
	{
		a.sendKeys(billingPostalCode,postalCode).build().perform();
	}
	
	//enter billing phone number method
	public void enterBillingPhoneNumber(String phoneNum)
	{
		a.sendKeys(billingPhoneNum,phoneNum).build().perform();
		
	}
	
	//complete billing address method
	public void completeBillingAddress(String country, String city, String address1, String address2, String postalCode, String phoneNum)
	{
		selectBillingCountry(country);
		enterBillingCity(city);
		enterBillingAddress1(address1);
		enterBillingAddress1(address2);
		enterBillingPostalCode(postalCode);
		enterBillingPhoneNumber(phoneNum);
		billingContinue.click();
	}
	
	//get shipping address method
	public String getShippingAddress()
	{
		String shippingAddress = shippingAdd.getText();
		return shippingAddress;
	}
	
	//complete shipping address method
	public void completeShippingAddress()
	{
		shippingAddContinue.click();
	}
	
	//select shipping method
	public void selectShippingMethod(String shippingMethod)
	{
		if(shippingMethod.contains("Ground"))
		{
			groundShipRadioBtn.click();
		}
		else if(shippingMethod.contains("Next Day Air"))
		{
			nextDayAirShipRadioBtn.click();
		}
		else if(shippingMethod.contains("2nd Day Air"))
		{
			secDayAirShipRadioBtn.click();
		}
		shippingMethodContinue.click();
	}
	
	//select payment method
	public void selectPaymentMethod()
	{
		codPaymentBtn.click();
		paymentMethodContinue.click();
	}
	
	//get payment information text method
	public String getPaymentInfoText()
	{
		String paymentInfoText = paymentInfo.getText();
		return paymentInfoText;
	}
	
	//complete payment information method
	public void completePaymentInfo()
	{
		paymentInfoContinue.click();
	}
	
	//confirm order method
	public void confirmOrder()
	{
		confirmOrderBtn.click();
	}
	
	//get confirmation message method
	public String getConfirmationMsg()
	{
		String confirmationMessage = confirmationMsg.getText();
		return confirmationMessage;
	}
}
