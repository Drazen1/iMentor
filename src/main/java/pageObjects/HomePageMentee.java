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

public class HomePageMentee {

    private WebDriver driver;

    @FindBy(css = ".selected-match.col-md-12.ng-star-inserted")
    private WebElement webElementCurriculumMessagesTitle;

    @FindBy(css = "tbody>tr.notification>td:nth-child(2)")
    private List<WebElement> webElementListLessons;

    @FindBy(css = "tbody>tr.notification>td>span")
    private List<WebElement> webElementNewMessageInfo;

    private List<String> cachedLessonsList = new ArrayList<>();

    /**
     * Class constructor;
     *
     * @param driver
     */
    public HomePageMentee(WebDriver driver) {
        this.driver = driver;
        WaitUtils.waitForPageProcessing(3000);
        WebElement contentElement = FrameworkUtils.getContentElement(driver, By.cssSelector("div.home-page"));
        PageFactory.initElements(new DefaultElementLocatorFactory(contentElement), this);

        popUpDismiss();
    }

    /**
     * If rating popUp is displayed click on the 4;
     * If the Questionnaire popUp is displayed, 'Remind me next time' button will be clicked;
     */
    public void popUpDismiss() {
        if (driver.findElements(By.cssSelector("li.rating-item")).size() > 0) {
            ElementsUtils.clickElementAndWait(driver.findElement(By.cssSelector("li.rating-item:nth-child(4)")), 2000);
        }

        if (driver.findElements(By.cssSelector("div.task")).size() > 0) {
            List<WebElement> webElementsRemindMeButtons = driver.findElements(By.cssSelector(".action-buttons .btn.imentor-btn-secondary"));
            for(int i = 0; i < webElementsRemindMeButtons.size(); i++) {
                ElementsUtils.clickElementAndWait(webElementsRemindMeButtons.get(i), 1500);
            }
        }
    }

    public void curriculumLessonsUpdate() {
        cachedLessonsList = ElementsUtils.getElementsListText(webElementListLessons);
    }

    public boolean clickLesson(String lesson) {
        curriculumLessonsUpdate();
        if (cachedLessonsList.contains(lesson)) {
            int index = cachedLessonsList.indexOf(lesson);
            MouseUtils.scrollIntoView(webElementCurriculumMessagesTitle);
            return ElementsUtils.clickElementAndWait(webElementListLessons.get(index), 1500);
        }
        return false;
    }

    public String getNewMessageInfo(String lesson) {
        curriculumLessonsUpdate();
        if (cachedLessonsList.contains(lesson)) {
            int resultIndex = cachedLessonsList.indexOf(lesson);
            return ElementsUtils.getElementText(webElementNewMessageInfo.get(resultIndex));
        }
        return null;
    }
}
