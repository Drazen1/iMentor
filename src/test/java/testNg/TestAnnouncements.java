package testNg;

import extensions.MouseUtils;
import extensions.WaitUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.*;

public class TestAnnouncements extends  _TestNGSetup {

    String title = "Title " + Long.toString(System.currentTimeMillis());
    String text = "Text: " + Long.toString(System.currentTimeMillis());

    @BeforeMethod
    public void beforeMethodActions() {
        driver.navigate().to(testConfig.testURL);
        WaitUtils.waitForPageProcessing(testConfig.testDelayBeforePageProcessing);

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.setEmail(testConfig.testUsername), "[1] Failed to set email address!");
        Assert.assertTrue(loginPage.setPassword(testConfig.testPassword), "[2] Failed to set password!");
        Assert.assertTrue(loginPage.signIn(), "[3] The click action on the 'Sign in' button failed to execute!");

        Navigation topNavigation = new Navigation(driver);
        Assert.assertTrue(topNavigation.clickElement("menuicon"), "[4]The click action on menu icon failed to execute!");

        SideNavigation sideNavigation = new SideNavigation(driver);
        Assert.assertTrue(sideNavigation.selectNavigationSubmenuOption("Administration", "Tasks & Announcements"),
                "[5] Failed to select 'Tasks & Announcements' option!");
        Assert.assertTrue(topNavigation.clickElement("menuicon"), "Failed to collapse menu!");
    }

    @DataProvider
    public Object[][] getAnnouncementType() {
        return new Object[][]{
                {"Custom"},
                {"Confirm User Info"},
                {"PSP Enrollment"}
        };
    }

    @Test(testName = "scheduleAnnouncement"
            , enabled = true
            , dataProvider = "getAnnouncementType"
            , description = "Login as EMA/MA user and check schedule announcement functionality (CUI & PSP):<br>"
            + "&emsp;1. Click action on 'Schedule New Announcement' button;<br>"
            + "&emsp;2. After the Schedule Announcement page has been opened click on group drop down toggle button;<br>"
            + "&emsp;3. Click on the 'Show My Groups' and then select 'AFHS 19 Mentees' group;<br>"
            + "&emsp;4. Collapse goup drop down;<br>"
            + "&emsp;5. Click on the Start date button;<br>"
            + "&emsp;6. Set the start date in calendar;<br>"
            + "&emsp;7. Click on the End date button;<br>"
            + "&emsp;8. Set the end date in calendar;<br>"
            + "&emsp;9. Click on the types drop down;<br>"
            + "&emsp;10. Select type (CUI/PSP) from drop down;<br>"
            + "&emsp;10a. For CUI type select first name;<br>"
            + "&emsp;11. Click on the 'schedule announcement button';<br>"
            + "&emsp;12. Click on the OK button in confirm popUp;<br>"
    )
    public void scheduleAnnouncement(String type) {
        WaitUtils.waitForPageProcessing(testConfig.testDelayBeforePageProcessing);
        TasksAndAnnouncementsPage tasksAndAnnouncementsPage = new TasksAndAnnouncementsPage(driver);
        Assert.assertTrue(tasksAndAnnouncementsPage.scheduleNewAnnouncement(), "[1] The click action on the 'Schedule New Announcement' button failed to execute!");

        ScheduleNewAnnouncementPage newAnnouncementPage = new ScheduleNewAnnouncementPage(driver);
        Assert.assertTrue(newAnnouncementPage.expandGroupDropDown(), "[2] Group drop down has not been expanded!");
        Assert.assertTrue(newAnnouncementPage.showMyGroups(), "[3a] Failed to select 'Show My Groups' button");
        Assert.assertTrue(newAnnouncementPage.selectGroup("AFHS 19 Mentees"), "[3b] Failed to select 'AFHS 19 Mentees' group!");
        Assert.assertTrue(newAnnouncementPage.collapseGroupDropDown(), "[4] Failed to collapse group drop down!");
        Assert.assertTrue(newAnnouncementPage.selectStartDate(), "[5] The click action on the Start Date button failed to execute!");

        CalendarIM calendar = new CalendarIM(driver);
        Assert.assertTrue(calendar.selectActiveDayByIndex(0), "[6] Start date select action failed to execute!");
        Assert.assertTrue(newAnnouncementPage.selectEndDate(), "[7] The click action on the End Date button failed to execute!");
        calendar.refresh();
        Assert.assertTrue(calendar.selectActiveDayByIndex(1), "[8] End date select action failed to execute!");
        Assert.assertTrue(newAnnouncementPage.expandTypesDropDown(), "[9] Types drop down expand action has not been executed!");
        Assert.assertTrue(newAnnouncementPage.selectType(type), "[10] Failed to select :'" + type + "' from Types drop down!");

        if (type.equalsIgnoreCase("Confirm User Info")) {
            Assert.assertTrue(newAnnouncementPage.selectInformationField("First name"), "[10a] Failed to select 'First name' from fields to confirm!");
        }

        if (type.equalsIgnoreCase("Custom")) {
            Assert.assertTrue(newAnnouncementPage.expandPriority(), "The click action on the Priority popUp button failed to execute!");
            Assert.assertTrue(newAnnouncementPage.selectPriority("Banner"), "The click action on the 'Banner' priority failed to execute!");
            Assert.assertTrue(newAnnouncementPage.setAnnouncementTitle(title), "Failed to set Announcement title");
            Assert.assertTrue(newAnnouncementPage.setAnnouncementText(text), "Failed to set Announcement text!");
            Assert.assertTrue(newAnnouncementPage.clickGotItButton(), "Failed to click on the 'Got it!' button!");
        }

        Assert.assertTrue(newAnnouncementPage.scheduleAnnouncement(), "[11] The click action on the 'Schedule Announcement' button failed to execute!");
        Assert.assertTrue(newAnnouncementPage.clickConfirmButton(), "[12] The click action on OK button in confirm popUp failed to execute!");
    }

    @Test(testName = "editAnnouncement"
            , enabled = true
            , description = "Check the edit CUI announcement functionality: <br>"
            + "&emsp;1. Click on the View and Manage button for the first CUI announcement;<br>"
            + "&emsp;2. Preview announcement page should be opened and after that click on the 'Edit' button;<br>"
            + "&emsp;3. Click on the next field which was not selected;<br>"
            + "&emsp;4. Click on the Save button;<br>"
            + "&emsp;5. Click on the OK button in the popUp;<br>"
            + "&emsp;6. Click again on the View and Manage button for the same announcement;<br>"
            + "&emsp;7. Check that the new filed has been added;<br>"
    )
    public void editAnnouncement() {
        WaitUtils.waitForPageProcessing(testConfig.testDelayBeforePageProcessing);
        TasksAndAnnouncementsPage tasksAndAnnouncementsPage = new TasksAndAnnouncementsPage(driver);
        Assert.assertTrue(tasksAndAnnouncementsPage.manageAnnouncement("Please confirm your information"), "[1] The click action on 'View and Manage' icon has not been executed!");

        PreviewAnnouncement previewAnnouncement = new PreviewAnnouncement(driver);
        int informationFields = previewAnnouncement.getLabelsList().size();
        Assert.assertTrue(previewAnnouncement.clickButton("edit"), "[2] The click action on the 'Edit' button failed to execute!");

        EditAnnouncement editAnnouncement = new EditAnnouncement(driver);
        Assert.assertTrue(editAnnouncement.clickNextFreeField(), "[3] The selection of the filed has not been executed!");
        Assert.assertTrue(editAnnouncement.clickSaveButton(), "[4] The click action on the 'Save' button failed to execute!");
        Assert.assertTrue(editAnnouncement.clickPopUpOkButton(), "[5] The click action on the OK popUp button failed to execute!");

        Assert.assertTrue(tasksAndAnnouncementsPage.manageAnnouncement("Please confirm your information"), "[6] The click action on 'View and Manage' icon has not been executed!");
        previewAnnouncement.refresh();
        int infromationFieldsAddedOne = previewAnnouncement.getLabelsList().size();
        Assert.assertEquals(infromationFieldsAddedOne, informationFields + 1, "[7] The new information filed has not been added!");
    }

    @Test(testName = "editCustomAnnouncement",
            enabled = true,
            dependsOnMethods = "scheduleAnnouncement",
            description = "Check the edit Custom announcement functionality:<br>"
            + "&emsp;1. Click on the View and Manage button for given CUI announcement;<br>"
            + "&emsp;2. Preview announcement page should be opened and after that click on the 'Edit' button;<br>"
            + "&emsp;3. Change the text for the Custom announcement;<br>"
            + "&emsp;4. Click on the Save button;<br>"
            + "&emsp;5. Click on the OK button in popUp;<br>"
    )
    public void editCustomAnnouncement() {
        WaitUtils.waitForPageProcessing(testConfig.testDelayBeforePageProcessing);
        TasksAndAnnouncementsPage tasksAndAnnouncementsPage = new TasksAndAnnouncementsPage(driver);
        Assert.assertTrue(tasksAndAnnouncementsPage.manageAnnouncement(title), "[1] Failed to click on the Manage announcement icon for announcement: " + title);

        PreviewAnnouncement previewAnnouncement = new PreviewAnnouncement(driver);
        Assert.assertTrue(previewAnnouncement.clickButton("edit"), "[2] The click action on the 'Edit' button failed to execute!");

        EditAnnouncement editAnnouncement = new EditAnnouncement(driver);
        Assert.assertTrue(editAnnouncement.editCustomAnnouncement(), "[3] Failed to change the text for the custom announcement!");
        Assert.assertTrue(editAnnouncement.clickSaveButton(), "[4] Failed to click on the Save button!");
        Assert.assertTrue(editAnnouncement.clickPopUpOkButton(), "[5] The click action on the OK popUp button failed to execute!");
    }

}
