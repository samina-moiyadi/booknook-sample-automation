package PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import AbstractComponents.AbstractComponents;

public class HomePage extends AbstractComponents{
	
	/*WebElement creation*/
	
	//get a list of categories
	@FindBy(xpath="//div[@class='block block-category-navigation']/div[2]/ul/li")
	List<WebElement> categories;
	
	
	/*By creation*/
	
	//category
	By catogoryBy = By.xpath("//div[@class='block block-category-navigation']/div[2]/ul/li");
	
	
    /*method creation*/
	
	//get category list
	public List<WebElement> getCategoryList()
	{
		waitForElementToAppear(catogoryBy);
		return categories;
	}
	
	//select category by name
	public WebElement getProductByName(String productName)
	{
		WebElement prod = categories.stream().filter(category->category.findElement(By.cssSelector("a")).getText().equals(productName)).findFirst().orElse(null);
		return prod;
	}
	

}
