package glacier.core.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import glacier.actions.ActionBase;
import glacier.core.driver.DriverManager;
import java.io.File;
import java.io.IOException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListeners implements ITestListener  {

    ExtentReports extent;

    ExtentTest test;

    String screenshotPath = System.getProperty("user.dir") +
            PropertiesUtils.getPropertyQuick(PropertiesUtils.PropertyFile.CONFIG, "reports.screens.location") + "/Screenshot_" +
            ActionBase.getTimeStamp("MM-dd-yy HH-mm-ss") + ".jpg";

    public void onTestStart(ITestResult result) {
        this.test = this.extent.createTest(result.getMethod().getMethodName());
    }

    public void onTestSuccess(ITestResult result) {
        this.test.log(Status.PASS, "Test Case: " + result.getMethod().getMethodName() + "is Passed.");
    }

    public void onTestFailure(ITestResult result) {
        this.test.log(Status.FAIL, "Test Case: " + result.getMethod().getMethodName() + "is Failed.");
        this.test.log(Status.FAIL, result.getThrowable());
        File src_screen = (File)((TakesScreenshot)DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
        File dest_screen = new File(this.screenshotPath);
        try {
            FileHandler.copy(src_screen, dest_screen);
            this.test.addScreenCaptureFromPath(this.screenshotPath, "Execution Failure Screen shot");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onTestSkipped(ITestResult result) {}

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

    public void onTestFailedWithTimeout(ITestResult result) {}

    public void onStart(ITestContext context) {
        this.extent = ReportUtils.setUpExtentReport();
    }

    public void onFinish(ITestContext context) {
        this.extent.flush();
    }
}

