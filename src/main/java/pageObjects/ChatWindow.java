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

public class ChatWindow {

    private WebDriver driver;
    private WebElement rootElement;

    @FindBy(css = ".writing-area>textarea")
    private WebElement webElementWritingArea;

    @FindBy(css = "div.send-btn-wrapper")
    private WebElement webElementSendButton;

    @FindBy(css = ".single-message-wrapper .ng-star-inserted")
    private List<WebElement> webElementListMessagesSent;

    private List<String> cachedMessagesSent = new ArrayList<>();


    public ChatWindow(WebDriver driver) {
        WaitUtils.waitForPageProcessing(3000);
        this.driver = driver;
        WebElement rootElement = FrameworkUtils.getContentElement(driver, By.cssSelector("div.chat-window:not([hidden])"));
        PageFactory.initElements(new DefaultElementLocatorFactory(rootElement), this);
    }

    public void updateSentMessages() {
        cachedMessagesSent = ElementsUtils.getElementsListText(webElementListMessagesSent);
    }

    public List<String> getSentMessages() {
        return cachedMessagesSent;
    }

    public boolean checkIfMessageExists(String expectedMessage) {
        updateSentMessages();
        for(String sentMessage : cachedMessagesSent) {
            if (sentMessage.equalsIgnoreCase(expectedMessage)) {
                return true;
            }
        }
        return false;
    }

    public boolean setMessage(String message) {
        return ElementsUtils.setElementValue(webElementWritingArea, message);
    }

    public boolean sendMessage() {
        return ElementsUtils.clickElementAndWait(webElementSendButton, 2000);
    }
}
