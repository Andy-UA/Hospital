package Beans;

import Types.RoleCategory;
import Types.RoleScope;

import java.util.List;

/**
 * Created by Andrew on 08.04.2017.
 */
public class Role extends Common {

    private Long id;
    private RoleScope scope;
    private RoleCategory category;
    private Long humanID;
    private Boolean enabled;
    private String note;
    private List<Job> jobs;

    public Role() {
    }

    public Role(Long id, RoleScope scope, RoleCategory category, Long humanID, Boolean enabled, String note, List<Job> jobs) {
        this.id = id;
        this.scope = scope;
        this.category = category;
        this.humanID = humanID;
        this.enabled = enabled;
        this.note = note;
        this.jobs = jobs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleScope getScope() {
        return scope;
    }

    public void setScope(RoleScope scope) {
        this.scope = scope;
    }

    public RoleCategory getCategory() {
        return category;
    }

    public void setCategory(RoleCategory category) {
        this.category = category;
    }

    public Long getHumanID() {
        return humanID;
    }

    public void setHumanID(Long humanID) {
        this.humanID = humanID;
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

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    @Override
    public String toString(){
        return scope.toString() + (enabled ? "" : " (disabled)");
    }
}
