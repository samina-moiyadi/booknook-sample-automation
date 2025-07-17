package com.testautomation.bookstore.pageObjects;

import java.util.List;
import org.openqa.selenium.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.testautomation.bookstore.abstractComponents.AbstractComponents;

public class HomePage extends AbstractComponents {

	/* constructor creation */
	WebDriver driver;

	public HomePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/* WebElement creation */

	// search bar
	@FindBy(id = "small-searchterms")
	WebElement searchBar;

	// list of auto-suggested searched products
	@FindBy(css = ".ui-menu-item")
	List<WebElement> searchAutoSuggProducts;

	// product title
	@FindBy(xpath = "//h1")
	WebElement productTitle;
	
	//search button
	@FindBy(css = "input[type='submit']")
	WebElement searchBtn;

	//search result not found message WebElement
	@FindBy(css = ".result")
	WebElement searchErrorMessage;
	
	//breadcrumb text
	@FindBy(css = ".current-item")
	WebElement breadcrumbNavText;
	
	/*Locator creation*/
	//searchBar locator
	By searchAutoSuggProductsLocator = By.cssSelector(".ui-autocomplete li");

	/* method creation */

	// select category by name method
//	public void navigateToCategoryOrSubcategory(String categoryName, String subcategoryName) {
//	    List<WebElement> mainCategories = driver.findElements(By.cssSelector(".top-menu li a"));
//	    for (WebElement category : mainCategories) {
//	        if (category.getText().equalsIgnoreCase(categoryName)) {
//	            Actions actions = new Actions(driver);
//	            actions.moveToElement(category).perform();
//
//	            if (subcategoryName != null && !subcategoryName.isBlank()) {
//	                List<WebElement> subCategories = category.findElements(By.xpath("../ul/li/a"));
//	                for (WebElement sub : subCategories) {
//	                    if (sub.getText().equalsIgnoreCase(subcategoryName)) {
//	                        sub.click();
//	                        return;
//	                    }
//	                }
//	            } else {
//	                category.click(); // If no subcategory provided
//	                return;
//	            }
//	        }
//	    }
//	}
	
	public String getCategoryByName(String categoryName) {
		List<WebElement> categories = driver.findElements(By.xpath("//ul[@class='top-menu']/li"));
		WebElement cat = categories.stream()
				.filter(category -> category.findElement(By.cssSelector("a")).getText()
				.trim().equalsIgnoreCase(categoryName))
				.findFirst().orElse(null);
		cat.click();
		waitForElementToAppear(breadcrumbNavText);
		return breadcrumbNavText.getText();
	}

	// search product method
	public String searchProduct(String searchText, String expectedProductName) {
		searchBar.clear();
		searchBar.sendKeys(searchText);
		
		try {
			waitForLocatorToAppear(searchAutoSuggProductsLocator, 5);
	        List<WebElement> suggestions = driver.findElements(searchAutoSuggProductsLocator);
	        
	        for (WebElement suggestion : suggestions) {
	            WebElement link = suggestion.findElement(By.cssSelector("a"));
	            String linkText = link.getText().trim();
	            if (expectedProductName != null && linkText.contains(expectedProductName)) {
	                link.click();
	                waitForElementToAppear(productTitle);
	                return productTitle.getText();
	            }
	        }
	        return null;
		
		}catch (TimeoutException e) {
			searchBtn.click();
			waitForElementToAppear(searchErrorMessage);
			return searchErrorMessage.getText();

		}
	
		 
	}

}
