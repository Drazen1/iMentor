package pageObjects;

import extensions.ElementsUtils;
import extensions.FrameworkUtils;
import extensions.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class NoteCatalog {

    private WebDriver driver;

    @FindBy(css = ".col-md-4 .mydp .selectiongroup.ng-star-inserted input")
    private WebElement webElementStartDateToggleButton;

    @FindBy(xpath = ".col-md-8 .mydp .selectiongroup.ng-star-inserted input")
    private WebElement webElementEndDateToggleButton;

    @FindBy(css = "button.dropdown-toggle-button")
    private List<WebElement> webElementListFilterDropDownButtons;

    @FindBy(css = ".fa.fa-angle-down")
    private List<WebElement> webElementListExpandCollapseIcons;

    @FindBy(css = "div.multi-select-dropdown-container.show li.item")
    private List<WebElement> webElementListDropDownOptions;

    @FindBy(css = ".col>button")
    private WebElement webElementApplyFiltersButton;

    @FindBy(css = "table.note-catalog-table")
    private WebElement webElementNoteCatalogTable;

    private List<String> cachedFiltersList = new ArrayList<>();
    private List<String> cachedDropDownOptions = new ArrayList<>();

    /**
     * Class constructor;
     * @param driver
     */
    public NoteCatalog(WebDriver driver) {
        this.driver = driver;
        refresh();
    }

    public void refresh() {
        WaitUtils.waitForPageProcessing(2000);
        WebElement contentElement = FrameworkUtils.getContentElement(driver, By.cssSelector("div.note-catalog-page"));
        Assert.assertNotNull(contentElement, "The contentElement is null!");
    }

    public void updateFiltersList() {
        cachedFiltersList = ElementsUtils.getElementsListText(webElementListFilterDropDownButtons);
    }

    public void updateDropDownOptions() {
        cachedDropDownOptions = ElementsUtils.getElementsListText(webElementListDropDownOptions);
    }

    public boolean selectFilterOption(String filter) {
        updateFiltersList();

        int resultIndex = cachedFiltersList.indexOf(filter);
        if (resultIndex >= 0) {
            return ElementsUtils.clickElementAndWait(webElementListFilterDropDownButtons.get(resultIndex), 2000);
        }
        return false;
    }

    public boolean collapseDropDown(String filter) {
        updateFiltersList();

        int resultIndex = cachedFiltersList.indexOf(filter);
        if (resultIndex >= 0) {
            return ElementsUtils.clickElementAndWait(webElementListExpandCollapseIcons.get(resultIndex), 2000);
        }
        return false;
    }

    public boolean selectDropDownOption(String option) {
        updateDropDownOptions();

        int resultIndex = cachedDropDownOptions.indexOf(option);
        if (resultIndex >= 0) {
            return ElementsUtils.clickElementAndWait(webElementListDropDownOptions.get(resultIndex), 2000);
        }
        return false;
    }

    public boolean selectStartDate() {
        return ElementsUtils.clickElementAndWait(webElementStartDateToggleButton, 2000);
    }

    public boolean selectEndDate() {
        return ElementsUtils.clickElementAndWait(webElementEndDateToggleButton, 2000);
    }

    public boolean applyFilters() {
        boolean actionStatus = ElementsUtils.clickElementAndWait(webElementApplyFiltersButton, 2000);
        this.refresh();
        return actionStatus;
    }

    public boolean isCatalogTableDisplayed() {
        return ElementsUtils.isElementDisplayed(webElementNoteCatalogTable);
    }

}
