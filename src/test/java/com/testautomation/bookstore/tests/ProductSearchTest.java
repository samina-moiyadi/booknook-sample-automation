package com.testautomation.bookstore.tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.testautomation.bookstore.pageObjects.HomePage;
import com.testautomation.bookstore.pageObjects.LoginPage;
import com.testautomation.bookstore.testComponents.BaseTest;

@Listeners(com.testautomation.bookstore.testComponents.Listeners.class)
public class ProductSearchTest extends BaseTest{
	LoginPage loginPage;
	HomePage homePage;
	
	//test for product search with valid data
	//@Test(dataProvider = "getValidSearchData", groups = {"SearchProduct"})
	public void selectProductByValidSearchTest(Map<String, String> input) {
		loginPage = new LoginPage(driver);
		homePage = loginPage.login("ABone@email.com", "A1Bone");
		String text = homePage.searchWithPartialText(
				input.get("searchText"),
				input.get("productName")
			);
		Assert.assertEquals(text, input.get("productName"));
	}
	
	//data provider method for product search with valid data
	@DataProvider
	public Object[][] getValidSearchData() throws IOException {
		List<Map<String, Object>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//com//testautomation//bookstore//data//productData//ValidSearchData.json");
		Object[][] testData = new Object[data.size()][1];
	    for (int i = 0; i < data.size(); i++) {
	        testData[i][0] = data.get(i);
	    }
	    return testData;
	}
	
	//test for product search with non-existant data
	@Test(dataProvider = "getNonExistantSearchData", groups = {"SearchProduct"})
	public void selectProductByNonExistantSearchTest(Map<String, String> input) {
		loginPage = new LoginPage(driver);
		homePage = loginPage.login("ABone@email.com", "A1Bone");
		String error = homePage.searchWithNonExistantProduct(
				input.get("searchText")
			);
		Assert.assertEquals(error, "No products were found that matched your criteria.");
	}
	
	//data provider method for product search with valid data
	@DataProvider
	public Object[][] getNonExistantSearchData() throws IOException {
		List<Map<String, Object>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//com//testautomation//bookstore//data//productData//NonExistentSearchData.json");
		Object[][] testData = new Object[data.size()][1];
	    for (int i = 0; i < data.size(); i++) {
	        testData[i][0] = data.get(i);
	    }
	    return testData;
	}
}
