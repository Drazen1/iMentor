package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriverStart {
    public static WebDriver instantiateWebDriver(PropertyHelper testConfig) throws Exception {
        if (testConfig.testBrowserName.equalsIgnoreCase("chrome")) {
            return new ChromeDriver();
        }

        if (testConfig.testBrowserName.equalsIgnoreCase("firefox")) {
            return new FirefoxDriver();
        }

        if (testConfig.testBrowserName.equalsIgnoreCase("android")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", "Nexus_5_API_28");;
            capabilities.setCapability("avd", "Nexus_5_API_28");
            capabilities.setCapability("browserName", "chrome");

            return new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        }

        if (testConfig.testBrowserName.equalsIgnoreCase("ios")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", "iOS");
            capabilities.setCapability("platformVersion", "12.1");
            capabilities.setCapability("deviceName", "iPhone X");
            capabilities.setCapability("automationName", "XCUITest");
            capabilities.setCapability("browserName", "Safari");
            capabilities.setCapability("fullReset", true);
            capabilities.setCapability("newCommandTimeout", 300);

            return new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        }

        if (testConfig.testBrowserName.equalsIgnoreCase("chromemobile")) {
            ChromeOptions chromeCapabilities = new ChromeOptions();
            Map<String, String> mobileEmulation = new HashMap<>();
            chromeCapabilities.addArguments("deviceName", "iPhone X");
            chromeCapabilities.setExperimentalOption("mobileEmulation", mobileEmulation);
            chromeCapabilities.addArguments("--window-size=700,1080");
            ChromeDriverService service = ChromeDriverService.createDefaultService();

            return new ChromeDriver(service, chromeCapabilities);
        }

        throw new Exception("[DriverStart] The valid browser nam has not been specified in testng.xml file!");
    }
}
