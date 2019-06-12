package pageObjects;

import extensions.ElementsUtils;
import extensions.FrameworkUtils;
import extensions.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import java.util.ArrayList;
import java.util.List;

public class Groups {

    private WebDriver driver;

    @FindBy(css = "h1.header-text")
    private WebElement webElementPageTitle;

    @FindBy(css = ".content>button")
    private WebElement webElementAddGroup;

    @FindBy(css = ".col-12>button:first-of-type")
    private WebElement webElementMyGroupsButton;

    @FindBy(css = ".col-12>button:nth-child(2)")
    private WebElement webElementAllGroupsButton;

    @FindBy(css = "input.form-control")
    private WebElement webElementGroupNameTextField;

    @FindBy(css = "div.filters-wrapper:nth-child(2) imentor-filters:nth-child(1) > div.page-content-section")
    private WebElement webElementFirstFilterToggleButton;

    @FindBy(css = "imentor-filter-options:nth-child(1) imentor-multi-select-dropdown:nth-child(1) div.multi-select-container > button.dropdown-toggle-button")
    private WebElement webElementSecondFilterToggleButton;

    @FindBy(css = ".multi-select-dropdown-container.show li.item")
    private List<WebElement> webElementListFirstFilterDropDownOptions;

    //@FindBy(css = "div.multi-select-container div.multi-select-dropdown-container.show div.multi-select-list-container ul.multi-select-list > li.item")
    @FindBy(css = ".multi-select-dropdown-container.show li.item")
    private List<WebElement> webElementListSecondFilterDropDownOptions;

    @FindBy(css = ".fa.fa-angle-up")
    private WebElement webElementCollapseDropDown;

    @FindBy(css = ".page-content-section>button")
    private List<WebElement> webElementListAddSaveCancelButtons;

    private List<String> cachedFilterOptionsList = new ArrayList<>();
    private List<String> cachedButtonsList = new ArrayList<>();

    /**
     * Class constructor;
     *
     * @param driver
     */
    public Groups(WebDriver driver) {
        WaitUtils.waitForPageProcessing(2000);
        this.driver = driver;
        refresh();
    }

    public void refresh() {
        WebElement contentElement = FrameworkUtils.getContentElement(driver, By.cssSelector("div.announcement-groups-page"));
        PageFactory.initElements(new DefaultElementLocatorFactory(contentElement), this);
    }

    public boolean clickElement(String element) {
        switch (element.toLowerCase()) {
            case "addgroup":
                boolean actionStatus =  ElementsUtils.clickElementAndWait(webElementAddGroup, 4000);
                this.refresh();
                return actionStatus;
            case "mygroups":
                return ElementsUtils.clickElementAndWait(webElementMyGroupsButton, 2000);
            case "allgroups":
                return ElementsUtils.clickElementAndWait(webElementAllGroupsButton, 2000);
            default:
                System.out.println("[Groups.clickElement()] The given element name: '" + element + "' is not valid!");
                return false;
        }
    }

    public void updateFilterOptions() {
        cachedFilterOptionsList = ElementsUtils.getElementsListText(webElementListFirstFilterDropDownOptions);
    }

//    public void updateSecondFilterOptions() {
//        cachedSecondFilterOptionsList = ElementsUtils.getElementsListText(webElementListSecondFilterDropDownOptions);
//    }

    public boolean setText(String text) {
        return ElementsUtils.setElementValue(webElementGroupNameTextField, text);
    }

    public boolean selectFirstFilterToggleButton() {
        return ElementsUtils.clickElementAndWait(webElementFirstFilterToggleButton, 2000);
    }

    public boolean selectSecondFilterToggleButton() {
        return ElementsUtils.clickElementAndWait(webElementSecondFilterToggleButton, 2000);
    }

    public boolean selectFirstFilter(String filter) {
        updateFilterOptions();
        int filterIndex = cachedFilterOptionsList.indexOf(filter);

        if (filterIndex >= 0) {
            return ElementsUtils.clickElementAndWait(webElementListFirstFilterDropDownOptions.get(filterIndex), 2000);
        }
        return false;
    }

    public boolean selectSecondFilter(String filter) {
        updateFilterOptions();
        int filterIndex = cachedFilterOptionsList.indexOf(filter);

        if (filterIndex >= 0) {
            return ElementsUtils.clickElementAndWait(webElementListSecondFilterDropDownOptions.get(filterIndex), 2000);
        }
        return false;
    }

    public boolean collapseDropDown() {
        return ElementsUtils.clickElementAndWait(webElementCollapseDropDown, 2000);
    }

    public void updateButtonsList() {
        cachedButtonsList = ElementsUtils.getElementsListText(webElementListAddSaveCancelButtons);
    }

    public boolean selectButton(String button) {
        updateButtonsList();

        int resultIndex = cachedButtonsList.indexOf(button);
        if (resultIndex >= 0) {
            return ElementsUtils.clickElementAndWait(webElementListAddSaveCancelButtons.get(resultIndex), 3000);
        }
        return false;
    }
}
