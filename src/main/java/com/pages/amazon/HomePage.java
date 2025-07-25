package com.pages.amazon;

import com.utility.ElementUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class HomePage {
    By SearchBox = By.id("twotabsearchtextbox");
    By SearchButton = By.id("nav-search-submit-button");
    By cartCount = By.cssSelector("#nav-cart-count");

    @Step("Enter Product {0} to search and click on Search button")
    public SearchPage searchProduct(String searchText) {
        ElementUtils.enterText(SearchBox, searchText);
        ElementUtils.click(SearchButton);
        return new SearchPage();
    }

    public int getCartCount() {
        return Integer.parseInt(ElementUtils.getText(cartCount));
    }


}
