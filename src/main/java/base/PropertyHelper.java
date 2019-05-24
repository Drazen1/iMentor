package base;

import org.testng.ITestContext;

public class PropertyHelper {

    private ITestContext context;

    public String testURL;
    public String testBrowserName;
    public String testUsername;
    public String testPassword;
    public String testUserType;
    public boolean testBrowserWindowMaximized;
    public int testDelayBeforePageProcessing;

    /**
     * Class constructor;
     * Read and set all the properties for the current run from the command line input or TestNG xml file;
     *
     * @param context - TestNG ITestContext object;
     * @throws Exception
     */
    public PropertyHelper(ITestContext context) throws Exception{
        this.context = context;

        testURL = getProperty("testURL");
        if (testURL == null) {
            throw new Exception("[TestConfig] The testURL variable is undefined!");
        }

        testBrowserName = getProperty("testBrowserName");
        if (testBrowserName == null) {
            throw new Exception("[TestConfig] The testBrowserName variable is undefined!");
        }

        String delayBeforePageProcessing = getProperty("testDelayBeforePageProcessing");
        if (delayBeforePageProcessing != null && !delayBeforePageProcessing.isEmpty()) {
            testDelayBeforePageProcessing = Integer.parseInt(delayBeforePageProcessing);
        } else {
            testDelayBeforePageProcessing = 0;
        }

        //testBrowserWindowMaximized should be false if we are testing Mobile app
        String browserMaximized = getProperty("testBrowserWindowMaximized");
        if (browserMaximized.equalsIgnoreCase("true")) {
            testBrowserWindowMaximized = Boolean.parseBoolean(browserMaximized);
        } else {
            testBrowserWindowMaximized = false;
        }

        testUsername = getProperty("testUsername");
        testPassword = getProperty("testPassword");
        testUserType = getProperty("testUserType");
    }

    /**
     * Read the input value for a given property from command line or TextNG xml file;
     * Note: If present in both, the command line input have priority in be returned!
     *
     * @param propName - String - the name of the property that want to be read;
     * @return propValue - String - the value found for the given property;
     */
    public String getProperty(String propName) {
        String propValue = System.getProperty(propName);
        if (propValue == null) {
            propValue = context.getCurrentXmlTest().getParameter(propName);
        }
        return propValue;
    }

}
