package com.testautomation.bookstore.pageObjects;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.testautomation.bookstore.abstractComponents.AbstractComponents;

public class ProductListPage extends AbstractComponents {

	/* constructor creation */
	WebDriver driver;

	public ProductListPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	/* WebElement creation */
	
	// sort by drop down
	@FindBy(id = "products-orderby")
	WebElement sortByDropdn;
	
	//list of product names
	@FindBy(css = ".product-title a")
	List<WebElement> prodNames;
	
	//list of product prices
	@FindBy(css = ".actual-price")
	List<WebElement> prodPrices;
	
	/* method creation */	
	
	// select sort by options
	public String selectSortByDropdn(String sortByOption) {
	    waitForElementToAppear(sortByDropdn);
	    Select select = new Select(sortByDropdn);
	    select.selectByVisibleText(sortByOption);
	    return select.getFirstSelectedOption().getText();
	}
	
	//get list of products based on sort option method
	public List<String> getProductNames() {
		return prodNames
				.stream()
				.map(WebElement::getText)
				.collect(Collectors.toList());
	}
	
	// Get product prices method 
	public List<Double> getProductPrices() {
	    return prodPrices
	            .stream()
	            .map(e -> e.getText().replaceAll("[^\\d.]", "")) 
	            .filter(s -> !s.isEmpty())
	            .map(Double::parseDouble)
	            .collect(Collectors.toList());
	}
}
