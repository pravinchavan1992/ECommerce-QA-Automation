package com.practice.priceline;

import com.pages.priceline.HomePage;
import com.pages.priceline.hotel.HotelListPage;
import com.practice.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BookHotelTest extends BaseTest {

    @Test(description = "Search hotel and book")
    public void SearchAndBookHotel()  {
        HomePage homePage = new HomePage();
        HotelListPage hotelListPage = homePage.clickOnHeader("hotels")
                .selectHotelRadioButton("Single Hotel")
                .enterDestination("Mumbai")
                .openCalenderPopup()
                .selectDate("08/12/2025", "08/20/2025")
                .openTraveler()
                .clickOnSearchButton("Hotel", HotelListPage.class)
                .switchToHotelListPage();
        Assert.assertTrue(hotelListPage.isHotelListDisplayed(), "Hotel list is not displayed");
    }
}
