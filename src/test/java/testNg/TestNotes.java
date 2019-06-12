package testNg;

import extensions.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.*;

public class TestNotes extends _TestNGSetup {

    //@BeforeTest
    @BeforeMethod
    public void login() {
        driver.navigate().to(testConfig.testURL);
        WaitUtils.waitForPageProcessing(testConfig.testDelayBeforePageProcessing);

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.setEmail(testConfig.testUsername), "[1] Failed to set email address!");
        Assert.assertTrue(loginPage.setPassword(testConfig.testPassword), "[2] Failed to set password!");
        Assert.assertTrue(loginPage.signIn(), "[3] The click action on the 'Sign in' button failed to execute!");

        WaitUtils.waitForPageProcessing(2 * testConfig.testDelayBeforePageProcessing);
        String selectAll = Keys.chord(Keys.ALT, "q");
        WebElement homeContainer = driver.findElement(By.cssSelector("div.container"));
        Assert.assertNotNull(homeContainer);

        Actions builder = new Actions(driver);
        Action seriesOfActions = builder.moveToElement(homeContainer).sendKeys(Keys.ALT, "q").build();
        seriesOfActions.perform();
    }

    @Test(testName = "notesGeneralCheck"
            , enabled = true
            , description = ""
    )
    public void notesGeneralCheck() {
        WaitUtils.waitForPageProcessing(2 * testConfig.testDelayBeforePageProcessing);
        NotesWindow notesWindow = new NotesWindow(driver);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(notesWindow.isDisplayed("individualized"), "The 'Individualized' button is not displayed!");
        softAssert.assertTrue(notesWindow.isDisplayed("quick"), "The 'Quick' button is not displayed!");
        softAssert.assertAll();
    }

    @DataProvider
    public Object[][] saveCancelCheck() {
        return new Object[][] {
                {"save"},
                {"cancel"}
        };
    }

    @Test(testName = "addNoteIndividualized"
            , enabled = true
            , dataProvider = "saveCancelCheck"
            , description = ""
    )
    public void addNoteIndividualized(String saveOrCancelOption) {
        WaitUtils.waitForPageProcessing(2 * testConfig.testDelayBeforePageProcessing);
        NotesWindow notesWindow = new NotesWindow(driver);

        Assert.assertTrue(notesWindow.clickElement("individualized"), "The click action on the 'Individualized' button failed to execute!");
        Assert.assertTrue(notesWindow.isDisplayed("selectschool"), "The 'Select School' drop down button is not displayed!");

        Assert.assertTrue(notesWindow.clickElement("selectschool"), "The click action on the 'Select School' toggle drop down button failed to execute!");
        Assert.assertTrue(notesWindow.selectOption("Arroyo High School"), "Failed to select 'Arroyo High School' high school!");

        Assert.assertTrue(notesWindow.setText("for", "user"), "The attempt to set the 'user' text in 'for' texfield failed to execute!");
        Assert.assertTrue(notesWindow.validateUsersList(), "The users list is empty!");
        Assert.assertTrue(notesWindow.selectFirstUser(), "Failed to select the first user from the list!");

        Assert.assertTrue(notesWindow.clickElement("templatedropdown"), "The click action on the 'Select Template' dropdown button failed to execute!");
        Assert.assertTrue(notesWindow.selectOption("General"), "Failed to select 'General' from Template drop down!");

        Assert.assertTrue(notesWindow.clickElement("questiondropdown"), "The click aciton on the Question drop down button failed to execute!");
        Assert.assertTrue(notesWindow.selectOption("TCC1"), "Failed to select 'TCC1' option from question drop down!");
        Assert.assertTrue(notesWindow.setText("question", "test"), "Failed to set the text in question type text filed!");

        Assert.assertTrue(notesWindow.verifyCategorySection(), "Category verification failed!");
        Assert.assertTrue(notesWindow.isDisplayed("save"), "Save button is not displayed!");
        Assert.assertTrue(notesWindow.isDisplayed("cancel"), "Cancel button is not displayed!");
        Assert.assertTrue(notesWindow.clickElement(saveOrCancelOption), "The click action on the '" + saveOrCancelOption + "' button failed to execute!");
    }

    @Test(testName = "addNoteQuick"
            , enabled = true
            , description = ""
    )
    public void addNoteQuick() {
        WaitUtils.waitForPageProcessing(2 * testConfig.testDelayBeforePageProcessing);
        NotesWindow notesWindow = new NotesWindow(driver);

        Assert.assertTrue(notesWindow.clickElement("quick"), "The click action on the 'Quick' button failed to execute!");
        Assert.assertTrue(notesWindow.isDisplayed("selectschool"), "The 'Select School' drop down button is not displayed!");
        Assert.assertTrue(notesWindow.clickElement("selectschool"), "The click action on the 'Select School' toggle drop down button failed to execute!");
        Assert.assertTrue(notesWindow.selectOption("Arroyo High School"), "Failed to select 'Arroyo High School' high school!");

        Assert.assertTrue(notesWindow.setText("for", "user"), "The attempt to set the 'user' text in 'for' texfield failed to execute!");
        Assert.assertTrue(notesWindow.validateUsersList(), "The users list is empty!");
        Assert.assertTrue(notesWindow.selectFirstUser(), "Failed to select the first user from the list!");

        Assert.assertTrue(notesWindow.verifySubjectSection(), "Subject verification failed!");
        Assert.assertTrue(notesWindow.verifyCategorySection(), "Category verification failed!");
    }


    @Test(testName = "deleteQuickNote"
            , enabled = true
            , description = ""
    )
    public void deleteQuickNote() {
        driver.navigate().to("https://platformtraining.imentorstaging.org/webapp/#/note-tracker");
        WaitUtils.waitForPageProcessing(testConfig.testDelayBeforePageProcessing);

        NoteTracker noteTracker = new NoteTracker(driver);
        Assert.assertTrue(noteTracker.expandPartnerDropDown(), "Failed to expand Partner drop down!");
        Assert.assertTrue(noteTracker.selectPartner("Arroyo High 19 Class 1"), "Faile to select 'Arroyo High 19 Class 1'!");
        Assert.assertTrue(noteTracker.expandCollapseDropDown(), "Failed to caollapse drop down!");

        Assert.assertTrue(noteTracker.selectStartDate(), "The click action on the Start Date toggle button failed to execute!");

        CalendarIM calendarIM = new CalendarIM(driver);

        Assert.assertTrue(calendarIM.selectPreviousMonth(), "The click action on the previous month icon failed to execute!");
        Assert.assertTrue(calendarIM.selectActiveDayByIndex(0), "Failed to select start date!");

        Assert.assertTrue(noteTracker.selectEndDate(), "The click action on the End Date toggle button failed to execute!");
        calendarIM.refresh();
        Assert.assertTrue(calendarIM.selectNextMonth(), "The click action on the next month icon failed to execute!");

        Assert.assertTrue(calendarIM.selectCurrentDay(), "Failed to select current day from End date calendar!");

        noteTracker.refresh();
        int initialPairNotesNumber = noteTracker.getPairNotesNumberFirstRecord();

        Assert.assertTrue(noteTracker.selectFirstMenteeMentorPair(), "The click action on the the firt check box Mentee Mentor option failed to execute!");
        Assert.assertTrue(noteTracker.selectQuickButton("Pairs"), "The click action on the 'Pairs' quick button failed to execute!");
        WaitUtils.waitForPageProcessing(testConfig.testDelayBeforePageProcessing);

        NotesWindow notesWindow = new NotesWindow(driver);
        Assert.assertNotNull(notesWindow, "Notes Window has not been loaded!");
        Assert.assertTrue(notesWindow.isDisplayed("selectschool"), "The 'Select School' drop down button is not displayed!");
        Assert.assertTrue(notesWindow.clickElement("selectschool"), "The click action on the 'Select School' toggle drop down button failed to execute!");
        Assert.assertTrue(notesWindow.selectOption("Arroyo High School"), "Failed to select 'Arroyo High School' high school!");

        Assert.assertTrue(notesWindow.selectSubjectOption(0), "Failed to select the first Subject option - Check In - In Person!");
        Assert.assertTrue(notesWindow.selectCategoryOption(0), "Failed to select the first Category option - Touch Point!");
        Assert.assertTrue(notesWindow.clickElement("save"), "The click action on the Save button failed to execute!");
        Assert.assertTrue(notesWindow.clickElement("close"), "The click action on the Close button failed to execute!");

        noteTracker.refresh();
        Assert.assertEquals(initialPairNotesNumber + 1, noteTracker.getPairNotesNumberFirstRecord(), "The new Note has not been aded!");

        Assert.assertTrue(noteTracker.clickNoteNumber(0, "pair"), "The click action on the the first Pair Note value is not executed!");

        ViewNotes viewNotes = new ViewNotes(driver);
        Assert.assertTrue(viewNotes.clickOnTrashIcon(0), "The click action on the first trash icon failed to execute!");

        ModalWindow modalWindow = new ModalWindow(driver);
        Assert.assertTrue(modalWindow.clickElement("delete"), "The click action on the Delete button failed to execute!");

        noteTracker.refresh();
        Assert.assertEquals(initialPairNotesNumber, noteTracker.getPairNotesNumberFirstRecord(), "The not has not been deleted!");
    }

    @DataProvider
    public Object[][] getCredentials() {
        return new Object[][]{
                {"super admin","testemails+user_100000@imentor.org", "test123"},
                {"member admin","testemails+user_151297@imentor.org", "test123"},
                {"enhanced member admin", "testemails+user_192977@imentor.org", "test123"}
        };
    }

    @Test(testName = "noteTrackerGeneralCheck"
            , enabled = true
            , dataProvider = "getCredentials"
            , description = ""
    )
    public void noteTrackerGeneralCheck(String role, String email, String password) {
        driver.navigate().to(testConfig.testURL);
        WaitUtils.waitForPageProcessing(testConfig.testDelayBeforePageProcessing);

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.setEmail(email), "[1] Failed to set email address!");
        Assert.assertTrue(loginPage.setPassword(password), "[2] Failed to set password!");
        Assert.assertTrue(loginPage.signIn(), "[3] The click action on the 'Sign in' button failed to execute!");

        driver.navigate().to("https://platformtraining.imentorstaging.org/webapp/#/note-tracker");
        WaitUtils.waitForPageProcessing(2 * testConfig.testDelayBeforePageProcessing);

        NoteTracker noteTracker = new NoteTracker(driver);
        Assert.assertTrue(noteTracker.isDisplayed("selectpartner"), "Select Partner drop down toggle button is not displayed!");

        Assert.assertTrue(noteTracker.expandPartnerDropDown(), "Failed to expand Partner drop down!");
        Assert.assertTrue(noteTracker.selectPartner("Arroyo High 19 Class 1"), "Faile to select 'Arroyo High 19 Class 1'!");
        Assert.assertTrue(noteTracker.expandCollapseDropDown(), "Failed to caollapse drop down!");

        Assert.assertTrue(noteTracker.selectStartDate(), "The click action on the Start Date toggle button failed to execute!");

        CalendarIM calendarIM = new CalendarIM(driver);

        Assert.assertTrue(calendarIM.selectPreviousMonth(), "The click action on the previous month icon failed to execute!");
        Assert.assertTrue(calendarIM.selectActiveDayByIndex(0), "Failed to select start date!");

        Assert.assertTrue(noteTracker.selectEndDate(), "The click action on the End Date toggle button failed to execute!");
        calendarIM.refresh();
        Assert.assertTrue(calendarIM.selectNextMonth(), "The click action on the next month icon failed to execute!");
        Assert.assertTrue(calendarIM.selectCurrentDay(), "Failed to select current day from End date calendar!");

        Assert.assertTrue(noteTracker.isDisplayed("diagram"), "View Chart is not displayed!");
        Assert.assertTrue(noteTracker.isDisplayed("diagramtitle"), "View Chart tile is not displayed!");
    }

    @Test(testName = "noteCatalogCheck"
            , enabled = true
            , description = ""
    )
    public void noteCatalogCheck() {
        driver.navigate().to("https://platformtraining.imentorstaging.org/webapp/#/note-catalog");
        WaitUtils.waitForPageProcessing(testConfig.testDelayBeforePageProcessing);

        NoteCatalog noteCatalog = new NoteCatalog(driver);
        Assert.assertNotNull(noteCatalog, "NoteCatolog object is null!");

        Assert.assertTrue(noteCatalog.selectStartDate(), "The click action on the Start Date toggle button failed to execute!");
        CalendarIM calendarIM = new CalendarIM(driver);

        Assert.assertTrue(calendarIM.selectPreviousMonth(), "The click action on the previous month icon failed to execute!");
        Assert.assertTrue(calendarIM.selectActiveDayByIndex(0), "Failed to select start date!");

        Assert.assertTrue(noteCatalog.selectEndDate(), "The click action on the End Date toggle button failed to execute!");
        calendarIM.refresh();
        Assert.assertTrue(calendarIM.selectNextMonth(), "The click action on the next month icon failed to execute!");
        Assert.assertTrue(calendarIM.selectCurrentDay(), "Failed to select current day from End date calendar!");

        Assert.assertTrue(noteCatalog.selectFilterOption("Individualized Templates"), "Failed to click on the 'Individualized Templates' drop down toggle button");
        Assert.assertTrue(noteCatalog.collapseDropDown("Individualized Templates"), "Failed to collapse 'Individualized Templates' drop down!");
        Assert.assertTrue(noteCatalog.selectDropDownOption("General"), "Failed to select 'General' drop down option!");
        Assert.assertTrue(noteCatalog.applyFilters(), "The click action on the 'Apply Filters' failed to execute!");
        Assert.assertTrue(noteCatalog.isCatalogTableDisplayed(), "The catalog table is not displayed!");
    }

}
