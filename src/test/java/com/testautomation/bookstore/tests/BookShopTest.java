package com.testautomation.bookstore.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.testautomation.bookstore.pageObjects.HomePage;
import com.testautomation.bookstore.pageObjects.LoginPage;
import com.testautomation.bookstore.testComponents.BaseTest;

@Listeners(com.testautomation.bookstore.testComponents.Listeners.class)
public class BookShopTest extends BaseTest{
	LoginPage loginPage;
	HomePage homePage;
	String category = "Books";
	SoftAssert a = new SoftAssert();
	
	@Test(groups = {"BookShopping"})
	public void bookPageNavigation() {
		loginPage = new LoginPage(driver);
		homePage = loginPage.login("ABone@email.com", "A1Bone");
		String navText = homePage.getCatogoryByName(category);
		a.assertTrue(driver.getTitle().contains(category));
		String text = driver.getCurrentUrl().split("/")[3];
		a.assertTrue(text.equalsIgnoreCase(category));
		a.assertTrue(navText.equalsIgnoreCase(category));
		a.assertAll();
		
	
	}

}
