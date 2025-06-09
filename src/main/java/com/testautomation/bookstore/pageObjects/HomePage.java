package com.testautomation.bookstore.pageObjects;

import java.util.List;

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

	// list of categories
	@FindBy(xpath = "//ul[@class='top-menu']/li")
	List<WebElement> categories;

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

	/* method creation */

	// select category by name method
	public String getCatogoryByName(String categoryName) {
		waitForElementsToAppear(categories);
		WebElement cat = categories.stream()
				.filter(category -> category.findElement(By.cssSelector("a")).getText()
				.trim().equalsIgnoreCase(categoryName))
				.findFirst().orElse(null);
		cat.click();
		return breadcrumbNavText.getText();
	}

	// search by partial text method
	public String searchWithPartialText(String searchText, String productName) {
		searchBar.sendKeys(searchText);
		waitForElementsToAppear(searchAutoSuggProducts);
		WebElement product = searchAutoSuggProducts.stream()
				.filter(pro->pro.findElement(By.cssSelector("a")).getText()
				.trim().contains(productName))
				.findFirst().orElse(null);
		product.click();
		waitForElementToAppear(productTitle);
		return productTitle.getText();		
	}
	
	public String searchWithNonExistantProduct(String searchText) {
		searchBar.sendKeys(searchText);
		searchBtn.click();
		waitForElementToAppear(searchErrorMessage);
		return searchErrorMessage.getText();
	}

}
