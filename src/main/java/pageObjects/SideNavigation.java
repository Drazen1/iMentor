package pageObjects;

import extensions.ElementsUtils;
import extensions.FrameworkUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import java.util.List;

public class SideNavigation {

    private WebDriver driver;

    @FindBy(css = ".nav.nav-pills.nav-stacked>li>a")
    private List<WebElement> webElementListNavigationOptions;

    @FindBy(css = ".submenu>li>a")
    private List<WebElement> webElementListNavigationSubmenuOptions;

    private List<String> cachedNavigationOptions;
    private List<String> cachedNavigationSubmenuOptions;

    /**
     * Class constructor;
     *
     * @param driver
     */
    public SideNavigation(WebDriver driver) {
        this.driver = driver;
        WebElement rootElement = FrameworkUtils.getContentElement(driver, By.cssSelector("div.side-navbar.open"));
        PageFactory.initElements(new DefaultElementLocatorFactory(rootElement), this);

        updateNavigationOptions();
    }

    public void updateNavigationOptions() {
        cachedNavigationOptions = ElementsUtils.getElementsListText(webElementListNavigationOptions);
    }

    public void updateSubmenuOptions() {
        cachedNavigationSubmenuOptions = ElementsUtils.getElementsListText(webElementListNavigationSubmenuOptions);
    }

    public List<String> getNavigationOptions() {
        return cachedNavigationOptions;
    }

    public List<String> getNavigationSubmenuOptions() {
        return cachedNavigationSubmenuOptions;
    }

    public boolean selectNavigationOption(String option) {
        int resultIndex = cachedNavigationOptions.indexOf(option);

        if (resultIndex >= 0) {
            return ElementsUtils.clickElementAndWait(webElementListNavigationOptions.get(resultIndex), 2000);
        }
        return false;
    }

    public boolean selectNavigationSubmenuOption(String navOption, String subNavOptions) {
        int navOptionIndex = cachedNavigationOptions.indexOf(navOption);

        if ((navOptionIndex >= 0)) {
            if (ElementsUtils.clickElementAndWait(webElementListNavigationOptions.get(navOptionIndex), 2000)) {
                updateSubmenuOptions();
                int navSubmenuOptionIndex = cachedNavigationSubmenuOptions.indexOf(subNavOptions);
                if (navSubmenuOptionIndex >= 0) {
                    return ElementsUtils.clickElementAndWait(webElementListNavigationSubmenuOptions.get(navSubmenuOptionIndex), 2000);
                }
            }
        }
        return false;
    }

}
