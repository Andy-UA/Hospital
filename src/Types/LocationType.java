package Types;

/**
 * Created by Andrew on 11.04.2017.
 */
public enum LocationType {
    Undefined,
    LivingPlace,
    RegistrationPlace,
    WorkPlace;
    public static LocationType fromInteger(int x) {
        if(x >= 0 && x < values().length)
            return values()[x];
        return values()[0];
    }
}
