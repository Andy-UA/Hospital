package Beans;

/**
 * Created by Andrew on 08.04.2017.
 */
public class Workplace extends Common {

    private Long id;
    private String building;
    private String floor;
    private String room;
    private String note;

    public Workplace(Long id, String building, String floor, String room, String note) {
        this.id = id;
        this.building = building;
        this.floor = floor;
        this.room = room;
        this.note = note;
    }

    public Workplace() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public void validate() throws Exception {
        if(isEmptyValue(building) && isEmptyValue(floor) && isEmptyValue(room) && isEmptyValue(note))
            throw new Exception("Whole record is empty");

    }

    @Override
    public String toString(){
        String s = "";
        s += building == null || building.isEmpty() ? "" : ("building: " + building);
        s += s.isEmpty() ? "" : ", ";
        s += floor == null || floor.isEmpty() ? "" : ("floor: " + floor);
        s += s.isEmpty() ? "" : ", ";
        s += room == null || room.isEmpty() ? "" : ("room: " + room);
        s += s.isEmpty() ? "" : ", ";
        s += note == null || note.isEmpty() ? "" : ("(note: " + note + ")");
        return s;
    }
}
