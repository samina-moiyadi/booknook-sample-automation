package com.testautomation.bookstore.tests;

import java.io.IOException;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.testautomation.bookstore.pageObjects.HomePage;
import com.testautomation.bookstore.testComponents.BaseTest;

@Listeners(com.testautomation.bookstore.testComponents.Listeners.class)
public class ProductSearchTest extends BaseTest{
	
	@BeforeMethod
	public void setupHomePage() {
		homePage = new HomePage(driver);  // directly initialize it
	}
	
	@Test(description = "Search product with valid data",
			dataProvider = "getValidSearchData", groups = {"SearchProduct"})
	public void selectProductByValidSearchTest(Map<String, String> input) {
		String result = homePage.searchProduct(
				input.get("searchText"),
				input.get("productName")
			);
	    Assert.assertEquals(result, input.get("productName"));
	}
	
	//data provider method for product search with valid data
	@DataProvider
	public Object[][] getValidSearchData() throws IOException {
		String filePath = System.getProperty("user.dir") + "//src//test//java//com//testautomation//bookstore//data//productData//ValidSearchData.json";
	    return getTestDataFromJson(filePath);
	}
	
	@Test(description = "Search product with data not matching expected product",
			dataProvider = "getMismatchedSearchData", groups = {"SearchProduct"})
	public void searchWithInvalidExpectedProductTest(Map<String, String> input) {
		String result = homePage.searchProduct(
				input.get("searchText"),
				input.get("productName")
			);
		Assert.assertNull(result, "Expected product not found");
	}
	
	//data provider method for product search with mismatched expected product
	@DataProvider
	public Object[][] getMismatchedSearchData() throws IOException {
		String filePath = System.getProperty("user.dir") + "//src//test//java//com//testautomation//bookstore//data//productData//MismatchedSearchData.json";
	    return getTestDataFromJson(filePath);
	}
	
	@Test(description = "Search product with non-existent data",
			dataProvider = "getNonExistentSearchData", groups = {"SearchProduct"})
	public void selectProductByNonExistentSearchTest(Map<String, String> input) {
		String error = homePage.searchProduct(
				input.get("searchText"),
				input.get("productName")
			);
		Assert.assertEquals(error, "No products were found that matched your criteria.");
	}
	
	//data provider method for product search with valid data
	@DataProvider
	public Object[][] getNonExistentSearchData() throws IOException {
		String filePath = System.getProperty("user.dir") + "//src//test//java//com//testautomation//bookstore//data//productData//NonExistentSearchData.json";
	    return getTestDataFromJson(filePath);
	}
	
	@Test(description = "Search product with empty search text",
			dataProvider = "getEmptySearchData", groups = {"SearchProduct"})
	public void selectProductByEmptySearchTest(Map<String, String> input) {
		String error = homePage.searchProduct(
				input.get("searchText"),
				input.get("productName")
			);
		Assert.assertEquals(error, "Please enter some search keyword");
	}
	
	//data provider method for product search with valid data
	@DataProvider
	public Object[][] getEmptySearchData() throws IOException {
		String filePath = System.getProperty("user.dir") + "//src//test//java//com//testautomation//bookstore//data//productData//EmptySearchData.json";
	    return getTestDataFromJson(filePath);
	}
	
	@Test(description = "Search product with small search text (less than 3)",
			dataProvider = "getLessSearchData", groups = {"SearchProduct"})
	public void selectProductByLessSearchTest(Map<String, String> input) {
		String error = homePage.searchProduct(
				input.get("searchText"),
				input.get("productName")
			);
		Assert.assertEquals(error, "Search term minimum length is 3 characters");
	}
	
	//data provider method for product search with valid data
	@DataProvider
	public Object[][] getLessSearchData() throws IOException {
		String filePath = System.getProperty("user.dir") + "//src//test//java//com//testautomation//bookstore//data//productData//LessSearchData.json";
	    return getTestDataFromJson(filePath);
	}
}
