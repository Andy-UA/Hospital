package Types;

/**
 * Created by andre on 22.04.2017.
 */
public enum RecordDetailStatus {
    Unknown,
    Open,
    Closed;

    public static RecordDetailStatus fromInteger(int x) {
        if(x >= 0 && x < values().length)
            return values()[x];
        return values()[0];
    }
}
