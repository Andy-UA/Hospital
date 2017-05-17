package Beans;

import Types.RoleCategory;
import Types.ScheduleStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Andrew on 17.04.2017.
 */
public class ScheduleInfo extends Common {
    private Long id;
    private ScheduleStatus status;
    private String scheduleNote;
    private LocalDateTime eventBegin;
    private LocalDateTime eventEnd;
    private Long staffID;
    private String staffName;
    private RoleCategory staffCategory;
    private String staffNote;
    private String staffSex;
    private LocalDate staffBirthday;
    private Long workplaceID;
    private String building;
    private String floor;
    private String room;
    private String workplaceNote;
    private Long patientID;
    private Long humanID;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String sex;

    public String getPatientName() {
        String s = "";
        s += firstName == null ? "" : firstName;
        s += s.isEmpty() ? "" : ", ";
        s += lastName == null ? "" : lastName;
        return s;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }

    public String getScheduleNote() {
        return scheduleNote;
    }

    public void setScheduleNote(String scheduleNote) {
        this.scheduleNote = scheduleNote;
    }

    public LocalDateTime getEventBegin() {
        return eventBegin;
    }

    public void setEventBegin(LocalDateTime eventBegin) {
        this.eventBegin = eventBegin;
    }

    public String getEventBeginText() {
        return getDateTimeText(eventBegin);
    }

    public LocalDateTime getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(LocalDateTime eventEnd) {
        this.eventEnd = eventEnd;
    }

    public String getEventEndText() {
        return getDateTimeText(eventEnd);
    }

    public Long getStaffID() {
        return staffID;
    }

    public void setStaffID(Long staffID) {
        this.staffID = staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public RoleCategory getStaffCategory() {
        return staffCategory;
    }

    public void setStaffCategory(RoleCategory staffCategory) {
        this.staffCategory = staffCategory;
    }

    public String getStaffNote() {
        return staffNote;
    }

    public void setStaffNote(String staffNote) {
        this.staffNote = staffNote;
    }

    public String getStaffSex() {
        return staffSex;
    }

    public void setStaffSex(String staffSex) {
        this.staffSex = staffSex;
    }

    public LocalDate getStaffBirthday() {
        return staffBirthday;
    }

    public void setStaffBirthday(LocalDate staffBirthday) {
        this.staffBirthday = staffBirthday;
    }

    public Long getWorkplaceID() {
        return workplaceID;
    }

    public void setWorkplaceID(Long workplaceID) {
        this.workplaceID = workplaceID;
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

    public String getWorkplaceNote() {
        return workplaceNote;
    }

    public void setWorkplaceNote(String workplaceNote) {
        this.workplaceNote = workplaceNote;
    }

    public Long getPatientID() {
        return patientID;
    }

    public void setPatientID(Long patientID) {
        this.patientID = patientID;
    }

    public Long getHumanID() {
        return humanID;
    }

    public void setHumanID(Long humanID) {
        this.humanID = humanID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
