package glacier.actions;


import java.text.SimpleDateFormat;
import java.util.Date;

public class ActionBase {

    public static String getTimeStamp(String dateTimeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateTimeFormat);
        Date date = new Date();
        return format.format(date);
    }
}
