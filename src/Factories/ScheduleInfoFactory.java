package Factories;

import Beans.ScheduleInfo;
import Beans.ScheduleRequest;
import Types.ScheduleStatus;
import Types.RoleCategory;
import Types.JobScope;
import Types.ScheduleOccupiedState;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 17.04.2017.
 */
public class ScheduleInfoFactory extends CommonFactory {
    public static List<ScheduleInfo> query(Connection conn, ScheduleRequest request) throws Exception {
        List<ScheduleInfo> list = new ArrayList<>();
        String sql = "select s.* from vSchedulePatients s where s.EventBegin between ? and ? ";
        if (request.getStaffID() > 0L)
            sql += " and s.StaffID = " + request.getStaffID();
        if (request.getPatientID() > 0L)
            sql += " and s.PatientID = " + request.getPatientID();
        if (request.getScheduleStatus() != ScheduleStatus.Unknown)
            sql += " and s.Status = " + request.getScheduleStatus().ordinal();
        switch (request.getOccupiedState()){
            case Free:
                sql += " and s.PatientID is null";
                break;
            case Occupied:
                sql += " and s.PatientID is not null";
                break;
            case All:
                break;
        }
        if (request.getRoleCategory() != RoleCategory.Unknown || request.getJobScope() != JobScope.Unknown) {
            sql += " and exists(select * from vStaff f where f.RoleID = s.StaffID";
            if (request.getRoleCategory() != RoleCategory.Unknown)
                sql += " and f.Category = " + request.getRoleCategory().ordinal();
            if (request.getJobScope() != JobScope.Unknown)
                sql += " and exists(select * from Jobs j where j.RoleID = f.RoleID and j.Scope = " + request.getJobScope().ordinal() + ")";
            sql += ")";
        }
        sql += " order by s.EventBegin asc, s.EventEnd asc, s.ID asc";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setTimestamp(1, java.sql.Timestamp.valueOf(request.getEventBegin()));
        preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(request.getEventEnd()));
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            list.add(loadScheduleInfo(rs));
        }
        return list;
    }

    private static ScheduleInfo loadScheduleInfo(ResultSet rs) throws Exception {
        ScheduleInfo scheduleInfo = new ScheduleInfo();
        scheduleInfo.setId(rs.getLong("ID"));
        scheduleInfo.setStatus(ScheduleStatus.fromInteger(rs.getInt("Status")));
        scheduleInfo.setScheduleNote(rs.getString("ScheduleNote"));
        scheduleInfo.setEventBegin(rs.getTimestamp("EventBegin").toLocalDateTime());
        scheduleInfo.setEventEnd(rs.getTimestamp("EventEnd").toLocalDateTime());
        scheduleInfo.setStaffID(rs.getLong("StaffID"));
        scheduleInfo.setStaffName(rs.getString("StaffName"));
        scheduleInfo.setStaffCategory(RoleCategory.fromInteger(rs.getInt("StaffCategory")));
        scheduleInfo.setStaffNote(rs.getString("StaffNote"));
        scheduleInfo.setStaffSex(rs.getString("StaffSex"));
        scheduleInfo.setWorkplaceID(rs.getLong("WorkplaceID"));
        scheduleInfo.setBuilding(rs.getString("Building"));
        scheduleInfo.setFloor(rs.getString("Floor"));
        scheduleInfo.setRoom(rs.getString("Room"));
        scheduleInfo.setWorkplaceNote(rs.getString("WorkplaceNote"));
        scheduleInfo.setPatientID(rs.getLong("PatientID"));
        scheduleInfo.setHumanID(rs.getLong("HumanID"));
        scheduleInfo.setFirstName(rs.getString("FirstName"));
        scheduleInfo.setLastName(rs.getString("LastName"));
        scheduleInfo.setSex(rs.getString("Sex"));
        Date bd = rs.getDate("Birthday");
        if (bd != null)
            scheduleInfo.setBirthday(bd.toLocalDate());
        bd = rs.getDate("StaffBirthday");
        if (bd != null)
            scheduleInfo.setStaffBirthday(bd.toLocalDate());

        return scheduleInfo;
    }
}
