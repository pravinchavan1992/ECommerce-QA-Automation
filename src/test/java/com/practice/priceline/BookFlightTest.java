package com.practice.priceline;

import com.pages.priceline.HomePage;
import com.pages.priceline.flight.FlightListPage;
import com.practice.BaseTest;
import org.testng.annotations.Test;

public class BookFlightTest extends BaseTest {

    @Test(enabled = false)
    public void testBookFlight()    {
        HomePage homePage = new HomePage();
         homePage.clickOnHeader("flights")
                .selectFlightRadioButton("One-way")
                .enterTravelLocation("Mumbai, India - Chhatrapati Shivaji Maharaj Intl Airport (BOM)", "New Delhi, India - Indira Gandhi Intl Airport (DEL)")
                 .openCalenderPopup()
                 .selectTravelDate( "08/20/2025")
                 .addTravelerData(1,2,1)
                 .selectCabin("Business")
                 .clickOnSearchButton("Flight", FlightListPage.class)
                 .switchToFlightListPage();

    }
}
