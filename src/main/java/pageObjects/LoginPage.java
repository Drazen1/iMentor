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

public class LoginPage {

    private WebDriver driver;

    @FindBy(id = "email")
    private WebElement webElementEmailAddressField;

    @FindBy(id = "password")
    private WebElement webElementPasswordField;

    @FindBy(css = ".forgot-pass>a")
    private WebElement webElementForgotPasswordLink;

    @FindBy(id = "sign-in")
    private WebElement webElementSignInButton;

    @FindBy(className = "main-header")
    private WebElement webElementTitle;

    @FindBy(css = "a[href*='mentor']>button")
    private WebElement webElementMentorApplicationButton;

    @FindBy(css = "a[href*='mentee']>button")
    private WebElement webElementStudentApplicationButton;

    @FindBy(css = "a[href*='privacy']")
    private WebElement webElementPrivacyPolicyLink;

    /**
     * Class constructor;
     *
     * @param driver
     */
    public LoginPage(WebDriver driver) {
        WaitUtils.waitForPageProcessing(3000);
        this.driver = driver;
        WebElement contentElement = FrameworkUtils.getContentElement(driver, By.className("landingPage"));
        PageFactory.initElements(new DefaultElementLocatorFactory(contentElement), this);
    }

    public boolean setEmail(String emailAddress) {
        return ElementsUtils.setElementValue(webElementEmailAddressField, emailAddress);
    }

    public boolean setPassword(String password) {
        return ElementsUtils.setElementValue(webElementPasswordField, password);
    }

    public boolean signIn() {
        return ElementsUtils.clickElementAndWait(webElementSignInButton, 6000);
    }

    public boolean isDisplayed(String element) {
        switch (element.toLowerCase()) {
            case "emailfield":
                return ElementsUtils.isElementDisplayed(webElementEmailAddressField);
            case "passwordfield":
                return ElementsUtils.isElementDisplayed(webElementPasswordField);
            case "forgetpassword":
                return ElementsUtils.isElementDisplayed(webElementForgotPasswordLink);
            case "signin":
                return ElementsUtils.isElementDisplayed(webElementSignInButton);
            case "title":
                return ElementsUtils.isElementDisplayed(webElementTitle);
            case "mentorapplicationbutton":
                return ElementsUtils.isElementDisplayed(webElementMentorApplicationButton);
            case "studentapplicationbutton":
                return ElementsUtils.isElementDisplayed(webElementStudentApplicationButton);
            case "privacypolicy":
                return ElementsUtils.isElementDisplayed(webElementPrivacyPolicyLink);
            default:
                System.out.println("[LoginPage.isDisplayed()] The given element name: '" + element + "' is not valid!");
                return false;
        }
    }

    public boolean clickElement(String element) {
        switch (element.toLowerCase()) {
            case "forgotpassword":
                return ElementsUtils.clickElementAndWait(webElementForgotPasswordLink, 3000);
            case "mentorapplication":
                return ElementsUtils.clickElementAndWait(webElementMentorApplicationButton, 3000);
            case "stundentapplication":
                return ElementsUtils.clickElementAndWait(webElementStudentApplicationButton, 3000);
            default:
                System.out.println("[LoginPage.clickElement()] The given element name: '" + element + "' is not valid!");
                return false;
        }
    }

    public String getTitle() {
        return ElementsUtils.getElementText(webElementTitle);
    }
}
