package Types;

/**
 * Created by Andrew on 14.04.2017.
 */
public enum RoleCategory {
    Unknown,
    People,
    Nurses,
    Doctors;
    public static RoleCategory fromInteger(int x) {
        if(x >= 0 && x < values().length)
            return values()[x];
        return values()[0];
    }
}
