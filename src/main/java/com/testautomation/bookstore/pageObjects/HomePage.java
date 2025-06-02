package com.testautomation.bookstore.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.testautomation.bookstore.abstractComponents.AbstractComponents;

public class HomePage extends AbstractComponents{
	
	/* constructor creation*/
    WebDriver driver;

    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	
	/*WebElement creation*/
	
	//list of categories
	@FindBy(xpath="//ul[@class='top-menu']/li")
	List<WebElement> categories;
	
	
    /*method creation*/
	
	//get category list method
	public List<WebElement> getCategoryList()
	{
		return categories;
	}
	
	//select category by name method
	public void getCatogoryByName(String categoryName)
	{
		WebElement cat = categories.stream().filter(category->category.findElement(By.cssSelector("a")).getText().equals(categoryName)).findFirst().orElse(null);
		cat.click();
	}	

}
