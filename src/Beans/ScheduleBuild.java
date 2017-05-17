package Beans;

import java.time.LocalDateTime;

/**
 * Created by Andrew on 18.04.2017.
 */
public class ScheduleBuild extends Common {
    private Long staffID;
    private LocalDateTime eventBegin;
    private LocalDateTime eventEnd;
    private Integer interval;
    private Long workplaceID;
    private String note;

    public ScheduleBuild() {
    }

    public ScheduleBuild(Long staffID, Long workplaceID, LocalDateTime eventBegin, LocalDateTime eventEnd, Integer interval, String note) {
        this.staffID = staffID;
        this.eventBegin = eventBegin;
        this.eventEnd = eventEnd;
        this.interval = interval;
        this.workplaceID = workplaceID;
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getWorkplaceID() {
        return workplaceID;
    }

    public void setWorkplaceID(Long workplaceID) {
        this.workplaceID = workplaceID;
    }

    public Long getStaffID() {
        return staffID;
    }

    public void setStaffID(Long staffID) {
        this.staffID = staffID;
    }

    public LocalDateTime getEventBegin() {
        return eventBegin;
    }

    public void setEventBegin(LocalDateTime eventBegin) {
        this.eventBegin = eventBegin;
    }

    public LocalDateTime getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(LocalDateTime eventEnd) {
        this.eventEnd = eventEnd;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    @Override
    public void validate() throws Exception {
        if(staffID == null || staffID < 1)
            throw new Exception("Staff ID is empty");
        if(workplaceID == null || workplaceID < 0)
            throw new Exception("Workplace ID is empty");
        if(interval == null || interval < 5)
            throw new Exception("The interval should be greater than or equal to 5 minutes.");
        if(eventBegin == null || eventBegin.isBefore(LocalDateTime.now()))
            throw new Exception("Start shift is empty or less than current date and time");
        if(eventEnd == null || eventEnd.isBefore(eventBegin) || eventEnd.equals(eventBegin))
            throw new Exception("Start shift is empty or less than current date and time");

    }
}
