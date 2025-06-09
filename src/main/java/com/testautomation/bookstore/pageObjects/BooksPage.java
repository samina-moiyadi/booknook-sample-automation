package com.testautomation.bookstore.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.testautomation.bookstore.abstractComponents.AbstractComponents;

public class BooksPage extends AbstractComponents{

	/* constructor creation*/
    WebDriver driver;

    public BooksPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    
	/*WebElement creation*/
    
    //page title
	
	//get a list of books
	@FindBy(css = ".product-title")
	List<WebElement> books;
	
	//add to cart button
	@FindBy(css = ".add-to-cart-button")
	WebElement addToCartBtn;
	
	
    /*method creation*/
	
	//get book list
	public List<WebElement> getBookList()
	{
		return books;
	}
	
	//select book by name
	public void getBookByName(String bookName)
	{
		WebElement selectedBook = books.stream().filter(book->book.findElement(By.cssSelector("a")).getText().equals(bookName)).findFirst().orElse(null);
		selectedBook.click();
	}
	
	//add the selected book to cart
	public void addBookToCart()
	{
		addToCartBtn.click();
	}
	
	
}
