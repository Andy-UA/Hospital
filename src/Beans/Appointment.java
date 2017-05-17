package Beans;

import Types.AppointmentType;

/**
 * Created by Andrew on 08.04.2017.
 */
public class Appointment extends Common {

    private Long id;
    private AppointmentType type;
    private String description;
    private String unit;
    private String note;

    public Appointment(Long id, AppointmentType type, String description, String unit, String note) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.unit = unit;
        this.note = note;
    }

    public Appointment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppointmentType getType() {
        return type;
    }

    public void setType(AppointmentType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public void validate() throws Exception {
        if(isEmptyValue(description))
            throw new Exception("Description is empty");
        if(isEmptyValue(unit))
            throw new Exception("Unit is empty");
    }

    @Override
    public String toString(){
        String s = type.toString();
        s += s.isEmpty() ? "" : ", ";
        s += isEmptyValue(description) ? "" : description;
        s += s.isEmpty() ? "" : ", ";
        s += isEmptyValue(unit) ? "" : ("unit: " + unit);
        return s;
    }
}
