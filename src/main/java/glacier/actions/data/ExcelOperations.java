package glacier.actions.data;
import glacier.core.utilities.PropertiesUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelOperations {

    String fileLocation;

    Sheet sheet;

    boolean hasHeader = false;

    public ExcelOperations(String fileName, String sheetName, boolean hasHeader) throws Exception {
        try {
            PropertiesUtils.loadPropertyFile(PropertiesUtils.PropertyFile.CONFIG);
            this.fileLocation = System.getProperty("user.dir") + PropertiesUtils.getProperty("datafiles.location");
            FileInputStream fileInputStream = new FileInputStream(this.fileLocation + fileName);
            Workbook workbook = WorkbookFactory.create(fileInputStream);
            this.sheet = workbook.getSheet(sheetName);
            this.hasHeader = hasHeader;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCell(int row, int col) throws Exception {
        if (this.hasHeader)
            row++;
        Row rowVal = this.sheet.getRow(row);
        Cell cell = rowVal.getCell(col);
        return cell.getStringCellValue();
    }

    public String getRow(int row) throws Exception {
        if (this.hasHeader)
            row++;
        Row rowVal = this.sheet.getRow(row);
        return rowVal.toString();
    }

    public HashMap<String, String> getRowToMap(int row) throws Exception {
        if (this.hasHeader) {
            row++;
            HashMap<String, String> hashMap = new HashMap<>();
            for (int i = 0; i < this.sheet.getRow(0).getLastCellNum(); i++)
                hashMap.put(this.sheet.getRow(0).getCell(i).toString(), this.sheet.getRow(row).getCell(i).toString());
            return hashMap;
        }
        System.out.println("WARNING: This is method is only available for the data sheets with headers");
        return null;
    }

    public String[] getRowToArray(int row) throws Exception {
        if (this.hasHeader)
            row++;
        String[] currentRow = new String[this.sheet.getRow(0).getLastCellNum()];
        for (int i = 0; i < this.sheet.getRow(0).getLastCellNum(); i++)
            currentRow[i] = this.sheet.getRow(row).getCell(i).toString();
        return currentRow;
    }

    public int getRowCount() {
        if (this.hasHeader)
            return this.sheet.getLastRowNum() - 1;
        return this.sheet.getLastRowNum();
    }

    public int getColumnCount() {
        return this.sheet.getRow(0).getLastCellNum();
    }
}
