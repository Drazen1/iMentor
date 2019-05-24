package testNg;

import extensions.WaitUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.LoginPage;

public class TestLoginPage extends _TestNGSetup {

    @Test(testName = "loginPageGeneralCheck"
            , enabled = true
            , description = "Navigate to Login page and check if all elements are present:<br>"
            + "&emsp;1. Check if the Email address field is displayed;<br>"
            + "&emsp;2. Check if the Password field is displayed;<br>"
            + "&emsp;3. Check if the Forgot password link is displayed;<br>"
            + "&emsp;4. Check if the Sing in button is displayed<br>"
            + "&emsp;5. Check if the Title is displayed;<br>"
            + "&emsp;6. Check if the 'Start a New Mentor Application' button is displayed;<br>"
            + "&emsp;7. Check if the 'Start a New Student Application' button is displayed;<br>"
            + "&emsp;8. Check if the Privacy Policy link is displayed;<br>"
    )
    public void loginPageGeneralCheck() {
        driver.navigate().to(testConfig.testURL);
        WaitUtils.waitForPageProcessing(testConfig.testDelayBeforePageProcessing);
        SoftAssert softAssert = new SoftAssert();

        LoginPage loginPage = new LoginPage(driver);
        softAssert.assertTrue(loginPage.isDisplayed("emailfield"), "[1] Email address field is not displayed!");
        softAssert.assertTrue(loginPage.isDisplayed("passwordfield"), "[2] Password field is not displayed!");
        softAssert.assertTrue(loginPage.isDisplayed("forgetpassword"), "[3] Forgot password link is not displayed!");
        softAssert.assertTrue(loginPage.isDisplayed("signin"), "[4] Sign in button is not displayed!");
        softAssert.assertTrue(loginPage.isDisplayed("title") && loginPage.getTitle().length() > 0, "[5] Title is not displayed!");
        softAssert.assertTrue(loginPage.isDisplayed("mentorapplicationbutton"), "[6] 'Start a New Mentor Application' button is not displayed!");
        softAssert.assertTrue(loginPage.isDisplayed("studentapplicationbutton"), "[7] 'Start a New Student Application' button is not displayed!");
        softAssert.assertTrue(loginPage.isDisplayed("privacypolicy"), "[8] 'Privacy Policy' link is not displayed!");
        softAssert.assertAll();
    }

    @DataProvider
    public Object[][] getCredentials() {
        return new Object[][] {
                {"testemails+user_151742@imentor.org", "test123"}, // mentee
                {"testemails+user_151764@imentor.org", "test123"} //mentor
        };
    }

    @Test(testName = "loginFunctionalityCheck"
            , enabled = true
            , dataProvider = "getCredentials"
            , description = "Navigate to Login page and check if the login functionality works correctly for both mentee nad mentor user:<br>"
            + "&emsp;1. Check if the emal can be set;<br>"
            + "&emsp;2. Check if the password can be set;<br>"
            + "&emsp;3. Check if the click on the 'Sing in' button works correctly;<br>"
            + "&emsp;4. Check if the home page is opened by checking if the current page URL contains 'home';<br>"
    )
    public void loginFunctionalityCheck(String email, String password) {
        driver.navigate().to(testConfig.testURL);
        WaitUtils.waitForPageProcessing(testConfig.testDelayBeforePageProcessing);

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.setEmail(email), "[1] Failed to set email address!");
        Assert.assertTrue(loginPage.setPassword(password), "[2] Failed to set password!");
        Assert.assertTrue(loginPage.signIn(), "[3] The click action on the 'Sign in' button failed to execute!");
        Assert.assertTrue(driver.getCurrentUrl().contains("/home"), "Home page has not been opened!");
    }
}
