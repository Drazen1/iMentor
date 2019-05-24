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

public class Navigation {

    private WebDriver driver;

    @FindBy(css = "span.indicator-counter")
    private WebElement webElementChatIcon;

    //@FindBy(css = "span.hamburger-toggle")
    @FindBy(css = ".fa.fa-bars")
    private WebElement webElementDropDownMenuIcon;

    /**
     * Class constructor;
     *
     * @param driver
     */
    public Navigation(WebDriver driver) {
        WaitUtils.waitForPageProcessing(5000);
        this.driver = driver;
        WebElement contentElement = FrameworkUtils.getContentElement(driver, By.cssSelector("div.navbar-container"));
        PageFactory.initElements(new DefaultElementLocatorFactory(contentElement), this);
    }

    public boolean clickElement(String element) {
        switch (element.toLowerCase()) {
            case "chaticon":
                return ElementsUtils.clickElementAndWait(webElementChatIcon, 1500);
            case "menuicon":
                return ElementsUtils.clickElementAndWait(webElementDropDownMenuIcon, 1500);
            default:
                System.out.println("[HomePageAdmin.clickElement()] The given element name: '" + element + "' is not valid!");
                return false;
        }
    }
}
