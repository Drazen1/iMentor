package pageObjects;

import extensions.DropDownUtils;
import extensions.ElementsUtils;
import extensions.FrameworkUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import java.util.List;

public class Attendance {

    private WebDriver driver;

    @FindBy(css = "button.dropdown-toggle-button>span:not(.caret)")
    private WebElement webElementChooseClass;

    @FindBy(className = "multi-select-dropdown-container")
    private WebElement webElementChooseClassDropDown;

    @FindBy(css = "li.item")
    private List<WebElement> webElementListClassList;

    @FindBy(css = "table.table-attendance")
    private WebElement webElementAttendanceTable;

    private List<String> cachedDropDownOptions;

    /**
     * Class constructor;
     *
     * @param driver
     */
    public Attendance(WebDriver driver) {
        this.driver = driver;
        WebElement contentElement = FrameworkUtils.getContentElement(driver, By.cssSelector("div.attendance-page"));
        PageFactory.initElements(new DefaultElementLocatorFactory(contentElement), this);
    }

    public void updateOptions() {
        cachedDropDownOptions = ElementsUtils.getElementsListText(webElementListClassList);
    }

    public List<String> getDropDownOptions() {
        updateOptions();
        return cachedDropDownOptions;
    }

    public boolean selectChooseClass() {
        return ElementsUtils.clickElementAndWait(webElementChooseClass, 2000);
    }

    public boolean selectOption(String option) {
        updateOptions();
        int resultIndex = cachedDropDownOptions.indexOf(option);
        if (resultIndex >= 0) {
            return ElementsUtils.clickElementAndWait(webElementListClassList.get(resultIndex), 5000);
        }
        return false;
    }

    public boolean isAttendanceTableDisplayed() {
        return ElementsUtils.isElementDisplayed(webElementAttendanceTable);
    }
}
