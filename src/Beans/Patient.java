package Beans;

import Types.RoleCategory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by Andrew on 17.04.2017.
 */
public class Patient extends Common {
    private Long roleID;
    private RoleCategory category;
    private Boolean roleEnabled;
    private String roleNote;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Boolean humanEnabled;
    private String sex;
    private Long humanID;
    private String humanNote;

    public Patient(Long roleID, RoleCategory category, Boolean roleEnabled, String roleNote, String firstName, String lastName, LocalDate birthday, Boolean humanEnabled, String sex, Long humanID, String humanNote) {
        this.roleID = roleID;
        this.category = category;
        this.roleEnabled = roleEnabled;
        this.roleNote = roleNote;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.humanEnabled = humanEnabled;
        this.sex = sex;
        this.humanID = humanID;
        this.humanNote = humanNote;
    }

    public Patient() {
    }

    public Long getRoleID() {
        return roleID;
    }

    public void setRoleID(Long roleID) {
        this.roleID = roleID;
    }

    public RoleCategory getCategory() {
        return category;
    }

    public void setCategory(RoleCategory category) {
        this.category = category;
    }

    public Boolean getRoleEnabled() {
        return roleEnabled;
    }

    public void setRoleEnabled(Boolean roleEnabled) {
        this.roleEnabled = roleEnabled;
    }

    public String getRoleNote() {
        return roleNote;
    }

    public void setRoleNote(String roleNote) {
        this.roleNote = roleNote;
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

    public Boolean getHumanEnabled() {
        return humanEnabled;
    }

    public void setHumanEnabled(Boolean humanEnabled) {
        this.humanEnabled = humanEnabled;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Long getHumanID() {
        return humanID;
    }

    public void setHumanID(Long humanID) {
        this.humanID = humanID;
    }

    public String getHumanNote() {
        return humanNote;
    }

    public void setHumanNote(String humanNote) {
        this.humanNote = humanNote;
    }

    @Override
    public String toString(){
        String s = "";
        if (roleID == null)
            return "(empty)";
        else
        if (roleID == -1)
            return firstName;

        s += firstName == null || firstName.isEmpty() ? "" : firstName;
        s += s.isEmpty() ? "" : " ";
        s += lastName == null || lastName.isEmpty() ? "" : lastName;
        s += birthday == null ? "" : (", birthday: " + birthday.toString());
        s += sex == null || sex.isEmpty() ? "" : (", (gender: " + sex + ")");
        s += humanNote == null || humanNote.isEmpty() ? "" : (", (note: " + humanNote + ")");
        return s;
    }

    public String getNameText() {
        String s = "";
        s += firstName == null || firstName.isEmpty() ? "" : firstName;
        s += s.isEmpty() ? "" : " ";
        s += lastName == null || lastName.isEmpty() ? "" : lastName;
        return s;
    }

    public String getBirthdayText() {
        return getDateText(birthday);
    }

}