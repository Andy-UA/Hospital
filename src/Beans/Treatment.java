package Beans;

import Types.TreatmentStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Andrew on 08.04.2017.
 */
public class Treatment extends Common {

    private Long id;
    private Long detailID;
    private LocalDateTime eventDate;
    private Double amount;
    private TreatmentStatus status;
    private String note;

    public Treatment(Long id, Long detailID, LocalDateTime eventDate, Double amount, TreatmentStatus status, String note) {
        this.id = id;
        this.detailID = detailID;
        this.eventDate = eventDate;
        this.amount = amount;
        this.status = status;
        this.note = note;
    }

    public Treatment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDetailID() {
        return detailID;
    }

    public void setDetailID(Long detailID) {
        this.detailID = detailID;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TreatmentStatus getStatus() {
        return status;
    }

    public void setStatus(TreatmentStatus status) {
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
        if(isEmptyValue(eventDate.toString()))
            throw new Exception("Event date is empty");
        if(isEmptyValue(amount.toString()))
            throw new Exception("Amount end is empty");

    }

    public String getEventDateText() {
        return getDateTimeText(eventDate);
    }
}
