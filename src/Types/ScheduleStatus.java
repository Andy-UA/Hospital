package Types;

/**
 * Created by Andrew on 18.04.2017.
 */
public enum ScheduleStatus {
    Unknown,
    Open,
    Closed;
    public static ScheduleStatus fromInteger(int x) {
        if(x >= 0 && x < values().length)
            return values()[x];
        return values()[0];
    }
}
