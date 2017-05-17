package Beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by andre on 20.04.2017.
 */
public class RecordInfo extends Common {
    private Long id;
    private  String recordNote;
    private LocalDateTime eventDate;
    private Long scheduleID;
    private Long staffID;
    private Long patientID;
    private Long diagnosisID;
    private  String diagnosisCode;
    private  String diagnosisName;
    private  String diagnosisNote;
    private List<RecordDetailInfo> details;

    public RecordInfo() {
    }

    public RecordInfo(Long id, Long staffID, Long patientID, LocalDateTime eventDate, String recordNote, Long scheduleID, Long diagnosisID, String diagnosisCode, String diagnosisName, String diagnosisNote) {
        this.id = id;
        this.patientID = patientID;
        this.staffID = staffID;
        this.eventDate = eventDate;
        this.recordNote = recordNote;
        this.scheduleID = scheduleID;
        this.diagnosisID = diagnosisID;
        this.diagnosisCode = diagnosisCode;
        this.diagnosisName = diagnosisName;
        this.diagnosisNote = diagnosisNote;
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

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public String getEventDateText() {
        return getDateTimeText(eventDate);
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getRecordNote() {
        return recordNote;
    }

    public void setRecordNote(String recordNote) {
        this.recordNote = recordNote;
    }

    public Long getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(Long scheduleID) {
        this.scheduleID = scheduleID;
    }

    public Long getDiagnosisID() {
        return diagnosisID;
    }

    public void setDiagnosisID(Long diagnosisID) {
        this.diagnosisID = diagnosisID;
    }

    public String getDiagnosisName() {
        return diagnosisName;
    }

    public String getDiagnosisCode() {
        return diagnosisCode;
    }

    public void setDiagnosisCode(String diagnosisCode) {
        this.diagnosisCode = diagnosisCode;
    }

    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
    }

    public String getDiagnosisNote() {
        return diagnosisNote;
    }

    public void setDiagnosisNote(String diagnosisNote) {
        this.diagnosisNote = diagnosisNote;
    }

    public List<RecordDetailInfo> getDetails() {
        return details;
    }

    public void setDetails(List<RecordDetailInfo> details) {
        this.details = details;
    }

    @Override
    public String toString(){
        String s = getEventDateText()
                + (isEmptyValue(diagnosisName) ? " (no diagnosis)" : (" " + diagnosisName))
                + (isEmptyValue(diagnosisNote) ? "" : (" (" + diagnosisNote + ")"));
        return s;
    }
}
