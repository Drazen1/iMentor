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

public class ModalWindow {

    private WebDriver driver;

    @FindBy(css = "button:first-of-type")
    private WebElement webElementSaveButton;

    @FindBy(css = "button:first-of-type")
    private WebElement webElementDeleteButton;

    @FindBy(css = "button:nth-child(2)")
    private WebElement webElementCancelButton;

    /**
     * Class constructor;
     *
     * @param driver
     */
    public ModalWindow(WebDriver driver) {
        WaitUtils.waitForPageProcessing(2000);
        this.driver = driver;

        WebElement contentElement = FrameworkUtils.getContentElement(driver, By.cssSelector("div.modal-content"));
        PageFactory.initElements(new DefaultElementLocatorFactory(contentElement), this);
    }

    public boolean clickElement(String element) {
        switch (element.toLowerCase()) {
            case "save":
                return ElementsUtils.clickElementAndWait(webElementSaveButton, 3000);
            case "delete":
                return ElementsUtils.clickElementAndWait(webElementDeleteButton, 3000);
            case "cancel":
                return ElementsUtils.clickElementAndWait(webElementCancelButton, 3000);
            default:
                System.out.println("[ModalWindow.clickElement()] The given elment name is not valid!");
                return false;
        }
    }
}
