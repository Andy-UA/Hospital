package Beans;

/**
 * Created by Andrew on 08.04.2017.
 */
public class Diagnosis extends Common {

    private Long id;
    private String code;
    private String name;
    private String note;

    public Diagnosis(Long id, String code, String name, String note) {
        this.id = id == null ? 0 : id;
        this.code = code;
        this.name = name;
        this.note = note;
    }

    public Diagnosis() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public void validate() throws Exception {
        if(isEmptyValue(code))
            throw new Exception("Code is empty");
        if(isEmptyValue(name))
            throw new Exception("Name is empty");
    }
}
