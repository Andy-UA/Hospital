package Beans;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by andre on 27.04.2017.
 */
public class Common {
    public boolean isEmptyValue(String value) {
        return value == null || value.isEmpty();
    }

    public String toString(){
        return getClass().getName().toString();
    }

    public void validate() throws Exception {}

    protected String getDateText(LocalDate value) {
        if (value != null)
            return value.format(DateTimeFormatter.ofPattern("uuuu-MM-dd"));
        else
            return "(empty)";
    }

    protected String getDateTimeText(LocalDateTime value) {
        if (value != null)
            return value.format(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm"));
        else
            return "(empty)";
    }
}
