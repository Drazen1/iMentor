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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarIM {

    private WebDriver driver;

    @FindBy(css = "div[style*='left'] .icon-mydpright")
    private WebElement webElementNextMonthIcon;

    @FindBy(css = ".caltable td.currmonth:not(.disabled)")
    private List<WebElement> webElementListCurrentMonthActiveDays;

    private List<String> cachedActiveDaysList = new ArrayList<>();


    /**
     * Class constructor;
     *
     * @param driver
     */
    public CalendarIM(WebDriver driver) {
        WaitUtils.waitForPageProcessing(2000);
        this.driver = driver;

        refresh();
    }

    public void refresh() {
        WebElement contentElement = FrameworkUtils.getContentElement(driver, By.cssSelector("div.selector"));
        PageFactory.initElements(new DefaultElementLocatorFactory(contentElement), this);
    }

    public void updateActiveDays() {
        cachedActiveDaysList = ElementsUtils.getElementsListText(webElementListCurrentMonthActiveDays);
    }

    // +1 because of current today and time
    public boolean selectActiveDayByIndex(int index) {
        updateActiveDays();
        if (cachedActiveDaysList.size() > (index + 1) ) {
            return ElementsUtils.clickElementAndWait(webElementListCurrentMonthActiveDays.get(index + 1), 1500);
        } else {
            if (ElementsUtils.clickElementAndWait(webElementNextMonthIcon, 1500)) {
                if (cachedActiveDaysList.size() > (index + 1)) {
                    return ElementsUtils.clickElementAndWait(webElementListCurrentMonthActiveDays.get(index + 1), 1500);
                }

            }
        }
        return false;
    }

    public boolean selectDay() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        String nextDay = Integer.toString(dayOfMonth + 1);

        updateActiveDays();
        if (cachedActiveDaysList.contains(nextDay)) {
            return ElementsUtils.clickElementAndWait(webElementListCurrentMonthActiveDays.get(cachedActiveDaysList.indexOf(nextDay)), 1500);
        } else {
            if (ElementsUtils.clickElementAndWait(webElementNextMonthIcon, 1500)) {
                if (cachedActiveDaysList.contains(nextDay)) {
                    return ElementsUtils.clickElementAndWait(webElementListCurrentMonthActiveDays.get(cachedActiveDaysList.indexOf(nextDay)), 1500);
                }
            }
        }
        return false;
    }

}
