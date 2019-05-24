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

public class LessonPage {

    private WebDriver driver;

    @FindBy(css = "div.message")
    private List<WebElement> webElementListMessages;

    @FindBy(css = "span.toggle-writing-area")
    private WebElement webElementWritingAreaIcon;

    @FindBy(css = "div.writing-body")
    private WebElement webElementWritingBody;

    @FindBy(tagName = "textarea")
    private WebElement webElementWritingBodyTextarea;

    @FindBy(css = "button.pull-right")
    private WebElement webElementSendMessageButton;

    @FindBy(css = "button[ng-reflect-router-link*='lesson']")
    private WebElement webElementBackToLesson;

    @FindBy(css = "button[ng-reflect-router-link*='home']")
    private WebElement webElementBackToHomepage;

    private List<String> cachedMessages;

    /**
     * Class constructor;
     *
     * @param driver
     */
    public LessonPage(WebDriver driver) {
        this.driver = driver;
        WebElement contentElement = FrameworkUtils.getContentElement(driver, By.cssSelector("div.curriculum-page"));
        PageFactory.initElements(new DefaultElementLocatorFactory(contentElement), this);
    }

    public void updateMessages() {
        cachedMessages = ElementsUtils.getElementsListText(webElementListMessages);
    }

    public boolean containsMessage(String message) {
        updateMessages();

        int resultIndex = cachedMessages.indexOf(message);
        if (resultIndex >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isWritingBodyExpanded() {
        return webElementWritingBody.getAttribute("style").contains("height");
    }

    public boolean sendMessage(String message) {
        if (!isWritingBodyExpanded()) {
            ElementsUtils.clickElementAndWait(webElementWritingAreaIcon, 3000);
        }
        if (ElementsUtils.setElementValueAndWait(webElementWritingBodyTextarea, message, 2000)) {
            return ElementsUtils.clickElementAndWait(webElementSendMessageButton, 2000);
        }
        return false;
    }

    public boolean clickBackToLesson() {
        return ElementsUtils.clickElementAndWait(webElementBackToLesson, 3000);
    }

    public boolean clickBackToHomepage() {
        return ElementsUtils.clickElementAndWait(webElementBackToHomepage, 3000);
    }

}
