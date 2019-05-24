package extensions;

import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;


public class ElementsUtils {

    /**
     * Generic method to check if an element is displayed on the current screen view - by using selenium native support.
     *
     * @param element - Selenium WebElement object;
     * @return true | false - base on the check if the element is displayed or not on the current view;
     */
    public static boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Generic method to get the webElement defined by an By locator, using the driver as parent element;
     *
     * @param webElement - Selenium WebElement;
     * @param by         - the locator input for the element that need to be check specified using the BY
     *                   type, eg. by.cssSelector("element css locator");
     * @return null | WebElement object;
     */
    public static WebElement getElementBy(WebElement webElement, By by) {
        try {
            return webElement.findElement(by);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * Generic method to get the specified attribute from an given element.
     *
     * @param element   - Selenium WebElement object;
     * @param attribute - the attribute name;
     * @return String value | null - base of the success of the get action;
     */
    public static String getElementAttribute(WebElement element, String attribute) {
        try {
            return element.getAttribute(attribute);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * Generic method to insert a given string in to an input field;
     *
     * @param element - Selenium WebElement object;
     * @param value   - the string value that need to be input;
     * @return true | false - base on the success of the input action;
     */
    public static boolean setElementValue(WebElement element, String value) {
        try {
            element.sendKeys("");
            element.clear();
            element.sendKeys(value);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * @param element
     * @param value
     * @param milliseconds
     * @return
     */
    public static boolean setElementValueAndWait(WebElement element, String value, int milliseconds) {
        try {
            element.sendKeys("");
            element.clear();
            element.sendKeys(value);
            Thread.sleep(milliseconds);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Generic method to insert a given string in to an input field and press Enter key when is done.
     *
     * @param element - Selenium WebElement object;
     * @param value   - the String value that need to be input;
     * @return true | false - base on the success of the input action;
     */
    public static boolean setElementValueAndSubmit(WebElement element, String value) {
        try {
            element.clear();
            element.sendKeys(value);
            element.submit();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Generic method to get the text attribute from a given element.
     *
     * @param element - Selenium WebElement object;
     * @return String value | null - base of the success of the get action;
     */
    public static String getElementText(WebElement element) {
        try {
            return element.getText();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * Generic method to get click on an element.
     *
     * @param element - the Selenium WebElement object that is needed to interact with;
     * @return true | false - base on success of the click action;
     */
    public static boolean clickElement(WebElement element) {
        try {
            if (WaitUtils.waitForElementTo(element, "visible", 3)) {
                if(!ElementsUtils.isElementInViewPortJS(element)){
                    MouseUtils.scrollIntoView(element);
                }
                element.click();
                return true;
            } else
                return false;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Generic method to get click on an element and wait for the given time until make the next action;
     *
     * @param element      - the Selenium WebElement object that is needed to interact with;
     * @param milliseconds - the time to wait until to return the result;
     * @return true | false - base on success of the click action;
     */
    public static boolean clickElementAndWait(WebElement element, int milliseconds) {
        try {
            if (WaitUtils.waitForElementTo(element, "visible", 5)) {
                WebDriver driver = FrameworkUtils.getDriver(element);
                if(!ElementsUtils.isElementInViewPortJS(element)){
                    MouseUtils.scrollIntoView(element);
                }
                element.click();
                Thread.sleep(milliseconds);
                WaitUtils.waitForAjax(driver);
                return true;
            } else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Generic method to check if an element is in the current view port of the browser;
     *
     * @param element - Selenium WebElement object;
     * @return true | false - base on the check if the element is in the browser view port or not on the current view;
     */
    public static boolean isElementInViewPortJS(WebElement element) {
        WebDriver driver = FrameworkUtils.getDriver(element);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        return (boolean) js.executeScript(
                "isElementInViewport = function (el) {" +
                        "if (typeof jQuery !== 'undefined' && el instanceof jQuery) el = el[0];" +

                        "var rect = el.getBoundingClientRect();"+
                        "var windowHeight = (window.innerHeight || document.documentElement.clientHeight);"+
                        "var windowWidth = (window.innerWidth || document.documentElement.clientWidth);"+

                        "return ( (rect.left >= 0) && (rect.top >= 0) && ((rect.left + rect.width) <= windowWidth) && ((rect.top + rect.height) <= windowHeight) ); "+
                        "};"+
                        "return isElementInViewport(arguments[0]);", element);
    }


    /**
     * Generic method that return the parent WebElement of the given element
     *
     * @param element - the Selenium WebElement object that is needed to interact with;
     * @return WebElement - the parent WebElement object of the input element;
     */
    public static WebElement getElementParent(WebElement element) {
        return element.findElement(By.xpath(".."));
    }

    /**
     * Get the text from for each element from a List of elements;
     *
     * @param elementsList List WebElement - an List of Selenium WebElements;
     * @return List of Strings - with the text() from each element;
     */
    public static List<String> getElementsListText(List<WebElement> elementsList) {
        List<String> tmpList = new ArrayList<>();
        for (WebElement element : elementsList) {
            String tmpSting = FrameworkUtils.textRemoveWhiteSpaces(ElementsUtils.getElementText(element));
            tmpList.add(tmpSting);
        }
        return tmpList;
    }



}
