package Beans;

import Types.ScheduleStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Andrew on 08.04.2017.
 */
public class Schedule extends Common {

    private Long id;
    private Long staffID;
    private Long patientID;
    private Long workplaceID;
    private LocalDateTime eventBegin;
    private LocalDateTime eventEnd;
    private ScheduleStatus status;
    private String note;

    public Schedule(Long id, Long staffID, Long workplaceID, Long patientID, LocalDateTime eventBegin, LocalDateTime eventEnd, ScheduleStatus status, String note) {
        this.id = id;
        this.staffID = staffID;
        this.workplaceID = workplaceID;
        this.patientID = patientID;
        this.eventBegin = eventBegin;
        this.eventEnd = eventEnd;
        this.status = status;
        this.note = note;
    }

    public Schedule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStaffID() {
        return staffID;
    }

    public void setStaffID(Long staffID) {
        this.staffID = staffID;
    }

    public Long getPatientID() {
        return patientID;
    }

    public void setPatientID(Long patientID) {
        this.patientID = patientID;
    }

    public Long getWorkplaceID() {
        return workplaceID;
    }

    public void setWorkplaceID(Long workplaceID) {
        this.workplaceID = workplaceID;
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

    public ScheduleStatus getStatus() {
        return status;
    }

    public void setStatus(ScheduleStatus status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public void validate() throws Exception {
        if(isEmptyValue(eventBegin.toString()))
            throw new Exception("Event begin is empty");
        if(isEmptyValue(eventEnd.toString()))
            throw new Exception("Event end is empty");

    }
}
