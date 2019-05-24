package testNg;

import extensions.WaitUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.Attendance;
import pageObjects.LoginPage;
import pageObjects.Navigation;
import pageObjects.SideNavigation;

public class TestAttendance extends _TestNGSetup {

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
        Assert.assertTrue(attendancePage.selectOption("Arroyo High 19 Class 2"), "[7] Failed to select 'ALHS 19 Class 1' class!");
        Assert.assertTrue(attendancePage.isAttendanceTableDisplayed(), "[8] Attendance table is not displayed!");
    }
}
