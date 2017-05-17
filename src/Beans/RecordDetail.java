package Beans;

import Types.RecordDetailStatus;

/**
 * Created by Andrew on 08.04.2017.
 */
public class RecordDetail extends Common {

    private Long id;
    private Long recordID;
    private Long appointmentID;
    private RecordDetailStatus status;
    private Double amount;
    private String note;


    public RecordDetail() {
    }

    public RecordDetail(Long id, Long recordID, Long appointmentID, RecordDetailStatus status, Double amount, String note) {
        this.id = id;
        this.recordID = recordID;
        this.appointmentID = appointmentID;
        this.status = status;
        this.amount = amount;
        this.note = note;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public void validate() throws Exception {
        if(isEmptyValue(amount.toString()))
            throw new Exception("Amount is empty");

    }
}
