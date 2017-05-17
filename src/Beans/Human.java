package Beans;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Andrew on 08.04.2017.
 */
public class Human extends Common {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String sex;
    private Boolean enabled;
    private String note;
    private List<Role> roles;

    public Human(Long id, String firstName, String lastName, LocalDate birthday, String sex, Boolean enabled, String note) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.sex = sex;
        this.enabled = enabled;
        this.note = note;
    }

    public Human() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString(){
        String s = "";
        if (id == null)
            return "(empty)";
        else
        if (id == -1)
            return firstName;

        s += firstName == null || firstName.isEmpty() ? "" : firstName;
        s += s.isEmpty() ? "" : " ";
        s += lastName == null || lastName.isEmpty() ? "" : lastName;
        s += birthday == null ? "" : (", birthday: " + birthday.toString());
        s += sex == null || sex.isEmpty() ? "" : (", (gender: " + sex + ")");
        s += note == null || note.isEmpty() ? "" : (", (note: " + note + ")");
        return s;
    }

    public String getBirthdayText() {
        return getDateText(birthday);
    }

    @Override
    public void validate() throws Exception {
        if(isEmptyValue(firstName))
            throw new Exception("First name is empty");
        if(isEmptyValue(lastName))
            throw new Exception("Last name is empty");
        if(isEmptyValue(birthday.toString()))
            throw new Exception("Birthday is empty");
        if(isEmptyValue(sex))
            throw new Exception("Gender is empty");
    }

    public String getRolesText() {
        String s = "";
        if (roles != null)
            for (Role role : roles)
                s += (!s.isEmpty() ? "," : "") + role.getScope();
        return s;
    }

    public String getNameText() {
        String s = "";
        s += firstName == null || firstName.isEmpty() ? "" : firstName;
        s += s.isEmpty() ? "" : " ";
        s += lastName == null || lastName.isEmpty() ? "" : lastName;
        return s;
    }
}
