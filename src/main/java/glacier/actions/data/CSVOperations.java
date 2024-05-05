package glacier.actions.data;


import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import glacier.core.utilities.PropertiesUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CSVOperations {

    static String fileLocation;

    public CSVOperations() throws Exception {
        PropertiesUtils.loadPropertyFile(PropertiesUtils.PropertyFile.CONFIG);
        fileLocation = System.getProperty("user.dir") + PropertiesUtils.getProperty("datafiles.location");
    }

    public static String[] CSVReadOneLine(String fileName, int noOfColumns) {
        CSVReader reader = null;
        String[] data = new String[0];
        try {
            reader = new CSVReader(new FileReader(fileLocation + fileName));
            String[] csvCell;
            while ((csvCell = reader.readNext()) != null) {
                for (int i = 0; i <= noOfColumns; i++)
                    data[i] = csvCell[i];
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static List<String[]> CSVReadDataWithSkipLines(String fileName, int lineToSkip) {
        List<String[]> allData = null;
        try {
            FileReader filereader = new FileReader(fileLocation + fileName);
            CSVReader csvReader = (new CSVReaderBuilder(filereader)).withSkipLines(lineToSkip).build();
            allData = csvReader.readAll();
            for (String[] row : allData) {
                for (String cell : row)
                    System.out.print(cell + "\t");
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allData;
    }

    public static String ReadExcelFile(String fileName, String sheetName, int row, int col) {
        FileInputStream fileInputStream = null;
        String value = null;
        try {
            fileInputStream = new FileInputStream(fileLocation + fileName);
            XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
            XSSFSheet sheet = wb.getSheet(sheetName);
            XSSFRow rowValue = sheet.getRow(row);
            XSSFCell cellValue = rowValue.getCell(col);
            value = cellValue.getStringCellValue();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
}
