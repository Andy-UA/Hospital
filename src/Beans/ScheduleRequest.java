package Beans;

import Types.JobScope;
import Types.RoleCategory;
import Types.ScheduleOccupiedState;
import Types.ScheduleStatus;

import java.time.LocalDateTime;

/**
 * Created by andre on 19.04.2017.
 */
public class ScheduleRequest extends Common {
    private Long staffID = 0L;
    private Long patientID = 0L;
    private JobScope jobScope = JobScope.Therapist;
    private RoleCategory roleCategory = RoleCategory.Doctors;
    private LocalDateTime eventBegin;
    private LocalDateTime eventEnd;
    private ScheduleStatus scheduleStatus = ScheduleStatus.Open;
    private ScheduleOccupiedState occupiedState = ScheduleOccupiedState.All;

    public LocalDateTime getEventBegin() {
        return eventBegin;
    }

    public void setEventBegin(LocalDateTime eventBegin) {
        this.eventBegin = eventBegin;
    }

    public LocalDateTime getEventEnd() {
        return eventEnd;
    }

    public void setEventEnd(LocalDateTime eventEnd) {
        this.eventEnd = eventEnd;
    }

    public Long getStaffID() {
        return staffID;
    }

    public void setStaffID(Long staffID) {
        this.staffID = staffID;
    }

    public Long getPatientID() {
        return patientID;
    }

    public void setPatientID(Long patientID) {
        this.patientID = patientID;
    }

    public JobScope getJobScope() {
        return jobScope;
    }

    public void setJobScope(JobScope jobScope) {
        this.jobScope = jobScope;
    }

    public RoleCategory getRoleCategory() {
        return roleCategory;
    }

    public void setRoleCategory(RoleCategory roleCategory) {
        this.roleCategory = roleCategory;
    }

    public ScheduleStatus getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(ScheduleStatus scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    public ScheduleOccupiedState getOccupiedState() {
        return occupiedState;
    }

    public void setOccupiedState(ScheduleOccupiedState occupiedState) {
        this.occupiedState = occupiedState;
    }
}
