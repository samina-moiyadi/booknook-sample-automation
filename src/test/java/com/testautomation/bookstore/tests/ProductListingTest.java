package com.testautomation.bookstore.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.testautomation.bookstore.pageObjects.ProductListPage;
import com.testautomation.bookstore.testComponents.BaseTest;

@Listeners(com.testautomation.bookstore.testComponents.Listeners.class)
public class ProductListingTest extends BaseTest {
	ProductListPage prodListPage;
	
	//test for validating sort by options
	@Test(groups = {"SortProductList","requires-login"})
	public void sortProductListTest() {
		homePage.getCatogoryByName("Books");
		prodListPage = new ProductListPage(driver);
		String selectedSortByOption = prodListPage.selectSortByDropdn("Created on");
		System.out.println(selectedSortByOption);
		
	}
	

}
