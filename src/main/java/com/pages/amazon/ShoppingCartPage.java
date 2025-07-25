package com.pages.amazon;

import com.factory.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ShoppingCartPage {

    By productTitle = By.cssSelector("div[role=listitem] li.sc-item-product-title-cont span.a-truncate-full");

    public boolean isProductTitlePresent(String name) {
        List<WebElement> data =  DriverFactory.getDriver().findElements(productTitle).stream().filter(x -> x.getText().equals(name)).toList();
        return !data.isEmpty();
    }
}
