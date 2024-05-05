package glacier.core.utilities;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {

    public static Properties properties;

    public enum PropertyFile {
        CONFIG, WEB, MOBILE;
    }

    public static void loadPropertyFile(PropertyFile config) {
        try {
            properties = new Properties();
            FileInputStream propFile = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/project/config/" + config.toString().toLowerCase() + ".properties");
            properties.load(propFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String property) {
        return properties.getProperty(property);
    }

    public static String getPropertyQuick(PropertyFile propertyFile, String property) {
        loadPropertyFile(propertyFile);
        return getProperty(property);
    }
}
