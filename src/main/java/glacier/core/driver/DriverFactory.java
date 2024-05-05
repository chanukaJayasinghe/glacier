package glacier.core.driver;

import org.openqa.selenium.WebDriver;

public class DriverFactory {

    private static DriverFactory driverManger = new DriverFactory();

    public static DriverFactory getInstance() {
        return driverManger;
    }

    ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public WebDriver getDriver() {
        return this.driver.get();
    }

    public void setDriver(WebDriver driverElement) {
        this.driver.set(driverElement);
    }

    public void closeBrowser() {
        ((WebDriver)this.driver.get()).close();
        this.driver.remove();
    }

    public void quitBrowser() {
        ((WebDriver)this.driver.get()).quit();
        this.driver.remove();
    }
}
