package pageObjects;

import extensions.ElementsUtils;
import extensions.FrameworkUtils;
import extensions.MouseUtils;
import extensions.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Curriculum {

    private WebDriver driver;

    @FindBy(css = "button.dropdown-toggle-button")
    private WebElement webElementSelectGradeDropDownButton;

    @FindBy(css = ".multi-select-dropdown-container.show li.item")
    private List<WebElement> webElementListDropDownOptions;

    @FindBy(css = "span.item-title")
    private List<WebElement> webElementListUnitsList;

    @FindBy(css = ".fa.fa-pencil.fa-lg")
    private List<WebElement> webElementListEditIcon;

    @FindBy(css = ".col-lg-6.list-container button")
    private WebElement webElementAddUnitButton;

    @FindBy(css = ".col.title-group>input")
    private WebElement webElementUnitTitleTextField;

    @FindBy(css = ".col.number-group>input")
    private WebElement webElementUnitNumberTextField;

    @FindBy(css = "button.btn.imentor-btn-primary.green:first-of-type")
    private WebElement webElementSaveButton;

    @FindBy(css = ".col-lg-6.list-container:nth-child(2) h3.list-heading")
    private WebElement webElementLessonsTitle;

    @FindBy(css = ".col-lg-6.list-container:nth-child(2) button")
    private WebElement webElementAddLessonButton;

    @FindBy(id = "lessonTitle")
    private WebElement webElementLessonTitleTextField;

    @FindBy(id = "lessonNumber")
    private WebElement webElementLessonNumberTextField;

    @FindBy(css = ".col.mentee-only-group label")
    private List<WebElement> webElementListYesNoMenteeOnly;

    private List<String> cachedOptionsList = new ArrayList<>();
    private List<String> cachedUnitsList = new ArrayList<>();

    /**
     * Class constructor;
     *
     * @param driver
     */
    public Curriculum(WebDriver driver) {
        this.driver = driver;
        refresh();
    }

    public void refresh() {
        WaitUtils.waitForPageProcessing(2000);
        WebElement contentElement = FrameworkUtils.getContentElement(driver, By.cssSelector("div.curriculum-page"));
        PageFactory.initElements(new DefaultElementLocatorFactory(contentElement), this);
    }

    public void updateOptionsList() {
        cachedOptionsList = ElementsUtils.getElementsListText(webElementListDropDownOptions);
    }

    public boolean clickOnSelectGradeToggleButton() {
        return ElementsUtils.clickElementAndWait(webElementSelectGradeDropDownButton, 2000);
    }

    public boolean selectGrade(String grade) {
        updateOptionsList();
        int resultIndex = cachedOptionsList.indexOf(grade);
        if (resultIndex >= 0) {
            return ElementsUtils.clickElementAndWait(webElementListDropDownOptions.get(resultIndex), 2000);
        }
        return false;
    }

    public boolean clickAddUnit() {
        boolean actionStatus = ElementsUtils.clickElementAndWait(webElementAddUnitButton, 3500);
        this.refresh();
        return actionStatus;
    }

    public boolean setUnitTitle(String text) {
        return ElementsUtils.setElementValue(webElementUnitTitleTextField, text);
    }

    public boolean setUnitNumber() {
        Random r = new Random();
        int randomNumber;
        do {
            randomNumber = r.nextInt(5000) + 1;
        } while (driver.findElements(By.cssSelector(".invalid-feedback>p")).size() > 0);

        return ElementsUtils.setElementValue(webElementUnitNumberTextField, String.valueOf(randomNumber));
    }

    public boolean setLessonNumber() {
        Random r = new Random();
        int randomNumber;
        do {
            randomNumber = r.nextInt(5000) + 1;
        } while (driver.findElements(By.cssSelector(".invalid-feedback>p")).size() > 0);

        return ElementsUtils.setElementValue(webElementLessonNumberTextField, String.valueOf(randomNumber));
    }

    public boolean setOverviewText(String text) {
        driver.switchTo().frame(driver.findElement(By.cssSelector("#mceu_38>iframe")));
        WebElement body = driver.findElement(By.tagName("body")); // then you find the body
        body.sendKeys(Keys.CONTROL + "a"); // send 'ctrl+a' to select all
        body.sendKeys(text);
        driver.switchTo().defaultContent();
        return true;
    }

    public boolean clickOnSave() {
        boolean actionStatus =  ElementsUtils.clickElementAndWait(webElementSaveButton, 3000);
        this.refresh();
        return actionStatus;
    }

    public void updateUnitsList() {
        cachedUnitsList = ElementsUtils.getElementsListText(webElementListUnitsList);
    }

    public boolean editUnit(String unitTitle) {
        updateUnitsList();
        int resultIndex = cachedUnitsList.indexOf(unitTitle);

        if (resultIndex >= 0) {
            return ElementsUtils.clickElementAndWait(webElementListEditIcon.get(resultIndex), 2000);
        }
        return false;
    }

    public boolean clickOnUnit(String unitTitle) {
        updateUnitsList();

        int resultIndex = -1;
        for (int i = 0; i < cachedUnitsList.size(); i++) {
            if (cachedUnitsList.get(i).contains(unitTitle)) {
                resultIndex = i;
                break;
            }
        }

        if (resultIndex >= 0) {
            boolean actionStatus =  ElementsUtils.clickElementAndWait(webElementListUnitsList.get(resultIndex), 2000);
            this.refresh();
            return actionStatus;
        }
        return false;
    }

    public boolean clickOnAddLesson() {
        MouseUtils.scrollIntoView(webElementLessonsTitle);
        try {
            webElementAddLessonButton.click();
            Thread.sleep(3000);
        } catch (InterruptedException e) {}
        return true;
    }

    public boolean setLessonTitle(String title) {
        return ElementsUtils.setElementValue(webElementLessonTitleTextField, title);
    }

    public boolean menteeOnlyLessonChoice(boolean value) {
        if(value) {
            return ElementsUtils.clickElementAndWait(webElementListYesNoMenteeOnly.get(0), 2000);
        } else {
            return ElementsUtils.clickElementAndWait(webElementListYesNoMenteeOnly.get(1), 2000);
        }
    }
}
