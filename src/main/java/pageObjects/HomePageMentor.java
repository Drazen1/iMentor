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

public class HomePageMentor {

    private WebDriver driver;

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
    public HomePageMentor(WebDriver driver) {
        this.driver = driver;
        WaitUtils.waitForPageProcessing(3000);

        WebElement contentElement = FrameworkUtils.getContentElement(driver, By.cssSelector("div.home-page"));
        PageFactory.initElements(new DefaultElementLocatorFactory(contentElement), this);

        popUpDismiss();
    }

    /**
     * If the Questionnaire popUp is displayed, 'Remind me next time' button will be clicked;
     */
    public void popUpDismiss() {
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

    public String getNewMessageInfo(String lesson) {
        curriculumLessonsUpdate();
        if (cachedLessonsList.contains(lesson)) {
            int resultIndex = cachedLessonsList.indexOf(lesson);
            return ElementsUtils.getElementText(webElementNewMessageInfo.get(resultIndex));
        }
        return null;
    }

    public boolean clickLesson(String lesson) {
        curriculumLessonsUpdate();
        if (cachedLessonsList.contains(lesson)) {
            int index = cachedLessonsList.indexOf(lesson);
            return ElementsUtils.clickElementAndWait(webElementListLessons.get(index), 1500);
        }
        return false;
    }
}
