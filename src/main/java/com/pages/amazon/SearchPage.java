package com.pages.amazon;


import com.utility.ElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchPage {
    By resultInfo = By.cssSelector("div>h2 span:last-of-type");
    By resultList = By.cssSelector("div.s-result-list.s-search-results>[role=listitem]");
    By productTitle = By.cssSelector("div[role='listitem'] h2 span");
    By productPrice = By.cssSelector("[data-cy=\"price-recipe\"] span.a-price-whole");
    By productName = By.cssSelector("[data-cy=\"title-recipe\"] h2>span");

    public boolean verifyResultInfoBar(String productTitle) {
        return (ElementUtils.waitForElementTobeVisible(resultInfo).getText().replaceAll("\"", "")).equals(productTitle);
    }
    public boolean verifyResultList(String productTitle) {
        List<WebElement> result = ElementUtils.waitForElementsTobeVisible(resultList);
        return result.stream().anyMatch(webElement -> webElement.getText().contains(productTitle));
    }

    public Map<String, String> navigateToProductDetailsAndGetProductDetails (String productTitle) {
        List<WebElement> results = ElementUtils.waitForElementsTobeVisible(resultList);
        WebElement product = results.stream()
                .filter(el -> el.getText().contains(productTitle))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("❌ Product with title '" + productTitle + "' not found in search results"));
        WebElement productDescription = product.findElement(productName);
        WebElement productprice = product.findElement(productPrice);
        String name = productDescription.getText().trim();
        String price = productprice.getText().trim();

        System.out.println("✅ Product Name: " + name);
        System.out.println("✅ Product Price: " + price);

        Map<String, String> productDetails = new HashMap<>();
        productDetails.put("name", name);
        productDetails.put("price", price);
        productDescription.click();
        return productDetails;
    }
}
