package PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BooksPage {

	//booksBtn
	@FindBy(linkText = "Computing and Internet")
	WebElement booksBtn;
}
