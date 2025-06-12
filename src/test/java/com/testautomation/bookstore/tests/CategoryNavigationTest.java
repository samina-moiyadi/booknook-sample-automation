package com.testautomation.bookstore.tests;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.testautomation.bookstore.testComponents.BaseTest;

@Listeners(com.testautomation.bookstore.testComponents.Listeners.class)
public class CategoryNavigationTest extends BaseTest{
	SoftAssert a = new SoftAssert();
	
	//test for checking successful navigation to the various categories
	@Test(dataProvider = "getCategory", groups = {"CategoryNavigation","requires-login"}, retryAnalyzer = (com.testautomation.bookstore.testComponents.Retry.class))
	public void categoryPageNavigation(Map<String, String> input) {
		String category = input.get("category");
		String navText = homePage.getCatogoryByName(category);
		a.assertTrue(driver.getTitle().contains(category));
		//String text = driver.getCurrentUrl().split("/")[3];
		//a.assertTrue(text.equalsIgnoreCase(category));
		a.assertTrue(navText.equalsIgnoreCase(category));
		a.assertAll();
	}

	//data provider method for category selection
	@DataProvider
	public Object[][] getCategory() throws IOException {
		List<Map<String, Object>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//com//testautomation//bookstore//data//productData//CategoryData.json");
		Object[][] testData = new Object[data.size()][1];
	    for (int i = 0; i < data.size(); i++) {
	        testData[i][0] = data.get(i);
	    }
	    return testData;
	}
}
