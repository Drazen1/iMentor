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

public class LessonManagementPage {

    private WebDriver driver;

    @FindBy(className = "header-text")
    private WebElement webElementTitle;

    @FindBy(css = ".col .content imentor-multi-select-dropdown .dropdown-toggle-button span:first-of-type")
    private WebElement webElementChooseClass;

    @FindBy(css = ".col .content imentor-multi-select-dropdown .dropdown-toggle-button span:nth-child(2)")
    private WebElement webElementExpandCollapseIcon;

    @FindBy(css = ".content .multi-select-dropdown-container")
    private WebElement webElementGroupDropDownContainer;

    @FindBy(css = "li.item")
    private List<WebElement> webElementListGroups;

    @FindBy(xpath = "//div[4]//div[1]//div[1]//imentor-multi-select-dropdown[1]//div[1]//button[1]")
    private WebElement webElementGradeDropDown;

    @FindBy(xpath = "//li[contains(text(),'Grade 9')]")
    private WebElement webElementGradeValue;

    @FindBy(xpath = "//div[@class='row page-content-section']//div[2]//imentor-multi-select-dropdown[1]//div[1]//button[1]")
    private WebElement webElementUnitDropDown;

    @FindBy(xpath = "//li[contains(text(),'Test1 (1)')]")
    private WebElement webElementUnitFirstValue;

    @FindBy(xpath = "//button[@class='dropdown-toggle-button']//span[contains(text(),'Lesson')]")
    private WebElement webElementLessonDropDown;

    @FindBy(xpath = "//li[contains(text(),'Lesson1 (1) (National)')]")
    private WebElement webElementLessonValue;

    @FindBy(css = "div.selectiongroup")
    private WebElement webElementDateButton;

    @FindBy(css = ".ngb-tp-hour input")
    private WebElement webElementHourFiled;

    @FindBy(css = ".ngb-tp-minute input")
    private WebElement webElementMinutesFiled;

    @FindBy(css = ".submit-button button")
    private WebElement webElementScheduleLessonButton;

    @FindBy(css = "div.modal-content")
    private WebElement webElementCongratsPopUp;

    @FindBy(css = ".modal-footer button")
    private WebElement webElementPopUpManageLessonsButtons;

    @FindBy(css = ".icon .fa.fa-pencil")
    private List<WebElement> webElementListPencilIcons;

    @FindBy(css = ".fa.fa-trash-o")
    private WebElement webElementDeleteScheduledLesson;

    @FindBy(css = ".modal-footer button:first-child")
    private WebElement webElementConfirmDeleteButton;

    private List<String> cachedOptionsList = new ArrayList<>();


    /**
     * Class constructor;
     *
     * @param driver
     */
    public LessonManagementPage(WebDriver driver) {
        this.driver = driver;
        WaitUtils.waitForPageProcessing(3000);

        WebElement contentElement = FrameworkUtils.getContentElement(driver, By.cssSelector("div.lesson-management-page"));
        PageFactory.initElements(new DefaultElementLocatorFactory(contentElement), this);

        updateOptionsList();
    }

    public void updateOptionsList() {
        cachedOptionsList = ElementsUtils.getElementsListText(webElementListGroups);
    }

    public boolean selectOption(String option) {
        updateOptionsList();
        int resultIndex = cachedOptionsList.indexOf(option);

        if (resultIndex >= 0) {
            return ElementsUtils.clickElementAndWait(webElementListGroups.get(resultIndex), 2000);
        }
        return false;
    }

    public boolean clickElement(String element) {
        switch (element.toLowerCase()) {
            case "chooseclass":
                return ElementsUtils.clickElementAndWait(webElementChooseClass, 1500);
            case "grade":
                return ElementsUtils.clickElementAndWait(webElementGradeDropDown, 1500);
            case "gradevalue":
                return ElementsUtils.clickElementAndWait(webElementGradeValue, 1500);
            case "unit":
                return ElementsUtils.clickElementAndWait(webElementUnitDropDown, 1500);
            case "unitvalue":
                return ElementsUtils.clickElementAndWait(webElementUnitFirstValue, 1500);
            case "lesson":
                return ElementsUtils.clickElementAndWait(webElementLessonDropDown, 1500);
            case "lessonvalue":
                return ElementsUtils.clickElementAndWait(webElementLessonValue, 1500);
            case "date":
                return ElementsUtils.clickElementAndWait(webElementDateButton, 1500);
            case "schedulelesson":
                return ElementsUtils.clickElementAndWait(webElementScheduleLessonButton, 3000);
            default:
                System.out.println("[LessonManagementPage.clickElement()] The given element: '" + element + "' is not valid!");
                return false;
        }
    }

    public boolean setHours(int value) {
        return ElementsUtils.setElementValue(webElementHourFiled, Integer.toString(value));
    }

    public boolean setMinutes(int value) {
        return ElementsUtils.setElementValue(webElementMinutesFiled, Integer.toString(value));
    }

    public boolean isCongratsPoPupDisplayed() {
        return ElementsUtils.isElementDisplayed(webElementCongratsPopUp);
    }

    public boolean clickManageLessonsFromPopUp() {
        return ElementsUtils.clickElementAndWait(webElementPopUpManageLessonsButtons, 2000);
    }

    public boolean deleteAllScheduledLessons() {
        if (webElementListPencilIcons.size() > 0) {
            while(webElementListPencilIcons.size() > 0) {
                if (ElementsUtils.clickElementAndWait(webElementListPencilIcons.get(0), 2000)) {
                    if (ElementsUtils.clickElementAndWait(webElementDeleteScheduledLesson, 2000)) {
                        if (ElementsUtils.clickElementAndWait(webElementConfirmDeleteButton, 2000)) {
                            continue;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
