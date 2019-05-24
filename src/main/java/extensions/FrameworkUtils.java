package extensions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class FrameworkUtils {
    /**
     * Get the driver where the given element was created.
     *
     * @param element - WebElement object;
     * @return driver - the webDriver object (browser) where the given elements was initialised;
     */
    public static WebDriver getDriver(WebElement element) {
        WebElement myElement = ElementsUtils.getElementParent(element);
        return ((WrapsDriver) myElement).getWrappedDriver();
    }
    /**
     * Wait max 5 seconds for the content to be displayed and return the webElement for it;
     *
     * @param driver - current driver
     * @return webElement - the content webElement;
     */
    public static WebElement getContentElement(WebDriver driver, By contentLocator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 5);
            WebElement contentElement = wait.until(ExpectedConditions.visibilityOfElementLocated(contentLocator));
            Assert.assertNotNull(contentElement, "The contentElement cannot be found on page!");
            return contentElement;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Remove all new line symbols and the white spaces from start and end of a given string;
     *
     * @param stringInput - the string to be processed;
     * @return String - the resulted string after removal of white spaces;
     */
    public static String textRemoveWhiteSpaces(String stringInput) {
        boolean exitLoop = false;
        stringInput = stringInput.replaceAll("\r\n", " ");
        stringInput = stringInput.replaceAll("\r", " ");
        stringInput = stringInput.replaceAll("\n", " ");

        while (!exitLoop) {
            exitLoop = true;
            if (stringInput.startsWith(" ")) {
                stringInput = stringInput.substring(1);
                exitLoop = false;
            }
            if (stringInput.endsWith(" ")) {
                stringInput = stringInput.substring(0, stringInput.length() - 1);
                exitLoop = false;
            }
        }
        return stringInput;
    }
}
