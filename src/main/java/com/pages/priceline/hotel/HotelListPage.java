package com.pages.priceline.hotel;

import com.utility.ElementUtils;
import org.openqa.selenium.By;

public class HotelListPage {

    By hotelist = By.cssSelector("#main-wrapper div[class*=\"Listingshelper__ListingCardWrapper\"]");

    public boolean isHotelListDisplayed() {
        return !(ElementUtils.waitForElementsTobeVisible(hotelist).isEmpty());
    }
    public HotelListPage switchToHotelListPage() {
        ElementUtils.SwitchToNewTab();
        return this;
    }

}

