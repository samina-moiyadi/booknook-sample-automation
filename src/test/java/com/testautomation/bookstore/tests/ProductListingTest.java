package com.testautomation.bookstore.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.testautomation.bookstore.pageObjects.ProductListPage;
import com.testautomation.bookstore.testComponents.BaseTest;

@Listeners(com.testautomation.bookstore.testComponents.Listeners.class)
public class ProductListingTest extends BaseTest {
	ProductListPage prodListPage;
	
	//test for validating sort by options
	@Test(dataProvider = "sortOptionsData", groups = {"SortProductList","requires-login"})
	public void sortProductListTest(Map<String, String> input) {
		
	    String category = input.get("categoryName");
	    String subcategory = input.get("subcategoryName");
	    String sortOption = input.get("sortOption");
	    
	    prodListPage.selectSortByDropdn(category);
	    List<String> positionNames = prodListPage.getProductNames();
	    List<Double> positionPrices = prodListPage.getProductPrices();

	    // Select "Name: A to Z"
	    prodListPage.selectSortByDropdn("Name: A to Z");
	    List<String> nameAsc = prodListPage.getProductNames();
	    List<String> expectedNameAsc = new ArrayList<>(nameAsc);
	    Collections.sort(expectedNameAsc);
	    Assert.assertEquals(nameAsc, expectedNameAsc, "Name A-Z is not sorted alphabetically.");

	    // Select "Price: Low to High"
	    prodListPage.selectSortByDropdn("Price: Low to High");
	    List<Double> priceAsc = prodListPage.getProductPrices();
	    List<Double> expectedPriceAsc = new ArrayList<>(priceAsc);
	    Collections.sort(expectedPriceAsc);
	    Assert.assertEquals(priceAsc, expectedPriceAsc, "Price Low to High is not sorted correctly.");

	    // Sanity check that "Position" doesn't match sorted options
	    //Assert.assertNotEquals(positionNames, nameAsc, "'Position' and 'Name A-Z' result in same order.");
	    Assert.assertNotEquals(positionPrices, priceAsc, "'Position' and 'Price Low to High' result in same order.");
		
	}
	

}
