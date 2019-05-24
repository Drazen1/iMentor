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

import java.util.List;

public class HomePageAdmin {

    private WebDriver driver;

    @FindBy(css = "h1.header-text")
    private WebElement webElementPageTitle;

    @FindBy(css = "div.sq-header")
    private List<WebElement> webElementListItems;

    private List<String> cachedItemsList;

    /**
     * Class constructor;
     *
     * @param driver
     */
    public HomePageAdmin(WebDriver driver) {
        WaitUtils.waitForPageProcessing(3000);
        this.driver = driver;
        WebElement rootElement = FrameworkUtils.getContentElement(driver, By.cssSelector(".container.im-container"));
        PageFactory.initElements(new DefaultElementLocatorFactory(rootElement), this);

        updateItemsList();
    }

    public void updateItemsList() {
        cachedItemsList = ElementsUtils.getElementsListText(webElementListItems);
    }

    public boolean selectItem(String option) {
        int resultIndex = cachedItemsList.indexOf(option);
        if (resultIndex >= 0) {
            return ElementsUtils.clickElementAndWait(webElementListItems.get(resultIndex), 3000);
        }
        return false;
    }

    public String getTitle() {
        return ElementsUtils.getElementText(webElementPageTitle);
    }


}
