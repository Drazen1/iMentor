package extensions;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MouseUtils {

    /**
     * Scroll to an element from a given container using Java script;
     *
     * @param element - the Selenium WebElement that we need to scroll to;
     * @return true | false - base on the action success
     */
    public static void scrollIntoView(WebElement element) {
        WebDriver driver = FrameworkUtils.getDriver(element);
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
