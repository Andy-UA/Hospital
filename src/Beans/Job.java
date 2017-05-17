package Beans;

import Types.JobScope;

/**
 * Created by andre on 06.05.2017.
 */
public class Job {
    private Long id;
    private Long roleID;
    private JobScope scope;
    private String note;

    public Job() {
    }

    public Job(Long id, Long roleID, JobScope scope, String note) {
        this.id = id;
        this.roleID = roleID;
        this.scope = scope;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleID() {
        return roleID;
    }

    public void setRoleID(Long roleID) {
        this.roleID = roleID;
    }

    public JobScope getScope() {
        return scope;
    }

    public void setScope(JobScope scope) {
        this.scope = scope;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", roleID=" + roleID +
                ", scope=" + scope +
                ", note='" + note + '\'' +
                '}';
    }
}
