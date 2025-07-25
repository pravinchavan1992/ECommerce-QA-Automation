package com.pages.priceline;

import com.pages.priceline.flight.FlightListPage;
import com.pages.priceline.hotel.HotelListPage;
import com.utility.ElementUtils;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.IntStream;

public class HomePage {
    By headerLinks = By.cssSelector("[aria-label^='Search for']");

    By radioButton = By.xpath("//div[@id='panel-hotels']//input[@type='radio']/parent::div//parent::label");

    By flightButton = By.xpath("//div[@id='panel-flights']//input[@type='radio']/parent::div//parent::label");

    By inputBox = By.cssSelector("input[data-testid='endLocation-typeahead-input']");

    By inputDropdown = By.cssSelector("[data-testid=typeahead-dropdown-card] div[id^=endLocation-typeahead-downshift-container-item-]>div>div");

    public By header(String header) {
        return By.cssSelector("[aria-label^='Search for "+header+"']");
    }

    By calenderPopup = By.cssSelector("[data-testid=\"calendar-root\"]");

    By calenderTextBox = By.cssSelector("[data-testid='hotel-date-range']");

    By dateSelection = By.cssSelector("[data-testid='scroll-container']>div+div:nth-child(2)>div:last-of-type>div[role='button']");

    By forward = By.cssSelector("[data-testid='scroll-wrapper']+button+button");

    By backword = By.cssSelector("[data-testid='scroll-wrapper']+button");

    By monthlist = By.cssSelector("[data-testid='scroll-container'] div[class*='MonthName']");

    public By getDateSelector(String date) {
        return By.cssSelector("[aria-describedby$='" + date + "-calendar-day'] > div");
    }

    By doneButton = By.cssSelector("button[aria-label='Dismiss calendar']>div");

    By traveler = By.cssSelector("[data-testid='DASH_TAB_HOTELS_NUMROOMS']");

    By donebutton = By.cssSelector("#popover-description-traveler-selection-popover div+div>div>div>button");

    By findYourHotelButton = By.cssSelector("[data-testid=HOTELS_SUBMIT_BUTTON]>div");

    By getSearchButton(String search) {
        return By.xpath("//div[text()='Find Your "+search+"']");
    }

    //Flights
    public By getInputBox(String travel) {
        String travelType = travel.trim().equalsIgnoreCase("Departure") ? "start" : "end";
        return By.cssSelector("[data-testid=typeahead-dropdown-card] div[id*="+travelType+"Location-typeahead-downshift-container]>div");
    }

    public By getTextBox(String travel) {
        String travelType = travel.trim().equalsIgnoreCase("Departure") ? "start" : "end";
        return By.cssSelector("input[data-testid='flights.0."+travelType+"Location-typeahead-input']");
    }

    public By getRadio(String travelType){
        return By.xpath("//div[@id='panel-"+travelType+"']//input[@type='radio']/parent::div//parent::label");

    }

    By travelerTextBox = By.cssSelector("button[aria-label=\"Select traveler information\"]");

    By addChildren = By.cssSelector("button#children-number-radioPlus-button");

    By addAdult = By.cssSelector("button#adults-number-radioPlus-button");

    By infant = By.cssSelector("button#infants-on-lap-number-radioPlus-button");

    By done= By.xpath("//div[text()='Done']");

    By cabinDropDown = By.cssSelector("select#cabin-class-select");

    private static final Map<String, String> HEADER_MAPPING = Map.of(
            "vacations", "Packages",
            "hotels", "Hotels",
            "flights", "Flights"
    );

    public WebElement getHeader(String header) {
        return ElementUtils.waitForElementsTobeVisible(header(header)).stream().filter(x->x.getText().equals(HEADER_MAPPING.get(header))).findFirst().orElseThrow(
                ()-> {
                    Allure.step("No header found for " + header, Status.FAILED);
                    return new RuntimeException("No header found for " + header);
                }
        );
    }

    public WebElement getRadioButton(By by, String radioButtonName ) {
        return ElementUtils.waitForElementsTobeVisible(by).stream().filter(x->x.getText().equals(radioButtonName)).findFirst().orElseThrow(
                ()-> {
                    Allure.step("No radio found for " + radioButtonName, Status.FAILED);
                    return new RuntimeException("No radio found for " + radioButtonName);
                }
        );
    }

    public HomePage enterDestination(String destination) {
          ElementUtils.waitForElementTobeVisible(inputBox).sendKeys(destination);
          WebElement ele = ElementUtils.waitForElementsTobeVisible(inputDropdown).stream().filter(x -> x.getText().equals(destination)).findFirst().orElseThrow(
                  ()-> {
                      Allure.step("Destination not found " + destination, Status.FAILED);
                      return new RuntimeException("Destination not found " + destination);
                  }
          );
          ElementUtils.click(ele);
          return this;
    }

    public HomePage clickOnHeader(String header) {
        getHeader(header).click();
        return this;
    }

    public HomePage selectRadioButton(String group, String radioButtonName) {
        WebElement radioButton = getRadioButton(getRadio(group), radioButtonName);
        if (!radioButton.isSelected()) {
            radioButton.click();
        }
        return this;
    }

    public HomePage selectHotelRadioButton(String radioButtonName) {
        return selectRadioButton("hotel", radioButtonName);
    }

    public HomePage selectFlightRadioButton(String radioButtonName) {
        return selectRadioButton("flights", radioButtonName);
    }

    public boolean isCalenderPopupOpen() {
        return ElementUtils.waitForElementsToPresence(calenderPopup).isDisplayed();
    }

    public HomePage openCalenderPopup() {
        if (!isCalenderPopupOpen()){
            WebElement calenderBox = ElementUtils.waitForElementTobeVisible(calenderTextBox);
            ElementUtils.click(calenderBox);
        }
        return this;
    }

    public HomePage closeCalenderPopup() {
        if (isCalenderPopupOpen()){
            ElementUtils.click(doneButton);
        }
        return this;
    }

    public int getIndex(String targetText){;
        List<WebElement> monthElements = ElementUtils.waitForElementsTobeVisible(monthlist);
        int index = IntStream.range(0, monthElements.size())
                .filter(i -> monthElements.get(i).getText().trim().equalsIgnoreCase(targetText))
                .findFirst()
                .orElse(-1);
        return index == -1 ? -1 : index/2;
    }

    public void getMonthInFocus(String targetText){
        int index = getIndex(targetText);
        for(int i = 0; i < index; i++){
            ElementUtils.waitForElementTobeVisible((forward)).click();
        }
    }

    public Map<String, String> getDate(String inputDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(inputDate, formatter);

        String day = String.format("%02d", date.getDayOfMonth());
        String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        String monthNumber = String.format("%02d", date.getMonthValue());
        String year = String.valueOf(date.getYear());

        return Map.of("Day", day, "Month", month, "Year", year, "MonthNumber", monthNumber);
    }

    public HomePage selectDate(String checkinDate, String checkOutDate){
        Map<String, String> data = getDate(checkOutDate);
        String targetDate = data.get("Month")+" "+data.get("Year");
        String CheckOutDate = data.get("Year")+data.get("MonthNumber")+data.get("Day");

        Map<String, String> date = getDate(checkinDate);
        String CheckInDate = date.get("Year")+date.get("MonthNumber")+date.get("Day");

        getMonthInFocus(targetDate);
        selectDate(CheckInDate);
        selectDate(CheckOutDate);
        //closeCalenderPopup();
        return this;
    }

    public HomePage selectTravelDate(String checkOutDate){
        Map<String, String> data = getDate(checkOutDate);
        String targetDate = data.get("Month")+" "+data.get("Year");
        String CheckOutDate = data.get("Year")+data.get("MonthNumber")+data.get("Day");
        getMonthInFocus(targetDate);
        selectDate(CheckOutDate);
        //closeCalenderPopup();
        return this;
    }

    public void selectDate(String date) {
        ElementUtils.waitForElementTobeVisible(getDateSelector(date)).click();
    }

    public HomePage openTraveler(){
        ElementUtils.waitForElementTobeVisible(traveler).click();
        ElementUtils.waitForElementTobeVisible(donebutton).click();
        return this;
    }

    public HotelListPage findHotel(){
        ElementUtils.waitForElementTobeVisible(findYourHotelButton).click();
        return new HotelListPage();
    }

    public FlightListPage clickOnSearchButton(String itemName){
        ElementUtils.waitForElementTobeVisible(getSearchButton(itemName)).click();
        return new FlightListPage();
    }

    public <T> T clickOnSearchButton(String itemName, Class<T> pageClass){
        ElementUtils.waitForElementTobeVisible(getSearchButton(itemName)).click();
        try {
            return pageClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate page class: " + pageClass.getSimpleName(), e);
        }
    }

    public void naviateToHotelListPage(){}

    public HomePage enterTravelLocation(String departure, String arrival) {
        enterLocation("Departure", departure, "Destination");
        enterLocation("Arrival", arrival, "Arrival");
        return this;
    }

    private void enterLocation(String type, String location, String labelForFailure) {
        ElementUtils.waitForElementTobeVisible(getTextBox(type)).sendKeys(location);

        WebElement locationElement = ElementUtils.waitForElementsTobeVisible(getInputBox(type)).stream()
                .filter(el -> el.getText().equals(location))
                .findFirst()
                .orElseThrow(() -> {
                    Allure.step(labelForFailure + " not found: " + location, Status.FAILED);
                    return new RuntimeException(labelForFailure + " not found: " + location);
                });

        ElementUtils.click(locationElement);
    }

    public HomePage addTravelerData(int adult, int child, int infantNumber) {
        ElementUtils.click(travelerTextBox);
        clickMultipleTimes(addAdult, adult);
        clickMultipleTimes(addChildren, child);
        clickMultipleTimes(infant, infantNumber);
        ElementUtils.click(done);
        return this;
    }

    private void clickMultipleTimes(By locator, int count) {
        if (count <= 0) return;
        WebElement element = ElementUtils.waitForElementTobeVisible(locator);
        for (int i = 0; i < count; i++) {
            ElementUtils.click(element);
        }
    }

    public HomePage selectCabin(String cabin) {
        ElementUtils.selectFromDropDown(cabinDropDown, cabin);
        return this;
    }
}
