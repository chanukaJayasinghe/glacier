package glacier.actions.web;

import glacier.actions.ActionBase;
import glacier.core.driver.DriverManager;
import glacier.core.utilities.PropertiesUtils;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class WebUI extends ActionBase {

    protected static final int timeout = Integer.parseInt(PropertiesUtils.getPropertyQuick(PropertiesUtils.PropertyFile.WEB, "execution.TIME_OUT_DEFAULT"));

    protected static final int pageloadTimeout = Integer.parseInt(PropertiesUtils.getPropertyQuick(PropertiesUtils.PropertyFile.WEB, "execution.TIME_OUT_PAGELOAD"));

    protected static final int sleepTimeout = Integer.parseInt(PropertiesUtils.getPropertyQuick(PropertiesUtils.PropertyFile.WEB, "execution.TIME_OUT_SLEEP"));

    public static void back() {
        DriverManager.getDriver().navigate().back();
    }

    public static void closeBrowser() {
        DriverManager.quitBrowser();
    }

    public static void delay(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void forward() {
        DriverManager.getDriver().navigate().forward();
    }

    public static String getUrl() {
        String URL = DriverManager.getDriver().getCurrentUrl();
        return URL;
    }

    public static void maximizeWindow() {
        DriverManager.getDriver().manage().window().maximize();
    }

    public static void navigateToUrl(String URL) {
        DriverManager.getDriver().manage().deleteAllCookies();
        DriverManager.getDriver().get(URL);
    }

    public static void switchToTabs(int tabIndexToSwitch) {
        ArrayList<String> newTab = new ArrayList<>(DriverManager.getDriver().getWindowHandles());
        DriverManager.getDriver().switchTo().window(newTab.get(tabIndexToSwitch));
    }

    public static void scrollToTopOfThePage() {
        Actions actions = new Actions(DriverManager.getDriver());
        actions.sendKeys(new CharSequence[] { (CharSequence)Keys.HOME }).perform();
    }

    public static void scrollToBottomOfThePage() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        boolean isOn = toolkit.getLockingKeyState(144);
        System.out.println("NUM Lock Status : " + isOn);
        Actions actions = new Actions(DriverManager.getDriver());
        Robot robot = null;
        if (isOn) {
            try {
                robot = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
            robot.keyPress(144);
            robot.keyRelease(144);
        }
        robot.keyPress(35);
        robot.keyRelease(35);
    }

    public static void openLinkInNewTab(String url) {
        ((JavascriptExecutor)DriverManager.getDriver()).executeScript("window.open()", new Object[0]);
        ArrayList<String> tabs = new ArrayList<>(DriverManager.getDriver().getWindowHandles());
        DriverManager.getDriver().switchTo().window(tabs.get(1));
        DriverManager.getDriver().get(url);
    }

    public static void newTabClose(int tabIndexToBeClosed) {
        ArrayList<String> tabs = new ArrayList<>(DriverManager.getDriver().getWindowHandles());
        DriverManager.getDriver().close();
        DriverManager.getDriver().switchTo().window(tabs.get(tabIndexToBeClosed));
    }

    public static void clearText(WebElement element) {
        element.clear();
    }

    public static void click(WebElement element) {
        element.click();
    }

    public static String getText(WebElement element) {
        String text = element.getText();
        return text;
    }

    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor)DriverManager.getDriver()).executeScript("arguments[0].scrollIntoView(true);", new Object[] { element });
    }

    public static void sendKeys(WebElement element, String text) {
        element.sendKeys(new CharSequence[] { text });
    }

    public static void setText(WebElement element, String text) {
        element.clear();
        element.sendKeys(new CharSequence[] { text });
    }

    public static void selectDropDown(WebElement webElement, String valueToBeSelected) throws Exception {
        Select select = new Select(webElement);
        try {
            select.selectByVisibleText(valueToBeSelected);
        } catch (Exception e) {
            throw new Exception("Value is not present in the dropdown '" + webElement + "' ");
        }
    }

    public static void selectRadio(List<WebElement> webElements, String valueToBeSelected) {
        for (WebElement current : webElements) {
            if (current.getText().equalsIgnoreCase(valueToBeSelected)) {
                current.click();
                break;
            }
        }
    }

    public static void selectCheckBox(List<WebElement> webElements, String... values) {
        for (String value : values) {
            for (WebElement webElement : webElements) {
                if (webElement.getText().equalsIgnoreCase(value)) {
                    webElement.click();
                    break;
                }
            }
        }
    }

    public static void verifyElementClickable(WebElement element) {
        Duration timeout = Duration.ofSeconds(60);
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeout);
        wait.until((Function)ExpectedConditions.elementToBeClickable(element));
    }

    public static void verifyElementSelected(WebElement element) {
        boolean value = element.isSelected();
        Assert.assertEquals(value, true, "Element is not visible!!!");
    }

    public static void verifyElementEnabled(WebElement element) {
        boolean value = element.isEnabled();
        Assert.assertEquals(value, true, "Element is not visible!!!");
    }

    public static void verifyElementVisible(WebElement element) {
        boolean value = element.isDisplayed();
        Assert.assertEquals(value, true, "Element is not visible!!!");
    }

    public static void verifyElementNotPresent(WebElement element) {
        boolean value = !element.isDisplayed();
        Assert.assertEquals(value, false, "Element is present in the document!!!");
    }

    public static void verifyTextPresentInElement(WebElement element, String text) {
        byte[] textInElement = element.getText().getBytes();
        boolean value;
        if (value = (textInElement.length > 0))
            Assert.assertEquals(value, true, "Element text is not present in the document!!!");
    }

    public static void verifyTextPresent(String text) {
        Assert.assertTrue(DriverManager.getDriver().getPageSource().contains(text));
    }

    public static void verifyTextNotPresent(String text) {
        Assert.assertFalse(DriverManager.getDriver().getPageSource().contains(text));
    }

    public static boolean verifyElementPresent(By by) {
        int results = findElements(by).size();
        if (results > 0)
            return true;
        return false;
    }

    public static void verifyElementPresent(WebElement element, int timeout) {
        delay(timeout);
        boolean value;
        if (value = element.isDisplayed())
            Assert.assertEquals(value, true, "Element is not present in the document!!!");
    }

    public static void waitForElementPresent(By element) {
        Duration timeout = Duration.ofSeconds(60);
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeout);
        WebElement webElement = (WebElement)wait.until((Function)ExpectedConditions.presenceOfElementLocated(element));
    }

    public static void waitForElementVisible(WebElement webElement) {
        Duration timeout = Duration.ofSeconds(60);
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeout);
        wait.until((Function)ExpectedConditions.visibilityOf(webElement));
    }

    public static void waitForElementClickable(WebElement element) {
        Duration timeout = Duration.ofSeconds(60);
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeout);
        wait.until((Function)ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForPageLoad() {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return Boolean.valueOf(((JavascriptExecutor)driver).executeScript("return document.readyState", new Object[0]).toString().equals("complete"));
            }
        };
        try {
            Thread.sleep(sleepTimeout);
            Duration timeout = Duration.ofSeconds(sleepTimeout);
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), timeout);
            wait.until((Function)expectation);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete." + error.getMessage());
        }
    }

    public static void changeWindowToIframe(String frame) {
        DriverManager.getDriver().switchTo().frame(frame);
    }

    public static void changeWindowToIframe(WebElement webElement) {
        DriverManager.getDriver().switchTo().frame(webElement);
    }

    public static void switchBackToDefaultWindow() {
        DriverManager.getDriver().switchTo().parentFrame();
    }

    public static void switchBackParentFrame() {
        DriverManager.getDriver().switchTo().defaultContent();
    }

    public static WebElement findElement(By element) {
        return DriverManager.getDriver().findElement(element);
    }

    public static List<WebElement> findElements(By element) {
        return DriverManager.getDriver().findElements(element);
    }

    public static List<WebElement> getSubElements(WebElement webElement, By by) {
        return webElement.findElements(by);
    }
}
