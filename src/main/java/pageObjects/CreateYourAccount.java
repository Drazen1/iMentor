package pageObjects;

import extensions.ElementsUtils;
import extensions.FrameworkUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public class CreateYourAccount {

    private WebDriver driver;

    @FindBy(css = ".signupform>h2")
    private WebElement webElementTitle;

    @FindBy(css = "input[ng-model*='first_name']")
    private WebElement webElementFirstNameField;

    @FindBy(css = "input[ng-model*='last_name']")
    private WebElement webElementLastNameField;

    @FindBy(css = "input[ng-model*='email']")
    private WebElement webElementEmailField;

    private WebElement webElementPasswordField;

    private WebElement webElementConfirmPassword;

    private WebElement webElementCreateButton;

    public CreateYourAccount(WebDriver driver) {
        this.driver = driver;
        WebElement contentElement = FrameworkUtils.getContentElement(driver, By.cssSelector(".landingPage"));
        PageFactory.initElements(new DefaultElementLocatorFactory(contentElement), this);
    }

    public String getTitle() {
        return ElementsUtils.getElementText(webElementTitle);
    }
}
