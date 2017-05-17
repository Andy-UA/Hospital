package Beans;

/**
 * Created by Andrew on 08.04.2017.
 */
public class Account extends Common{

    private Long id;
    private Long humanID;
    private String login;
    private String password;
    private Boolean enabled;
    private String note;

    public Account(Long id, Long humanID, String login, String password, Boolean enabled, String note) {
        this.id = id;
        this.humanID = humanID;
        this.login = login;
        this.password = password;
        this.enabled = enabled;
        this.note = note;
    }

    public Account() {
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public void validate() throws Exception {
        if(isEmptyValue(login))
            throw new Exception("Login is empty");
        if(isEmptyValue(password))
            throw new Exception("Password is empty");
    }
}
