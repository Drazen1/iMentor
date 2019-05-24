package testNg;

import extensions.WaitUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.HomePageAdmin;
import pageObjects.LoginPage;

public class TestUserManagementFlipbookSnapshot extends _TestNGSetup {

    @DataProvider
    public Object[][] options() {
        return new Object[][] {
                {"Flipbook"},
                {"User Management"},
                {"Snapshot"}
        };
    }

    @Test(testName = "userManagementFlipbookSnapshotTest"
            , enabled = true
            , dataProvider = "options"
            , description = "Staff test case - Login to page and check if the 'Flipbook', 'User Management' and 'Snapshot' options can be opened from home page:<br>"
            + "&emsp;1. Navigate to login page and check if the Email can be set;<br>"
            + "&emsp;2. Check if the Password can be set;<br>"
            + "&emsp;3. Check if the click on the 'Sign in' button can be executed;<br>"
            + "&emsp;4. Click on the every of the following options: 'Flipbook', 'User Management' and 'Snapshot';<br>"
            + "&emsp;5. Validate that the clicked options stands as tab title and check that the right content element is displayed on the page;<br>"
    )
    public void userManagementFlipbookSnapshotTest(String option) {
        driver.navigate().to(testConfig.testURL);
        WaitUtils.waitForPageProcessing(testConfig.testDelayBeforePageProcessing);

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.setEmail(testConfig.testUsername), "[1] Failed to set username!");
        Assert.assertTrue(loginPage.setPassword(testConfig.testPassword), "[2] Failed to set password!");
        Assert.assertTrue(loginPage.signIn(), "[3] The click action on the Sign In button failed to execute!");

        HomePageAdmin homePageAdmin = new HomePageAdmin(driver);
        Assert.assertTrue(homePageAdmin.selectItem(option), "[4] The click action on the option: '" + option + "' failed to execute!");

        if (option.equals("Flipbook")) {
            Assert.assertTrue(driver.getTitle().contains("Flipbook"), "[5a] Flipbook option isn't displayed in browser tab title!");
            Assert.assertNotNull(driver.findElement(By.className("flipbook-page")), "[5b] Page container 'flipbook-page' isn't displayed!");
        }

        if (option.equals("User Management")) {
            Assert.assertTrue(driver.getTitle().contains("User Management"), "[5a] User Management option isn't displayed in browser tab title!");
            Assert.assertNotNull(driver.findElement(By.cssSelector("body#id_body")), "[5b] Page container 'body#id_body' isn't displayed!");
        }

        if (option.equals("Snapshot")) {
            Assert.assertTrue(driver.getTitle().contains("Snapshot"), "[5a] Snapshot option isn't displayed in browser tab title!");
            Assert.assertNotNull(driver.findElement(By.className("snapshot-page")), "[5b] Page container 'snapshot-page' isn't displayed!");
        }
    }
}
