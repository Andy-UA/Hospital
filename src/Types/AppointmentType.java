package Types;

/**
 * Created by Andrew on 11.04.2017.
 */
public enum AppointmentType {
    Unknown,
    Drugs,
    Procedure
    ;
    public static AppointmentType fromInteger(int x) {
        if(x >= 0 && x < values().length)
            return values()[x];
        return values()[0];
    }
}
