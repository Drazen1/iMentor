package pageObjects;

import extensions.ElementsUtils;
import extensions.FrameworkUtils;
import extensions.MouseUtils;
import extensions.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import java.util.List;

public class EditAnnouncement {

    private WebDriver driver;

    @FindBy(css = ".fa.fa-lg.checkbox.fa-square-o")
    private List<WebElement> webElementListInformationCheckBoxes;

    @FindBy(css = ".fa-check-square-o")
    private List<WebElement> webElementListCheckedFields;

    @FindBy(css = ".field-name>span")
    private List<WebElement> webElementListInformationFieldsToConfirm;

    @FindBy(css = ".col .btn.imentor-btn-primary.green")
    private WebElement webElementSaveButton;

    @FindBy(css = ".modal-content button")
    private WebElement webElementPopUpOkButton;

    /**
     * Class constructor;
     *
     * @param driver
     */
    public EditAnnouncement(WebDriver driver) {
        this.driver = driver;
        WaitUtils.waitForPageProcessing(3000);
        refresh();
    }

    public void refresh() {
        WebElement contentElement = FrameworkUtils.getContentElement(driver, By.cssSelector("div.tasks-and-announcements-page"));
        PageFactory.initElements(new DefaultElementLocatorFactory(contentElement), this);
    }

    public boolean clickNextFreeField() {
        return ElementsUtils.clickElementAndWait(webElementListInformationCheckBoxes.get(1), 1500);
    }

    public boolean clickSaveButton() {
        driver.switchTo().defaultContent();
        boolean actionStatus = false;
        try {
            MouseUtils.scrollIntoView(webElementSaveButton);
            actionStatus = ElementsUtils.clickElementAndWait(webElementSaveButton, 4000);
        }catch (StaleElementReferenceException e) {}
        refresh();
        return actionStatus;
    }

    public boolean clickPopUpOkButton() {
        return ElementsUtils.clickElementAndWait(webElementPopUpOkButton, 2000);
    }

    public boolean uncheckFields() {
        if (webElementListInformationCheckBoxes.size() < 3) {
            return ElementsUtils.clickElementAndWait(webElementListInformationCheckBoxes.get(webElementListInformationCheckBoxes.size()-1), 1500);
        }
        return true;
    }

    public boolean editCustomAnnouncement() {
        driver.switchTo().frame(driver.findElement(By.cssSelector("#mceu_38>iframe")));
        WebElement body = driver.findElement(By.tagName("body")); // then you find the body
        body.sendKeys(Keys.CONTROL + "a"); // send 'ctrl+a' to select all
        body.sendKeys(Long.toString(System.currentTimeMillis()));
        return true;
    }
}
