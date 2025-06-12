package com.testautomation.bookstore.pageObjects;

import java.util.List;

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
	
	// get a list of products
	@FindBy(css = ".product-title")
	List<WebElement> prodList;
	
	
	/* method creation */

	// select sort by options
	public String selectSortByDropdn(String sortByOption) {
	    waitForElementToAppear(sortByDropdn);
	    Select select = new Select(sortByDropdn);
	    select.selectByVisibleText(sortByOption);
	    return select.getFirstSelectedOption().getText();
	}
}
