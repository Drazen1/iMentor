package extensions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Date;

public class WaitUtils {

    /**
     * Wait for element based on web element to reach state visible / invisible with specific timeout
     *
     * @param el      - webElement to interact with;
     * @param option  - element state: visible or invisible
     * @param timeOut - evaluation action timeOut in seconds;
     * @return true | false - base on the evaluation action success;
     */
    public static boolean waitForElementTo(WebElement el, String option, int timeOut) {
        boolean result = false;
        int ticInterval = 1000; // milliseconds
        Date date = new Date();
        switch (option) {
            case "visible":
                while (!result && (new Date().getTime() - date.getTime() <= timeOut * 1000)) {
                    try {
                        if (el.isDisplayed())
                            result = true;
                        else
                            Thread.sleep(ticInterval);
                    } catch (Exception e) {
                        // do nothing
                    }
                }
                break;

            case "invisible":
                while (!result && (new Date().getTime() - date.getTime() <= timeOut * 1000)) {
                    try {
                        if (!el.isDisplayed())
                            result = true;
                        else
                            Thread.sleep(ticInterval);
                    } catch (Exception e) {
                        result = true;
                    }
                }
                break;

            case "viewport":
                while (!result && (new Date().getTime() - date.getTime() <= timeOut * 1000)) {
                    try {
                        if (ElementsUtils.isElementInViewPortJS(el))
                            result = true;
                        else
                            Thread.sleep(ticInterval);
                    } catch (Exception e) {
                        // do noting
                    }
                }
                break;
        }
        return result;
    }

    /**
     * Simple thread sleep method
     *
     * @param milliseconds - sleep value in milliseconds
     */
    public static void waitForPageProcessing(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * wait for ajax calls to be performed
     *
     * @param driver webDriver when the evaluation will occur;
     */
    public static Boolean waitForAjax(WebDriver driver) {
        boolean readyState = false;
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int i = 0;
        while (!readyState && i < 10) {
            try {
                readyState = js.executeScript("return document.readyState").equals("complete");
                if (!readyState) Thread.sleep(250);
                i++;
            } catch (Exception e) {
                // Do noting
            }
        }
        return readyState;
    }

}
