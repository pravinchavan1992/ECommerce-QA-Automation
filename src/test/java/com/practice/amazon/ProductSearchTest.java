package com.practice.amazon;

import com.pages.amazon.HomePage;
import com.pages.amazon.ProductDetailsPage;
import com.pages.amazon.SearchPage;
import com.pages.amazon.ShoppingCartPage;
import com.practice.BaseTest;
import io.qameta.allure.Allure;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Map;

public class ProductSearchTest extends BaseTest {

//    @DataProvider(name = "productName")
//   public Object [][] TestDataProvider(){
//       return new Object[][]{{"Laptop"}, {"Wireless Mouse"}};
//   }

    @DataProvider(name = "productName")
    public Object[][] TestDataProvider() {
        return new Object[][]{
                {Map.of("name", "Laptop", "price", "50000")},
                {Map.of("name", "Wireless Mouse", "price", "50000")}
        };
    }

    @Test(description = "Validate Search Result Titles", dataProvider = "productName")
    public void SearchProductAndValidateTitle(Map<String, String> productMap) {
        String productName = productMap.get("name");

        Allure.step("Open Home Page");
        HomePage homePage = new HomePage();

        Allure.step("Search for product: " + productName);
        SearchPage searchPage = homePage.searchProduct(productName);

        Allure.step("Verify info bar shows searched product: " + productName);
        Assert.assertTrue(searchPage.verifyResultInfoBar(productName), "Info bar does not show correct product");

        Allure.step("Verify result list contains the product: " + productName);
        Assert.assertTrue(searchPage.verifyResultList(productName), "Product not found in search result list");
    }

    @Test(description = "Add Product to Cart and Validate Prices", dataProvider = "productName")
    public void SearchProductAddToCartAndValidatePrice(Map<String, String> productMap) {
        String productName = productMap.get("name");
        String expectedPrice = productMap.get("price");
        String successMessage = "Added to cart";

        Allure.step("Open Home Page");
        HomePage homePage = new HomePage();

        Allure.step("Search for product: " + productName);
        SearchPage searchPage = homePage.searchProduct(productName);

        Allure.step("Navigate to product details and capture product info");
        Map<String, String> product = searchPage.navigateToProductDetailsAndGetProductDetails(productName);

        ProductDetailsPage productDetailsPage = new ProductDetailsPage();

        Allure.step("Validate product title and price on product details page");
        Assert.assertTrue(
                productDetailsPage.validateProductTitleAndPrice(product.get("name"), product.get("price")),
                "Product title or price mismatch on product details page."
        );

        Allure.step("Click on Add to Cart");
        productDetailsPage.clickOnAddToCart();

        Allure.step("Validate success message after adding to cart");
        Assert.assertEquals(
                productDetailsPage.getSuccessMessage(), successMessage,
                "Add to cart success message is incorrect."
        );

        Allure.step("Validate cart subtotal matches expected price");
        Assert.assertEquals(
                productDetailsPage.getCartTotal(), product.get("price"),
                "Cart subtotal price mismatch after adding product to cart."
        );

        Allure.step("Navigate to cart and verify product presence");
        ShoppingCartPage shoppingCartPage = productDetailsPage.getGoToCartButton();
        Assert.assertTrue(
                shoppingCartPage.isProductTitlePresent(product.get("name")),
                "Product title is not present in the shopping cart."
        );
    }
}