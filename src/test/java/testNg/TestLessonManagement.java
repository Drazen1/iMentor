package testNg;

import extensions.WaitUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.CalendarIM;
import pageObjects.HomePageAdmin;
import pageObjects.LessonManagementPage;
import pageObjects.LoginPage;

public class TestLessonManagement extends _TestNGSetup {

    @BeforeMethod
    public void deleteAllLessons() {
        driver.navigate().to(testConfig.testURL);
        WaitUtils.waitForPageProcessing(testConfig.testDelayBeforePageProcessing);

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.setEmail(testConfig.testUsername), "[1] Failed to set email address!");
        Assert.assertTrue(loginPage.setPassword(testConfig.testPassword), "[2] Failed to set password!");
        Assert.assertTrue(loginPage.signIn(), "[3] The click action on the 'Sign in' button failed to execute!");

        HomePageAdmin homePageAdmin = new HomePageAdmin(driver);
        Assert.assertTrue(homePageAdmin.selectItem("Lesson Management"), "Failed to select 'Lesson Management' option!");

        LessonManagementPage lessonManagementPage = new LessonManagementPage(driver);
        Assert.assertTrue(lessonManagementPage.clickElement("chooseclass"), "Failed to select 'Choose a class' drop down button!");
        Assert.assertTrue(lessonManagementPage.selectOption("ALHS 19 Class 1"), "Failed to select 'ALHS 19 Class 1' group!");
        Assert.assertTrue(lessonManagementPage.deleteAllScheduledLessons(), "Failed to clean(delete) all scheduled lessons!");
    }

    @Test(testName = "scheduleLessonTest"
            , enabled = true
            , description = "Login as admin user and check if the schedule lesson functionality works:<br>"
            + "&emsp;1. Check if the emal can be set;<br>"
            + "&emsp;2. Check if the password can be set;<br>"
            + "&emsp;3. Check if the click on the 'Sing in' button works correctly;<br>"
            + "&emsp;4. Check if the 'Lesson Management' option can be selected;<br>"
            + "&emsp;5. After the Lesson Management page has been loaded select 'Choose a class' drop down;<br>"
            + "&emsp;6. Select 'ALHS 19 Class 1' group option;<br>"
            + "&emsp;7. Select Grade drop down button;<br>"
            + "&emsp;8. Select Grade value;<br>"
            + "&emsp;9. Select Unit drop drown;<br>"
            + "&emsp;10. Select Unit value;<br>"
            + "&emsp;11. Select Lesson drop down button;<br>"
            + "&emsp;12. Select Lesson value;<br>"
            + "&emsp;13. Select Date button;<br>"
            + "&emsp;14. Set the date in Calendar;<br>"
            + "&emsp;15. Set the hours;<br>"
            + "&emsp;16. Set the minutes;<br>"
            + "&emsp;17. Click on the 'Schedule the lesson' button;<br>"
            + "&emsp;18. Check if the Congrats popUp is displayed;<br>"
            + "&emsp;19. Click on the 'Manage Lessons' button from Congrats popUp;<br>"
    )
    public void scheduleLessonTest() {
        driver.navigate().to(testConfig.testURL);
        WaitUtils.waitForPageProcessing(testConfig.testDelayBeforePageProcessing);

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.setEmail(testConfig.testUsername), "[1] Failed to set email address!");
        Assert.assertTrue(loginPage.setPassword(testConfig.testPassword), "[2] Failed to set password!");
        Assert.assertTrue(loginPage.signIn(), "[3] The click action on the 'Sign in' button failed to execute!");

        HomePageAdmin homePageAdmin = new HomePageAdmin(driver);
        Assert.assertTrue(homePageAdmin.selectItem("Lesson Management"), "[4] Failed to select 'Lesson Management' option!");

        LessonManagementPage lessonManagementPage = new LessonManagementPage(driver);
        Assert.assertTrue(lessonManagementPage.clickElement("chooseclass"), "[5] Failed to select 'Choose a class' drop down button!");
        Assert.assertTrue(lessonManagementPage.selectOption("ALHS 19 Class 1"), "[6] Failed to select 'ALHS 19 Class 1' group!");
        Assert.assertTrue(lessonManagementPage.clickElement("grade"), "[7] Failed to select 'Grade' drop down button!");
        Assert.assertTrue(lessonManagementPage.clickElement("gradevalue"), "[8] Failed to select grade value!");
        Assert.assertTrue(lessonManagementPage.clickElement("unit"), "[9] Failed to select 'Unit' drop down button!");
        Assert.assertTrue(lessonManagementPage.clickElement("unitvalue"), "[10] Failed to select unit value!");
        Assert.assertTrue(lessonManagementPage.clickElement("lesson"), "[11] Failed to select 'Lesson' drop down button!");
        Assert.assertTrue(lessonManagementPage.clickElement("lessonvalue"), "[12] Failed to select Lesson value!");

        Assert.assertTrue(lessonManagementPage.clickElement("date"), "[13] Failed to select Date button");
        CalendarIM calendar = new CalendarIM(driver);
        Assert.assertTrue(calendar.selectDay(), "[14] Start date select action failed to execute!");

        Assert.assertTrue(lessonManagementPage.setHours(7), "[15] Failed to set hours!");
        Assert.assertTrue(lessonManagementPage.setMinutes(25), "[16] Failed to set minutes!");
        Assert.assertTrue(lessonManagementPage.clickElement("schedulelesson"), "[17] The click action on the 'Schedule the lesson' button failed to execute!");

        Assert.assertTrue(lessonManagementPage.isCongratsPoPupDisplayed(), "[18] Congrats popUp should be displayed!");
        Assert.assertTrue(lessonManagementPage.clickManageLessonsFromPopUp(), "[19] The click action on 'Manage Lessons' from Congrats popUp failed to execute!");
    }
}
