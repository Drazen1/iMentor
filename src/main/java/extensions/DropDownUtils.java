package extensions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;


/**
 * Set of generic helper methods that interact with drop down menus;
 */
public class DropDownUtils {

    /**
     * Get the Text(label) of each Option from a given drop-down menu.
     *
     * @param menuWebElement - the Selenium WebElement object that represent the Menu;
     * @return List of Strings | null - an list with all testConfig, or NUll in case of an error.
     */
    public static List<String> getMenuOptions(WebElement menuWebElement) {
        try {
            List<String> stringList_selectOptions = new ArrayList<>();
            Select selectBox = new Select(menuWebElement);

            List<WebElement> webElements_selectOptions = selectBox.getOptions();

            for (WebElement option : webElements_selectOptions) {
                stringList_selectOptions.add(FrameworkUtils.textRemoveWhiteSpaces(option.getText()));
            }
            return stringList_selectOptions;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get the Text(label) of each Option from a given drop-down menu.
     *
     * @param menuWebElement - the Selenium WebElement object that represent the Menu;
     * @return List of Strings | null - an list with all testConfig, or NUll in case of an error.
     */
    public static List<String> getMenuOptionsActiveOnly(WebElement menuWebElement) {
        try {
            List<String> stringList_selectOptions = new ArrayList<>();
            Select selectBox = new Select(menuWebElement);

            List<WebElement> webElements_selectOptions = selectBox.getOptions();

            for (WebElement option : webElements_selectOptions) {
                if (option.getAttribute("disabled") == null)
                    stringList_selectOptions.add(FrameworkUtils.textRemoveWhiteSpaces(option.getText()));
            }
            return stringList_selectOptions;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get the Text(label) of each Option from a given drop-down menu.
     *
     * @param menuWebElement - the Selenium WebElement object that represent the Menu;
     * @return List of Strings | null - an list with all testConfig, or NUll in case of an error.
     */
    public static List<String> getMenuOptionsInactiveOnly(WebElement menuWebElement) {
        try {
            List<String> stringList_selectOptions = new ArrayList<>();
            Select selectBox = new Select(menuWebElement);

            List<WebElement> webElements_selectOptions = selectBox.getOptions();

            for (WebElement option : webElements_selectOptions) {
                if (option.getAttribute("disabled") != null)
                    stringList_selectOptions.add(FrameworkUtils.textRemoveWhiteSpaces(option.getText()));
            }
            return stringList_selectOptions;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * Get the Text(label) of all selected Options from a given drop-down Menu.
     *
     * @param menuWebElement - the Selenium WebElement object that represent the Menu;
     * @return ist of Strings | null - an list with all selected testConfig, or NUll in case of an error.
     */
    public static List<String> getMenuSelectedOptions(WebElement menuWebElement) {
        try {
            List<String> stringList_selectedOptions = new ArrayList<>();
            Select selectBox = new Select(menuWebElement);

            List<WebElement> webElements_selectedOptions = selectBox.getAllSelectedOptions();
            for (WebElement option : webElements_selectedOptions) {
                stringList_selectedOptions.add(FrameworkUtils.textRemoveWhiteSpaces(option.getText()));
            }
            return stringList_selectedOptions;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Generic method that perform an select option action from an drop-down Menu base on an given
     * input criteria.
     *
     * @param menuWebElement   - the Selenium WebElement object that represent the Menu;
     * @param selectByCriteria - the select criteria could be one of the strings: Index, Value and Text;
     * @param selectOption     - base on the select criteria this will identify the option that need to be selected;
     * @return true | false - base on the success of the select option action;
     */
    public static boolean selectMenuOptionByCriteria(WebElement menuWebElement, String selectByCriteria, String selectOption) {
        try {
            boolean actionResult = false;
            Select selectBox = new Select(menuWebElement);

            switch (selectByCriteria.toLowerCase()) {
                case "index":
                    selectBox.selectByIndex(Integer.parseInt(selectOption));
                    actionResult = true;
                    break;
                case "value":
                    selectBox.selectByValue(selectOption);
                    actionResult = true;
                    break;
                case "text":
                    selectBox.selectByVisibleText(selectOption);
                    actionResult = true;
                    break;
            }
            return actionResult;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Generic method that perform an select option action from an drop-down Menu by hovering the select option and perform
     * click action on it.
     *
     * @param menuWebElement - the Selenium WebElement object that represent the Menu;
     * @param selectOption   - base on the select criteria this will identify the option that need to be selected;
     * @return true | false - base on the success of the select option action;
     */
    public static boolean selectMenuOptionByHoverAndClick(WebElement menuWebElement, WebElement selectOption) {
        WebDriver driver = FrameworkUtils.getDriver(menuWebElement);

        boolean actionStatus = false;
        Actions action = new Actions(driver);
        // the moveByOffset action is required for IE8 browsers to trigger the display of the handOver
        action.moveToElement(menuWebElement).moveByOffset(1, 0).perform();
        // action.moveToElement(menuWebElement).perform();
        if (WaitUtils.waitForElementTo(selectOption, "visible", 5)) {
            action.moveToElement(selectOption).click().perform();
            actionStatus = true;
        }
        return actionStatus;
    }


    /**
     * Select an given option from a drop drown menu by specifying his value;
     *
     * @param menuWebElement - WebElement object that represent the dropDown menu;
     * @param selectOption   - the Sting value of the option to be selected
     * @return true | false - base on the success of the select option action;
     */
    public static boolean selectMenuOptionByValue(WebElement menuWebElement, String selectOption) {
        boolean actionStatus;

        By optionLocator = By.xpath("//option[@value='" + selectOption + "']");
        ElementsUtils.clickElement(menuWebElement);
        WebElement optionElement = ElementsUtils.getElementBy(menuWebElement, optionLocator);
        actionStatus = ElementsUtils.clickElement(optionElement);
        return actionStatus;
    }

}
