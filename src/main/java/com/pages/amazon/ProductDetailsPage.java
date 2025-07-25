package com.pages.amazon;

import com.utility.ElementUtils;
import org.openqa.selenium.By;

public class ProductDetailsPage {

    By productTitle = By.cssSelector("#productTitle");
    By productPrice = By.cssSelector("#corePriceDisplay_desktop_feature_div span.a-price-whole");
    By addToCartButton = By.cssSelector("div.a-section.a-spacing-none.a-padding-none input#add-to-cart-button");
    By successMessage = By.cssSelector("#NATC_SMART_WAGON_CONF_MSG_SUCCESS h1");
    By subTotal = By.cssSelector("#sw-subtotal span.a-price-whole");
    By goToCartButton = By.cssSelector("#sw-subtotal+form+span>span>a");

    public boolean validateProductTitleAndPrice(String productDesc, String price) {
        System.out.println(ElementUtils.getText(productTitle));
        boolean isTitle = ElementUtils.getText(productTitle).equals(productDesc);
        System.out.println(ElementUtils.getText(productPrice));
        boolean isPrice = ElementUtils.getText(productPrice).equals(price);
        return isTitle && isPrice;
    }

    public void clickOnAddToCart() {
        ElementUtils.click(addToCartButton);
    }
    public String getSuccessMessage() {
        return ElementUtils.getText(successMessage);
    }

    public String getCartTotal() {
        return ElementUtils.getText(subTotal);
    }
    public ShoppingCartPage getGoToCartButton() {
        ElementUtils.click(goToCartButton);
        return new ShoppingCartPage();
    }
}
