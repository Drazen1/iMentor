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

public class NotesWindow {

    private WebDriver driver;

    @FindBy(css = ".fa.fa-close.fa-2x")
    private WebElement webElementCloseButton;

    @FindBy(css = "button.imentor-btn-primary:nth-child(1)")
    private WebElement webElementIndividualizedButton;

    //@FindBy(css = "button.imentor-btn-primary:nth-child(2)")
    @FindBy(css = ".page-content-section button.imentor-btn-primary:nth-child(2)")
    private WebElement webElementQuickButton;

    @FindBy(css = "imentor-schools.ng-tns-c4-0 div.ng-star-inserted imentor-multi-select-dropdown:nth-child(1) div.multi-select-container > button.dropdown-toggle-button")
    private WebElement webElementSelectSchoolDropDownButton;

    //'user' should be typed and dropdown will be displayed
    @FindBy(css = "input.search")
    private WebElement webElementForTextField;

    @FindBy(css = ".autocomplete-list-container li")
    private List<WebElement> webElementListUsersDropDownList;

    @FindBy(css = "imentor-add-note.ng-tns-c4-0.ng-star-inserted:nth-child(5) div.notes-window.ng-tns-c4-0.ng-trigger.ng-trigger-slideInOut.ng-star-inserted imentor-templates.ng-tns-c4-0.ng-star-inserted:nth-child(5) div.page-content-section.ng-star-inserted imentor-multi-select-dropdown:nth-child(1) div.multi-select-container > button.dropdown-toggle-button")
    private WebElement webElementTemplateDropDownButton;

    @FindBy(css = "div.page-content-section.ng-star-inserted div.template-field.ng-star-inserted:nth-child(2) imentor-dropdown-question-type.ng-star-inserted imentor-multi-select-dropdown.ng-star-inserted div.multi-select-container > button.dropdown-toggle-button")
    private WebElement webElementDropDownQuestionButton;

    @FindBy(css = "div.template-field.ng-star-inserted:nth-child(3) imentor-open-question-type.ng-star-inserted > textarea.template-field-content")
    private WebElement webElementQuestionTextArea;

    @FindBy(xpath = "//p[contains(text(),'Category')]")
    private WebElement webElementCategoryLabel;

    @FindBy(css = ".fa.fa-square-o")
    private List<WebElement> webElementListCategoriesCheckBoxesIcons;

    @FindBy(xpath = "//p[contains(text(),'Subject:')]")
    private WebElement webElementSubjectLabel;

    @FindBy(css = "i.fa.fa-genderless")
    private List<WebElement> webElementListSubjectCheckBoxesIcons;

    @FindBy(css = ".imentor-btn-primary.green")
    private WebElement webElementSaveButton;

    @FindBy(css = "button.imentor-btn-secondary")
    private WebElement webElementCancelButton;

    @FindBy(css = "div.multi-select-dropdown-container.show li")
    private List<WebElement> webElementListDropDownOptions;

    private List<String> cachedSchoolsList = new ArrayList<>();
    private List<String> cachedUsersList = new ArrayList<>();


    /**
     * Class constructor;
     * @param driver
     */
    public NotesWindow(WebDriver driver) {
        WaitUtils.waitForPageProcessing(2000);
        this.driver = driver;

        WebElement contentElement = FrameworkUtils.getContentElement(driver, By.cssSelector("div.notes-window"));
        PageFactory.initElements(new DefaultElementLocatorFactory(contentElement), this);
    }

    public boolean setText(String element, String text) {
        switch (element.toLowerCase()) {
            case "for":
                return ElementsUtils.setElementValueAndWait(webElementForTextField, text, 2000);
            case "question":
                return ElementsUtils.setElementValueAndWait(webElementQuestionTextArea, text, 2000);
            default:
                System.out.println("[NotesWindow.setText()] The given element name: '" + element + "' is not valid!");
                return false;
        }
    }

    public boolean clickElement(String element) {
        switch (element.toLowerCase()) {
            case "close":
                return ElementsUtils.clickElementAndWait(webElementCloseButton, 2000);
            case "individualized":
                return ElementsUtils.clickElementAndWait(webElementIndividualizedButton, 2000);
            case "quick":
                return ElementsUtils.clickElementAndWait(webElementQuickButton, 2000);
            case "selectschool":
                return ElementsUtils.clickElementAndWait(webElementSelectSchoolDropDownButton, 2000);
            case "templatedropdown":
                return ElementsUtils.clickElementAndWait(webElementTemplateDropDownButton, 2000);
            case "questiondropdown":
                return ElementsUtils.clickElementAndWait(webElementDropDownQuestionButton, 2000);
            case "save":
                return ElementsUtils.clickElementAndWait(webElementSaveButton, 2000);
            case "cancel":
                return ElementsUtils.clickElementAndWait(webElementCancelButton, 2000);
            default:
                System.out.println("[NotesWindow.clickElement()] The given element name: '" + element + "' is not valid!");
                return false;
        }
    }

    public boolean isDisplayed(String element) {
        switch (element.toLowerCase()) {
            case "individualized":
                return ElementsUtils.isElementDisplayed(webElementIndividualizedButton);
            case "quick":
                return ElementsUtils.isElementDisplayed(webElementQuickButton);
            case "selectschool":
                return ElementsUtils.isElementDisplayed(webElementSelectSchoolDropDownButton);
            case "templatedropdown":
                return ElementsUtils.isElementDisplayed(webElementTemplateDropDownButton);
            case "questiondropdown":
                return ElementsUtils.isElementDisplayed(webElementDropDownQuestionButton);
            case "save":
                return ElementsUtils.isElementDisplayed(webElementSaveButton);
            case "cancel":
                return ElementsUtils.isElementDisplayed(webElementCancelButton);
            default:
                System.out.println("[NotesWindow.clickElement()] The given element name: '" + element + "' is not valid!");
                return false;
        }
    }

    public void updateSchoolsList() {
        cachedSchoolsList = ElementsUtils.getElementsListText(webElementListDropDownOptions);
    }

    public boolean selectOption(String schoolName) {
        if (!cachedSchoolsList.contains(schoolName)) {
            updateSchoolsList();
        }

        int resultIndex = cachedSchoolsList.indexOf(schoolName);
        if (resultIndex >= 0) {
            return ElementsUtils.clickElementAndWait(webElementListDropDownOptions.get(resultIndex), 2500);
        }
        return false;
    }

    public void updateUsersList() {
        cachedUsersList = ElementsUtils.getElementsListText(webElementListUsersDropDownList);
    }

    public boolean validateUsersList() {
        updateUsersList();
        if (cachedUsersList.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean selectFirstUser() {
        return ElementsUtils.clickElementAndWait(webElementListUsersDropDownList.get(0), 3000);
    }

    public boolean verifyCategorySection() {
        boolean status1 = ElementsUtils.isElementDisplayed(webElementCategoryLabel) && ElementsUtils.getElementText(webElementCategoryLabel).equalsIgnoreCase("category");
        boolean status2 = webElementListCategoriesCheckBoxesIcons.size() > 0;
        return status1 && status2;
    }

    public boolean verifySubjectSection() {
        boolean status1 =  ElementsUtils.isElementDisplayed(webElementSubjectLabel) && ElementsUtils.getElementText(webElementSubjectLabel).toLowerCase().contains("subject");
        boolean status2 = webElementListSubjectCheckBoxesIcons.size() > 0;
        return status1 && status2;
    }

    public boolean selectSubjectOption(int index) {
        return ElementsUtils.clickElementAndWait(webElementListSubjectCheckBoxesIcons.get(index), 2000);
    }

    public boolean selectCategoryOption(int index) {
        return ElementsUtils.clickElementAndWait(webElementListCategoriesCheckBoxesIcons.get(index), 2000);
    }
}
