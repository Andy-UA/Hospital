package Types;

/**
 * Created by andre on 24.04.2017.
 */
public enum TreatmentStatus {
    Unknown,
    Open,
    Closed;
    public static TreatmentStatus fromInteger(int x) {
        if(x >= 0 && x < values().length)
            return values()[x];
        return values()[0];
    }
}
