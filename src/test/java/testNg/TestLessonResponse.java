package testNg;

import extensions.WaitUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageObjects.HomePageMentee;
import pageObjects.HomePageMentor;
import pageObjects.LessonPage;
import pageObjects.LoginPage;

public class TestLessonResponse extends _TestNGSetup {

    String message = Long.toString(System.currentTimeMillis());

    @BeforeTest
    public void login() {
        driver.navigate().to(testConfig.testURL);
        WaitUtils.waitForPageProcessing(testConfig.testDelayBeforePageProcessing);

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.setEmail(testConfig.testUsername), "[1] Failed to set email address!");
        Assert.assertTrue(loginPage.setPassword(testConfig.testPassword), "[2] Failed to set password!");
        Assert.assertTrue(loginPage.signIn(), "[3] The click action on the 'Sign in' button failed to execute!");
    }

    @Test(testName = "sendLessonResponseTest"
            , enabled = true
            , description = "Login as Mentee and then navigate to lesson page and check if the response for the lesson can be sent to mentor."
            + "&emsp;1. Login as mentee user and click on the 'Lesson 2';<br>"
            + "&emsp;2. Send the message;<br>"
            + "&emsp;3. Click on the 'Back to Homepage' button;<br>"
    )
    public void sendLessonResponseTest() {
        HomePageMentee homePageMentee = new HomePageMentee(driver);
        Assert.assertTrue(homePageMentee.clickLesson("Lesson 2"), "Failed to select 'Lesson 2'!");

        LessonPage lessonPage = new LessonPage(driver);
        Assert.assertTrue(lessonPage.sendMessage(message), "[1] Failed to sent the following message: "  + message);
        Assert.assertTrue(lessonPage.clickBackToHomepage(), "[2] The click action on the Back to Homepage button failed to execute!");
    }

    @Test(testName = "readLessonResponseByMentorTest"
            , enabled = true
            , description = "Login to website as Mentor and chek if there is a new message sent from the mentee:<br>"
            + "&emsp;1. Check if the email can be set;<br>"
            + "&emsp;2. Check if the password can be set;<br>"
            + "&emsp;3. Check if the click on the 'Sing in' button works correctly;<br>"
            + "&emsp;4. After the mentor has been logged in check if there is information about NEW message;<br>"
            + "&emsp;5. Click on the the 'Lesson 2' and check if there is a message which was sent from mentee;<br>"
    )
    public void readLessonResponseByMentorTest() {
        driver.navigate().to(testConfig.testURL);
        WaitUtils.waitForPageProcessing(testConfig.testDelayBeforePageProcessing);

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.setEmail("testemails+user_151742@imentor.org"), "[1] Failed to set email address!");
        Assert.assertTrue(loginPage.setPassword("test123"), "[2] Failed to set password!");
        Assert.assertTrue(loginPage.signIn(), "[3] The click action on the 'Sign in' button failed to execute!");

        HomePageMentor homePageMentor = new HomePageMentor(driver);
        Assert.assertTrue(homePageMentor.getNewMessageInfo("Lesson 2").contains("NEW"), "[4] The information about 'NEW' message is not displayed");
        Assert.assertTrue(homePageMentor.clickLesson("Lesson 2"), "[5a] The click action on 'Lesson 2' failed to execute!");

        LessonPage lessonPage = new LessonPage(driver);
        Assert.assertTrue(lessonPage.containsMessage(message), "[5b] The sent message is missing on the lesson page!");
    }
}
