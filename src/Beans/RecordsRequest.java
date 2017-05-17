package Beans;

import java.time.LocalDateTime;

/**
 * Created by andre on 20.04.2017.
 */
public class RecordsRequest extends Common {
    private Long patientID = 0L;
    private Long staffID = 0L;
    private LocalDateTime eventBegin;
    private LocalDateTime eventEnd;

    public Long getPatientID() {
        return patientID;
    }

    public void setPatientID(Long patientID) {
        this.patientID = patientID;
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
}
