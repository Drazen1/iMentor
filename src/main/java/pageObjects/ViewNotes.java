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

public class ViewNotes {

    private WebDriver driver;

    @FindBy(css = "p.template-name")
    private List<WebElement> webElementsListNotes;

    @FindBy(css = "i.fa-trash-o")
    private List<WebElement> webElementListTrashIcons;

    /**
     * Class constructor;
     *
     * @param driver
     */
    public ViewNotes(WebDriver driver) {
        WaitUtils.waitForPageProcessing(2000);
        this.driver = driver;

        WebElement contentElement = FrameworkUtils.getContentElement(driver, By.cssSelector("div.notes-window"));
        PageFactory.initElements(new DefaultElementLocatorFactory(contentElement), this);
    }

    public boolean clickOnTrashIcon(int index) {
        if ((index >= 0) && (webElementListTrashIcons.size() > index)) {
            return ElementsUtils.clickElementAndWait(webElementListTrashIcons.get(index), 2500);
        }
        return false;
    }
}
