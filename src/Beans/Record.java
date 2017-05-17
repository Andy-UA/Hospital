package Beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Andrew on 08.04.2017.
 */
public class Record extends Common {

    private Long id;
    private LocalDateTime eventDate;
    private Long diagnosisID;
    private Long scheduleID;
    private String note;


    public Record() {
    }

    public Record(Long id, LocalDateTime eventDate, Long diagnosisID, Long scheduleID, String note) {
        this.id = id;
        this.eventDate = eventDate;
        this.diagnosisID = diagnosisID;
        this.scheduleID = scheduleID;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public Long getDiagnosisID() {
        return diagnosisID;
    }

    public void setDiagnosisID(Long diagnosisID) {
        this.diagnosisID = diagnosisID;
    }

    public Long getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(Long scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public void validate() throws Exception {
        if(isEmptyValue(eventDate.toString()))
            throw new Exception("Event date is empty");
    }

    public String getEventDateText() {
        return getDateTimeText(eventDate);
    }
}
