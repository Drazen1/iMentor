package testNg;

import extensions.WaitUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.*;

public class TestChat extends _TestNGSetup {
    String text = Long.toString(System.currentTimeMillis());

    @Test(testName = "chatFunctionalityMenteeSendMessage"
            , enabled = true
            , description = "Login as Mentee and check if the chat functionality works by sending message to mentor: <br>"
            + "&emsp;1. Check if the email can be set;<br>"
            + "&emsp;2. Check if the password can be set;<br>"
            + "&emsp;3. Check if the click on the 'Sing in' button works correctly;<br>"
            + "&emsp;4. If the Mentee home page is opened and questionnaire popUp is displayed dismiss the popUp and check if the click action on the Chat icon can be executed;<br>"
            + "&emsp;5. Check if the text message can be set in Chat text filed;<br>"
            + "&emsp;6. Check if the message can be sent by clicking send icon;<br>"
            + "&emsp;7. Check if the sent message can be found in message area;<br>"
    )
    public void chatFunctionalityMenteeSendMessage() {
        driver.navigate().to(testConfig.testURL);
        WaitUtils.waitForPageProcessing(testConfig.testDelayBeforePageProcessing);

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.setEmail(testConfig.testUsername), "[1] Failed to set username!");
        Assert.assertTrue(loginPage.setPassword(testConfig.testPassword), "[2] Failed to set password!");
        Assert.assertTrue(loginPage.signIn(), "[3] The click action on the Sign In button failed to execute!");

        HomePageMentee homePageMentee = new HomePageMentee(driver);
        Navigation navigation = new Navigation(driver);
        Assert.assertTrue(navigation.clickElement("chaticon"), "[4] The click action on the Chat icon failed to execute!");

        ChatWindow chatWindow = new ChatWindow(driver);
        Assert.assertTrue(chatWindow.setMessage(text), "[5] Failed to set the text into text filed area!");
        Assert.assertTrue(chatWindow.sendMessage(), "[6] The click action on the send icon failed to execute!");
        Assert.assertTrue(chatWindow.checkIfMessageExists(text), "[7] The expected message: '" + text + "' is not found in sent messages area!");
    }

    @Test(testName = "chatFunctionalityMentorReadMessage"
            , enabled = true
            , description = "Login as Mentor and check if the message sent by mentee can be found:<br>"
            + "&emsp;1. Check if the emal can be set;<br>"
            + "&emsp;2. Check if the password can be set;<br>"
            + "&emsp;3. Check if the click on the 'Sing in' button works correctly;<br>"
            + "&emsp;4. After the Mentor has been signed in, check if the click action on the Chat icon can be executed;<br>"
            + "&emsp;5. Check if the Mentee message can be found in message area;<br>"
    )
    public void chatFunctionalityMentorReadMessage() {
        driver.navigate().to(testConfig.testURL);
        WaitUtils.waitForPageProcessing(testConfig.testDelayBeforePageProcessing);

        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.setEmail("testemails+user_151764@imentor.org"), "[1] Failed to set username!");
        Assert.assertTrue(loginPage.setPassword("test123"), "[2] Failed to set password!");
        Assert.assertTrue(loginPage.signIn(), "[3] The click action on the Sign In button failed to execute!");

        HomePageMentor homePageMentor = new HomePageMentor(driver);
        Navigation navigation = new Navigation(driver);
        Assert.assertTrue(navigation.clickElement("chaticon"), "[4] The click action on the Chat icon failed to execute!");

        ChatWindow chatWindow = new ChatWindow(driver);
        Assert.assertTrue(chatWindow.checkIfMessageExists(text), "[5] The expected message: '" + text + "' is not found in sent messages area!");
    }
}
