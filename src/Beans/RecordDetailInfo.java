package Beans;

import Types.AppointmentType;
import Types.RecordDetailStatus;

import java.util.List;

/**
 * Created by andre on 22.04.2017.
 */
public class RecordDetailInfo extends Common {
    private Long id;
    private Long recordID;
    private Long appointmentID;
    private String appointmentDescription;
    private String appointmentUnit;
    private AppointmentType appointmentType;
    private String appointmentNote;
    private RecordDetailStatus status;
    private Double amount;
    private String detailNote;
    private List<Treatment> treatments;

    public RecordDetailInfo() {
    }

    public RecordDetailInfo(Long id, Long recordID, Long appointmentID, String appointmentDescription, String appointmentUnit, AppointmentType appointmentType, String appointmentNote, RecordDetailStatus status, Double amount, String detailNote) {
        this.id = id;
        this.recordID = recordID;
        this.appointmentID = appointmentID;
        this.appointmentDescription = appointmentDescription;
        this.appointmentUnit = appointmentUnit;
        this.appointmentType = appointmentType;
        this.appointmentNote = appointmentNote;
        this.status = status;
        this.amount = amount;
        this.detailNote = detailNote;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecordID() {
        return recordID;
    }

    public void setRecordID(Long recordID) {
        this.recordID = recordID;
    }

    public Long getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(Long appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getAppointmentDescription() {
        return appointmentDescription;
    }

    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }

    public String getAppointmentUnit() {
        return appointmentUnit;
    }

    public void setAppointmentUnit(String appointmentUnit) {
        this.appointmentUnit = appointmentUnit;
    }

    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getAppointmentNote() {
        return appointmentNote;
    }

    public void setAppointmentNote(String appointmentNote) {
        this.appointmentNote = appointmentNote;
    }

    public RecordDetailStatus getStatus() {
        return status;
    }

    public void setStatus(RecordDetailStatus status) {
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDetailNote() {
        return detailNote;
    }

    public void setDetailNote(String detailNote) {
        this.detailNote = detailNote;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<Treatment> treatments) {
        this.treatments = treatments;
    }

    @Override
    public String toString(){
        String s = (isEmptyValue(appointmentDescription) ? "(no appointment)" : appointmentDescription)
                 + (isEmptyValue(detailNote) ? "" : (" (" + detailNote + ")"));
        return s;
    }
}
