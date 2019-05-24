package pageObjects;

import extensions.ElementsUtils;
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

public class TasksAndAnnouncementsPage {

    private WebDriver driver;

    @FindBy(css = "div.content>.imentor-btn-primary.blue")
    private WebElement webElementScheduleNewAnnouncement;

    @FindBy(css = "div.col-lg-7>button:first-of-type")
    private WebElement webElementMyAnnouncement;

    @FindBy(css = "div.col-lg-7>button:nth-of-type(2)")
    private WebElement webElementAllAnnouncement;

    @FindBy(css = ".table .scheduled-announcements-table-head")
    private WebElement webElementTableHeader;

    @FindBy(css = "tbody>tr>td:first-of-type")
    private List<WebElement> webElementListAnnouncementTitles;

    @FindBy(css = "td>i")
    private List<WebElement> webElementListManageIcons;

    private List<String> cachedAnnouncementTitlesList = new ArrayList<>();

    /**
     * Class constructor;
     *
     * @param driver
     */
    public TasksAndAnnouncementsPage(WebDriver driver) {
        WaitUtils.waitForPageProcessing(4000);
        this.driver = driver;

        WebElement contentElement = driver.findElement(By.cssSelector("div.tasks-and-announcements-page"));
        PageFactory.initElements(new DefaultElementLocatorFactory(contentElement), this);
    }

    public void updateAnnouncementList() {
        cachedAnnouncementTitlesList = ElementsUtils.getElementsListText(webElementListAnnouncementTitles);
    }

    public int getAnnouncementIndex(String announcementTitle) {
        updateAnnouncementList();
        for (int i = 0; i < cachedAnnouncementTitlesList.size(); i++) {
            if (cachedAnnouncementTitlesList.get(i).equalsIgnoreCase(announcementTitle)) {
                return i;
            }
        }
        return -1;
    }

    public boolean manageAnnouncement(String announcementTitle) {
        int index = getAnnouncementIndex(announcementTitle);
        if (index >= 0) {
            MouseUtils.scrollIntoView(webElementTableHeader);
            return ElementsUtils.clickElementAndWait(webElementListManageIcons.get(index), 3000);
        }
        return false;
    }

    public boolean scheduleNewAnnouncement() {
        return ElementsUtils.clickElementAndWait(webElementScheduleNewAnnouncement, 3000);
    }
}
