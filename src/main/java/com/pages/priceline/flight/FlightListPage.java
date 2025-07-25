package com.pages.priceline.flight;

import com.utility.ElementUtils;
import org.openqa.selenium.By;

public class FlightListPage {

    By flightList = By.cssSelector("ul.fly-search-listings li");
    public boolean isFlightListDisplayed() {
        return !(ElementUtils.waitForElementsTobeVisible(flightList).isEmpty());
    }
    public FlightListPage switchToFlightListPage() {
        ElementUtils.SwitchToNewTab();
        return this;
    }
}
