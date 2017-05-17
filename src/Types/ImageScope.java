package Types;

/**
 * Created by Andrew on 15.04.2017.
 */
public enum ImageScope {
    Unknown,
    Photo,
    Scan;
    public static ImageScope fromInteger(int x) {
        if(x >= 0 && x < values().length)
            return values()[x];
        return values()[0];
    }
}
