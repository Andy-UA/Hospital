package Types;

/**
 * Created by Andrew on 14.04.2017.
 */
public enum RoleScope {
    Unknown,
    Patient,
    Staff,
    Admin;
    public static RoleScope fromInteger(int x) {
        if(x >= 0 && x < values().length)
            return values()[x];
        return values()[0];
    }
}
