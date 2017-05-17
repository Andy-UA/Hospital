package Beans;

import Types.DocumentType;

/**
 * Created by Andrew on 08.04.2017.
 */
public class Document extends Common {

    private Long id;
    private Long humanID;
    private DocumentType type;
    private String value;
    private String note;

    public Document(Long id, Long humanID, DocumentType type, String value, String note) {
        this.id = id;
        this.humanID = humanID;
        this.type = type;
        this.value = value;
        this.note = note;
    }

    public Document() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHumanID() {
        return humanID;
    }

    public void setHumanID(Long humanID) {
        this.humanID = humanID;
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public void validate() throws Exception {
        if(isEmptyValue(value))
            throw new Exception("Value is empty");
    }

    @Override
    public String toString(){
        String s = type.toString();
        s += s.isEmpty() ? "" : ", ";
        s += isEmptyValue(value) ? "" : value;
        return s;
    }
}
