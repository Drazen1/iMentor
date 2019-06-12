package pageObjects;

import extensions.ElementsUtils;
import extensions.FrameworkUtils;
import extensions.MouseUtils;
import extensions.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import java.util.ArrayList;
import java.util.List;

public class NoteTracker {

    private WebDriver driver;

    @FindBy(css = "h1.header-text")
    private WebElement webElementTitle;

    @FindBy(css = "button.dropdown-toggle-button")
    private WebElement webElementSelectPartnerDropDownToggleButton;

    @FindBy(css = ".fa.fa-angle-up")
    private WebElement webElementExpandCollapseButton;

    @FindBy(css = ".item.ng-star-inserted")
    private List<WebElement> webElementListDropDownOptions;

    @FindBy(css = ".col-md-4 .selection.inputnoteditable.ng-untouched.ng-pristine.ng-valid")
    private WebElement webElementStartDateToggleButton;

    @FindBy(css = ".col-md-8 .selection.inputnoteditable.ng-untouched.ng-pristine.ng-valid")
    private WebElement webElementEndDateToggleButton;

    @FindBy(css = "div.chart.blue")
    private WebElement webElementChartDiagram;

    @FindBy(css = ".title.blue-bg")
    private WebElement webElementChardDiagramTitle;

    @FindBy(css = ".add-quick-container>span")
    private WebElement webElementAddQuickNoteLabel;

    @FindBy(css = "button.add-quick-text.add-quick-button")
    private List<WebElement> webElementListQuickButtons;

    @FindBy(css = ".fa.fa-square-o")
    private List<WebElement> webElementListCheckBoxes;

    @FindBy(css = "table tr td:nth-child(2)")
    private List<WebElement> webElementListMenteeNames;

    @FindBy(css = "table tr td:nth-child(3)")
    private List<WebElement> webElementListMentorNames;

    @FindBy(css = "table tr td:nth-child(4)")
    private List<WebElement> webElementListMenteeNoteNumber;

    @FindBy(css = "table tr td:nth-child(5)")
    private List<WebElement> webElementListMentorNoteNumber;

    @FindBy(css = "table tr td:nth-child(6)")
    private List<WebElement> webElementListPairNoteNumber;

    private List<String> cachedPartnersList = new ArrayList<>();
    private List<String> cachedQuickButtonsList = new ArrayList<>();


    /**
     * Class constructor;
     *
     * @param driver
     */
    public NoteTracker(WebDriver driver) {
        WaitUtils.waitForPageProcessing(2000);
        this.driver = driver;

        refresh();
    }

    public void refresh() {
        WebElement contentElement = FrameworkUtils.getContentElement(driver, By.cssSelector("div.note-tracker-page"));
        PageFactory.initElements(new DefaultElementLocatorFactory(contentElement), this);
    }

    public boolean clickNoteNumber(int index, String type) {
        switch(type.toLowerCase()) {
            case "mentee":
                if (webElementListMenteeNoteNumber.size() > index) {
                    return ElementsUtils.clickElementAndWait(webElementListMenteeNoteNumber.get(index), 2000);
                }
                return false;
            case "mentor":
                if (webElementListMentorNoteNumber.size() > index) {
                    return ElementsUtils.clickElementAndWait(webElementListMentorNoteNumber.get(index), 2000);
                }
                return false;
            case "pair":
                if (webElementListPairNoteNumber.size() > index) {
                    return ElementsUtils.clickElementAndWait(webElementListPairNoteNumber.get(index), 2000);
                }
                return false;
            default:
                System.out.println("[NoteTracker.clickNoteNumber()] The given type name: '" + type + "' is not valid!");
                return false;
        }
    }

    public boolean expandPartnerDropDown() {
        return ElementsUtils.clickElementAndWait(webElementSelectPartnerDropDownToggleButton, 2000);
    }

    public void updatePartnersList() {
        cachedPartnersList = ElementsUtils.getElementsListText(webElementListDropDownOptions);
    }

    public boolean selectStartDate() {
        return ElementsUtils.clickElementAndWait(webElementStartDateToggleButton, 1500);
    }

    public boolean selectEndDate() {
        return ElementsUtils.clickElementAndWait(webElementEndDateToggleButton, 1500);
    }

    public boolean selectPartner(String partner) {
        updatePartnersList();

        int index = cachedPartnersList.indexOf(partner);
        if (index >= 0) {
            return ElementsUtils.clickElementAndWait(webElementListDropDownOptions.get(index), 2000);
        }
        return false;
    }

    public boolean expandCollapseDropDown() {
        return ElementsUtils.clickElementAndWait(webElementExpandCollapseButton, 2000);
    }

    public int getPairNotesNumberFirstRecord() {
        int number = Integer.parseInt(webElementListPairNoteNumber.get(0).getText());
        return number;
    }

    public boolean selectFirstMenteeMentorPair() {
        try {
            MouseUtils.scrollIntoView(webElementAddQuickNoteLabel);
            webElementListCheckBoxes.get(1).click();
            Thread.sleep(2000);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateQuickButtonsList() {
        cachedQuickButtonsList = ElementsUtils.getElementsListText(webElementListQuickButtons);
    }

    public boolean selectQuickButton(String button) {
        updateQuickButtonsList();
        if (cachedQuickButtonsList.contains(button)) {
            MouseUtils.scrollIntoView(webElementChartDiagram);
            webElementListQuickButtons.get(cachedQuickButtonsList.indexOf(button)).click();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean isDisplayed(String element) {
        switch(element.toLowerCase()) {
            case "selectpartner":
                return ElementsUtils.isElementDisplayed(webElementSelectPartnerDropDownToggleButton);
            case "diagram":
                return ElementsUtils.isElementDisplayed(webElementChartDiagram);
            case "diagramtitle":
                return ElementsUtils.isElementDisplayed(webElementChardDiagramTitle) && ElementsUtils.getElementText(webElementChardDiagramTitle).length() > 0;
            default:
                System.out.println("[NoteTracker.isDisplayed()] The given element name: '" + element + "' is not displayed!");
                return false;
        }
    }
}
