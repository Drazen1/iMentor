package testNg;

import extensions.WaitUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjects.*;

public class TestAttendance extends _TestNGSetup {

    String unitTitle = "Test" + Long.toString(System.currentTimeMillis());
    String lessonTitle = "Test" + Long.toString(System.currentTimeMillis());


    @BeforeClass
    public void createNationalLesson() {
        driver.navigate().to(testConfig.testURL);
        WaitUtils.waitForPageProcessing(testConfig.testDelayBeforePageProcessing);

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.setEmail("testemails+user_100000@imentor.org"), "[1] Failed to set email address!");
        Assert.assertTrue(loginPage.setPassword("test123"), "[2] Failed to set password!");
        Assert.assertTrue(loginPage.signIn(), "[3] The click action on the 'Sign in' button failed to execute!");

        Navigation topNavigation = new Navigation(driver);
        Assert.assertTrue(topNavigation.clickElement("menuicon"), "[4]The click action on menu icon failed to execute!");

        SideNavigation sideNavigation = new SideNavigation(driver);
        Assert.assertTrue(sideNavigation.selectNavigationOption("National Curriculum Management"), "Failed to select 'National Curriculum Management' option!");

        Curriculum curriculumPage = new Curriculum(driver);
        Assert.assertTrue(curriculumPage.clickOnSelectGradeToggleButton(), "The click action on the 'Select grade' drop down toggle button failed to execute!");
        Assert.assertTrue(curriculumPage.selectGrade("Grade 9"), "Failed to select 'Grade 9'!");
        Assert.assertTrue(curriculumPage.clickAddUnit(), "The click action on the 'Add Unit' button failed to execute!");
        Assert.assertTrue(curriculumPage.setUnitTitle(unitTitle), "Failed to set Unit unitTitle!");
        Assert.assertTrue(curriculumPage.setUnitNumber(), "Failed to set Unit Number!");
        Assert.assertTrue(curriculumPage.setOverviewText("test"), "Failed to set Overiew text to: 'test'!");
        Assert.assertTrue(curriculumPage.clickOnSave(), "The click action on the 'Save' button failed to execute!");

        Assert.assertTrue(curriculumPage.clickOnUnit(unitTitle), "The click action on the unit: '" + unitTitle + "' failed to execute!");
        Assert.assertTrue(curriculumPage.clickOnAddLesson(), "The click action on the 'Add Lesson' button failed to execute!");
        Assert.assertTrue(curriculumPage.setLessonTitle(lessonTitle), "Failed to set lesson title");
        Assert.assertTrue(curriculumPage.setLessonNumber(), "Failed to set the lesson number");
        Assert.assertTrue(curriculumPage.menteeOnlyLessonChoice(true), "Failed to set mentee-only lesson to 'Yes'!");
        Assert.assertTrue(curriculumPage.clickOnSave(), "The click action on the 'Save' button failed to execute!");
    }

    public void lessonManagementPresettup() {
        // u lesson managementu dodati lesson u klasu
    }

    @Test(testName = "attendancePageCheck"
            , enabled = true
            , description = "Navigate to Attendance Page, choose a class and check if attendance table is displayed:<br>"
            + "&emsp;1. Check if the email can be set;<br>"
            + "&emsp;2. Check if the password can be set;<br>"
            + "&emsp;3. Check if the click on the 'Sing in' button works correctly;<br>"
            + "&emsp;4. Click on the menu icon;<br>"
            + "&emsp;5. Select Attendance submenu from Administration menu;<br>"
            + "&emsp;6. Click on the 'Choose a class' drop down;<br>"
            + "&emsp;7. Select 'ALHS 19 Class 1' class;<br>"
            + "&emsp;8. Check if attendance table is displayed on the page;<br>"
    )
    public void attendancePageCheck() {
        driver.navigate().to(testConfig.testURL);
        WaitUtils.waitForPageProcessing(testConfig.testDelayBeforePageProcessing);

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.setEmail(testConfig.testUsername), "[1] Failed to set email address!");
        Assert.assertTrue(loginPage.setPassword(testConfig.testPassword), "[2] Failed to set password!");
        Assert.assertTrue(loginPage.signIn(), "[3] The click action on the 'Sign in' button failed to execute!");

        Navigation topNavigation = new Navigation(driver);
        Assert.assertTrue(topNavigation.clickElement("menuicon"), "[4]The click action on menu icon failed to execute!");

        SideNavigation sideNavigation = new SideNavigation(driver);
        Assert.assertTrue(sideNavigation.selectNavigationSubmenuOption("Administration", "Attendance"),
                "[5] Failed to select 'Attendance' option!");

        Attendance attendancePage = new Attendance(driver);
        Assert.assertTrue(attendancePage.selectChooseClass(), "[6] The click action on the 'Choose a class' drop down failed to execute!");
        //Assert.assertTrue(attendancePage.selectOption("Arroyo High 19 Class 2"), "[7] Failed to select 'ALHS 19 Class 1' class!");
        Assert.assertTrue(attendancePage.selectOption(lessonTitle), "[7] Failed to select '" + lessonTitle + "' class!");
        Assert.assertTrue(attendancePage.isAttendanceTableDisplayed(), "[8] Attendance table is not displayed!");
    }
}
