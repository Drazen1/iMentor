package pageObjects;

import extensions.ElementsUtils;
import extensions.MouseUtils;
import extensions.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import java.util.ArrayList;
import java.util.List;

public class ScheduleNewAnnouncementPage {

    private WebDriver driver;

    @FindBy(css = ".col .content imentor-multi-select-dropdown .dropdown-toggle-button span:first-of-type")
    private WebElement webElementGroupToggleButton;

    @FindBy(css = "button.helper-button.reset")
    private WebElement webElementShowMyGroups;

    @FindBy(css = ".content .multi-select-dropdown-container")
    private WebElement webElementGroupDropDownContainer;

    @FindBy(css = ".fa.fa-angle-up")
    private WebElement webElementGroupCollapseDropDownIcon;

    @FindBy(css = ".col-md-4 .dropdown-toggle-button")
    private WebElement webElementTypeToggleButton;

    @FindBy(css = ".col-md-4 .selection.inputnoteditable.ng-untouched.ng-pristine.ng-valid")
    private WebElement webElementStartDateToggleButton;

    @FindBy(css = ".col-md-8 .selection.inputnoteditable.ng-untouched.ng-pristine.ng-valid")
    private WebElement webElementEndDateToggleButton;

    @FindBy(css = "li.item")
    private List<WebElement> webElementListOptions;

    @FindBy(css = ".col-md-4 li.item")
    private List<WebElement> webElementListTypes;

    @FindBy(css = ".col-sm-7.col-xs-12 .subheader-text")
    private WebElement webElementFiledToConfirmTitle;

    @FindBy(css = ".fa.fa-lg.checkbox.fa-square-o")
    private List<WebElement> webElementListInformationCheckBoxes;

    @FindBy(css = ".field-name>span")
    private List<WebElement> webElementListInformationFieldsToConfirm;

    @FindBy(id = "title")
    private WebElement webElementAnnouncementTitle;

    //@FindBy(id = "tiny-angular_11495820831558530833968_ifr")
    @FindBy(xpath = "/html[1]")
    private WebElement webElementAnnouncementTextFiled;

    @FindBy(css = ".col-md-4.col-md-offset-4 .multi-select-container button")
    private WebElement webElementPriorityToggleButton;

    @FindBy(css = ".col-md-6.margin-top-25:first-of-type button:first-of-type")
    private WebElement webElementGotItButton;

    @FindBy(css = ".multi-select-dropdown-container.show li.item")
    private List<WebElement> webElementListPriorityOptions;

    //@FindBy(css = ".btn.imentor-btn-primary.green")
    @FindBy(css = ".action-buttons button")
    private WebElement webElementScheduleAnnouncementButton;

    @FindBy(css = ".modal-footer button")
    private WebElement webElementOkConfirmPopUp;

    private List<String> cachedOptionsList = new ArrayList<>();
    private List<String> cachedTypesList = new ArrayList<>();
    private List<String> cachedInformationList = new ArrayList<>();
    private List<String> cachedPriorityList = new ArrayList<>();


    /**
     * Class constructor;
     *
     * @param driver
     */
    public ScheduleNewAnnouncementPage(WebDriver driver) {
        this.driver = driver;
        WaitUtils.waitForPageProcessing(5000);

        WebElement contentElement = driver.findElement(By.cssSelector("div.tasks-and-announcements-page"));
        PageFactory.initElements(new DefaultElementLocatorFactory(contentElement), this);
    }

    public void updateOptionsList() {
        cachedOptionsList = ElementsUtils.getElementsListText(webElementListOptions);
    }

    public void updateTypesList() {
        cachedTypesList = ElementsUtils.getElementsListText(webElementListTypes);
    }

    public void updateInformationList() {
        cachedInformationList = ElementsUtils.getElementsListText(webElementListInformationFieldsToConfirm);
    }

    public void updatePriorityList() {
        cachedPriorityList = ElementsUtils.getElementsListText(webElementListPriorityOptions);
    }

    public boolean selectGroup(String groupName) {
        updateOptionsList();
        int index = cachedOptionsList.indexOf(groupName);
        if (index >= 0) {
            return ElementsUtils.clickElementAndWait(webElementListOptions.get(index), 1500);
        }
        return false;
    }

    public boolean expandGroupDropDown() {
        return ElementsUtils.clickElementAndWait(webElementGroupToggleButton, 3000);
    }

    public boolean collapseGroupDropDown() {
        return ElementsUtils.clickElementAndWait(webElementGroupCollapseDropDownIcon, 1500);
    }

    public boolean showMyGroups() {
        return ElementsUtils.clickElementAndWait(webElementShowMyGroups, 1500);
    }

    public boolean selectStartDate() {
        return ElementsUtils.clickElementAndWait(webElementStartDateToggleButton, 1500);
    }

    public boolean selectEndDate() {
        return ElementsUtils.clickElementAndWait(webElementEndDateToggleButton, 1500);
    }

    public boolean expandTypesDropDown() {
        return ElementsUtils.clickElementAndWait(webElementTypeToggleButton, 3000);
    }

    public boolean selectType(String typeOption) {
        updateTypesList();
        int resultIndex = cachedTypesList.indexOf(typeOption);

        if (resultIndex >= 0) {
            return ElementsUtils.clickElementAndWait(webElementListTypes.get(resultIndex), 3500);
        }
        return false;
    }

    public boolean expandPriority() {
        return ElementsUtils.clickElementAndWait(webElementPriorityToggleButton, 2000);
    }

    public boolean selectPriority(String priorityOption) {
        updatePriorityList();
        int resultIndex = cachedPriorityList.indexOf(priorityOption);
        if (resultIndex >= 0) {
            return ElementsUtils.clickElementAndWait(webElementListPriorityOptions.get(resultIndex), 1500);
        }
        return false;
    }

    public boolean clickGotItButton() {
        driver.switchTo().defaultContent();
        return ElementsUtils.clickElementAndWait(webElementGotItButton, 2000);
    }

    public boolean scheduleAnnouncement() {
          return ElementsUtils.clickElementAndWait(webElementScheduleAnnouncementButton, 4000);
    }

    public boolean selectInformationField(String info) {
        updateInformationList();
        int resultIndex = cachedInformationList.indexOf(info);
        if (resultIndex >= 0) {
            MouseUtils.scrollIntoView(webElementFiledToConfirmTitle);
            return ElementsUtils.clickElementAndWait(webElementListInformationCheckBoxes.get(resultIndex), 1000);
        }
        return false;
    }

    public boolean clickConfirmButton() {
        return ElementsUtils.clickElementAndWait(webElementOkConfirmPopUp, 2000);
    }

    public boolean setAnnouncementTitle(String text) {
        return ElementsUtils.setElementValue(webElementAnnouncementTitle, text);
    }

    public boolean setAnnouncementText(String text) {
       driver.switchTo().frame(driver.findElement(By.cssSelector("#mceu_38>iframe")));
       WebElement body = driver.findElement(By.tagName("body")); // then you find the body
        body.sendKeys(Keys.CONTROL + "a"); // send 'ctrl+a' to select all
        body.sendKeys(text);
       return true;

       // MouseUtils.scrollIntoView(webElementAnnouncementTextFiled);
       // return ElementsUtils.setElementValue(webElementAnnouncementTextFiled, text);
    }

}
