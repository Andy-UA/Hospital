package Types;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andre on 06.05.2017.
 */
public enum JobScope {
    Unknown,
    Therapist,
    Surgeon,
    Psychiatrist,
    Dentist;
    public static JobScope fromInteger(int x) {
        if(x >= 0 && x < values().length)
            return values()[x];
        return values()[0];
    }
}
