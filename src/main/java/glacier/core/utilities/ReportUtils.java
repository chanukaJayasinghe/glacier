package glacier.core.utilities;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import glacier.actions.ActionBase;


public class ReportUtils {


    public static ExtentReports setUpExtentReport() {
        Theme theme;
        PropertiesUtils.loadPropertyFile(PropertiesUtils.PropertyFile.CONFIG);
        String reportLocation = System.getProperty("user.dir") + PropertiesUtils.getProperty("reports.location") + "/ExecutionReport_" + ActionBase.getTimeStamp("MM-dd-yy HH-mm-ss") + ".html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportLocation);
        ExtentReports extentReport = new ExtentReports();
        extentReport.attachReporter(new ExtentReporter[] { (ExtentReporter)sparkReporter });
        sparkReporter.config().setDocumentTitle(PropertiesUtils.getProperty("reports.title"));
        sparkReporter.config().setReportName(PropertiesUtils.getProperty("reports.name"));
        if (PropertiesUtils.getProperty("reports.theme.dark").equalsIgnoreCase("true")) {
            theme = Theme.DARK;
        } else {
            theme = Theme.STANDARD;
        }
        sparkReporter.config().setTheme(theme);
        return extentReport;
    }


}
