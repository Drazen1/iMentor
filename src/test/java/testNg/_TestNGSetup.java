package testNg;

import base.DriverStart;
import base.PropertyHelper;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class _TestNGSetup {

    public PropertyHelper testConfig;
    public WebDriver driver;

    @BeforeTest
    public void setupBeforeTest(ITestContext context) throws Exception{
        testConfig = new PropertyHelper(context);
        driver = DriverStart.instantiateWebDriver(testConfig);

        if (testConfig.testBrowserWindowMaximized) {
            driver.manage().window().maximize();
        }
    }

    @AfterTest
    public void setupAfterTest() {
        if (driver != null) {
            driver.quit();
        }
    }
}
