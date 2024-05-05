package glacier.core.driver;

import glacier.core.utilities.PropertiesUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;


public class BrowserFactory {


    private static String browserType;

    private static String browserVersion;

    private static boolean isHeadless;

    private static boolean isPrivate;

    private static boolean disableGpu;

    public WebDriver createBrowserInstance() {

        System.setProperty("webdriver.chrome.driver" , "C:\\chromedriver\\chromedriver.exe");
        EdgeDriver edgeDriver = null;
        WebDriver driver = null;
        if (browserType == null || browserVersion == null) {
            PropertiesUtils.loadPropertyFile(PropertiesUtils.PropertyFile.WEB);
            browserType = PropertiesUtils.getProperty("browser.type");
            browserVersion = PropertiesUtils.getProperty("browser.version");
            isHeadless = Boolean.parseBoolean(PropertiesUtils.getProperty("browser.mode.headless").toLowerCase());
            isPrivate = Boolean.parseBoolean(PropertiesUtils.getProperty("browser.mode.private").toLowerCase());
            disableGpu = Boolean.parseBoolean(PropertiesUtils.getProperty("browser.disableGPU").toLowerCase());
        }
        if (browserType.equalsIgnoreCase("chrome")) {
//            if (browserVersion.startsWith("auto")) {
//                WebDriverManager.chromedriver().setup();
//            } else {
//                WebDriverManager.chromedriver().browserVersion(browserVersion).setup();
//            }
//            ChromeOptions cOptions = new ChromeOptions();
//            if (isHeadless)
//                //cOptions.setHeadless(true);
//            if (isPrivate)
//                cOptions.addArguments(new String[] { "--incognito" });
//            if (disableGpu)
//                cOptions.addArguments(new String[] { "--disable-gpu" });
//            ChromeDriver chromeDriver = new ChromeDriver(cOptions);
              return (WebDriver) new ChromeDriver();
        } else if (browserType.equalsIgnoreCase("firefox")) {
            if (browserVersion.startsWith("auto")) {
                WebDriverManager.firefoxdriver().setup();
            } else {
                WebDriverManager.firefoxdriver().browserVersion(browserVersion).setup();
            }
            FirefoxOptions fOptions = new FirefoxOptions();
            if (isHeadless)
                //fOptions.setHeadless(true);
            if (isPrivate)
                fOptions.addArguments(new String[] { "-private" });
            FirefoxDriver firefoxDriver = new FirefoxDriver(fOptions);
        } else if (browserType.toLowerCase().startsWith("ie") || browserType.equalsIgnoreCase("internet explorer")) {
            if (browserVersion.startsWith("auto")) {
                WebDriverManager.iedriver().setup();
            } else {
                WebDriverManager.iedriver().browserVersion(browserVersion).setup();
            }
            InternetExplorerOptions ieOptions = new InternetExplorerOptions();
            if (isPrivate)
                ieOptions.addCommandSwitches(new String[] { "-private" });
            InternetExplorerDriver internetExplorerDriver = new InternetExplorerDriver(ieOptions);
        } else if (browserType.equalsIgnoreCase("edge")) {
            if (browserVersion.startsWith("auto")) {
                WebDriverManager.edgedriver().setup();
            } else {
                WebDriverManager.edgedriver().browserVersion(browserVersion).setup();
            }
            edgeDriver = new EdgeDriver();
        }
        return (WebDriver)edgeDriver;
    }


}

