package com.testautomation.bookstore.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.testautomation.bookstore.abstractComponents.AbstractComponents;

public class ShoppingCartPage extends AbstractComponents{

	/* constructor creation*/
    WebDriver driver;

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    
	/*WebElement creation*/
	
	//get a list of added products
	@FindBy(css = ".product-name")
	List<WebElement> addedProducts;
	
	//select country dropdown
	@FindBy(xpath = "//select[@class='country-input valid']")
	WebElement countryDropdown;
	
	//terms of service checkbox
	@FindBy(id = "termsofservice")
	WebElement termsOfService;
	
	//Checkout button
	@FindBy(id = "checkout")
	WebElement checkoutBtn;
	
	/*method creation*/
	
	//get added products list method
	public List<WebElement> getAddedProductList()
	{
		return addedProducts;
	}
	
	//select product by name method
	public WebElement getAddedProductByName(String productName)
	{
		WebElement addedProduct = addedProducts.stream().filter(product -> product.getText().equalsIgnoreCase(productName)).findFirst().orElse(null);
		return addedProduct;
	}
	
	//select country method
	public void selectCountry(String country)
	{
		countryDropdown.click();
		countryDropdown.findElement(By.xpath("//option[@value='"+country+"']")).click();		
	}
	
	//click terms of service check box method
	public void termsOfServiceChkbox()
	{
		termsOfService.click();
	}
	
	//click on checkout button method
	public void checkout()
	{
		checkoutBtn.click();
	}
}
