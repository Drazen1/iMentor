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

public class PreviewAnnouncement {

    private WebDriver driver;

    @FindBy(css = ".page-content-section label")
    private List<WebElement> webElementsListInformationLabels;

    @FindBy(css = "div.row.page-content-section:nth-child(3) button:nth-child(2)")
    private WebElement webElementEitButton;

    private List<String> cachedLabelsList = new ArrayList<>();

    /**
     * Class constructor;
     *
     * @param driver
     */
    public PreviewAnnouncement(WebDriver driver) {
        this.driver = driver;
        refresh();
    }

    public void refresh() {
        WaitUtils.waitForPageProcessing(4000);

        WebElement contentContainer = FrameworkUtils.getContentElement(driver, By.cssSelector("div.modal-container"));
        PageFactory.initElements(new DefaultElementLocatorFactory(contentContainer), this);
    }

    public boolean clickButton(String button) {
        switch (button) {
            case "edit":
                return ElementsUtils.clickElementAndWait(webElementEitButton, 2000);
            default:
                System.out.println("[PreviewAnnouncement.clickButton()] The given element name: '" + button + "' is not valid!");
                return false;
        }
    }

    public void updateLabelsList() {
        cachedLabelsList = ElementsUtils.getElementsListText(webElementsListInformationLabels);
    }

    public List<String> getLabelsList() {
        updateLabelsList();
        return cachedLabelsList;
    }
}
