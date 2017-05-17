package Factories;

import Beans.Schedule;
import Beans.ScheduleBuild;
import Types.ScheduleStatus;
import Data.Database;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by Andrew on 11.04.2017.
 */
public class ScheduleFactory extends CommonFactory {
    public static Schedule select(Connection conn, Long id) throws Exception {
        String sql = "Select a.ID, a.StaffID, a.PatientID, a.WorkplaceID, a.EventBegin, a.EventEnd, " +
                                            "a.Status, a.Note from Schedules a where a.ID = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, id);

        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            Schedule schedule = new Schedule();
            schedule.setId(rs.getLong("ID"));
            schedule.setStaffID(rs.getLong("StaffID"));
            schedule.setPatientID(rs.getLong("PatientID"));
            schedule.setWorkplaceID(rs.getLong("WorkplaceID"));
            schedule.setEventBegin(rs.getTimestamp("EventBegin").toLocalDateTime());
            schedule.setEventEnd(rs.getTimestamp("EventEnd").toLocalDateTime());
            schedule.setStatus(ScheduleStatus.fromInteger(rs.getInt("Status")));
            schedule.setNote(rs.getString("Note"));
            return schedule;
        }
        return null;
    }

    public static Boolean insert(Connection conn, Schedule schedule) throws Exception{
        String sql = "insert into Schedules(StaffID, PatientID, WorkplaceID, EventBegin, EventEnd, Status, Note) values(?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setLong(1, schedule.getStaffID());
        if (schedule.getPatientID() != null)
            preparedStatement.setLong(2, schedule.getPatientID());
        else
            preparedStatement.setNull(2, Types.BIGINT);
        preparedStatement.setLong(3, schedule.getWorkplaceID());
        preparedStatement.setTimestamp(4, java.sql.Timestamp.valueOf(schedule.getEventBegin()));
        preparedStatement.setTimestamp(5, java.sql.Timestamp.valueOf(schedule.getEventEnd()));
        preparedStatement.setInt(6, schedule.getStatus().ordinal());
        preparedStatement.setString(7, schedule.getNote());

        preparedStatement.executeUpdate();
        schedule.setId(Database.getID(conn, "Schedules"));
        return  schedule.getId() > 0L;
    }

    public static Boolean update(Connection conn, Schedule schedule) throws Exception {
        String sql = "update Schedules set StaffID=?, PatientID=?, WorkplaceID=?, EventBegin=?, EventEnd=?, Status=?, Note=? where ID=?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setLong(1, schedule.getStaffID());
        if (schedule.getPatientID() != null)
            preparedStatement.setLong(2, schedule.getPatientID());
        else
            preparedStatement.setNull(2, Types.BIGINT);
        preparedStatement.setLong(3, schedule.getWorkplaceID());
        preparedStatement.setTimestamp(4, java.sql.Timestamp.valueOf(schedule.getEventBegin()));
        preparedStatement.setTimestamp(5, java.sql.Timestamp.valueOf(schedule.getEventEnd()));
        preparedStatement.setInt(6, schedule.getStatus().ordinal());
        preparedStatement.setString(7, schedule.getNote());
        preparedStatement.setLong(8, schedule.getId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static Boolean delete(Connection conn, Long id) throws Exception {
        if(id == null || id < 1)
            return false;

        String sql = "delete from Schedules where ID=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setLong(1, id);

        return preparedStatement.executeUpdate() > 0;
    }

    public static void build(Connection conn, ScheduleBuild scheduleBuild) throws Exception {
        if(scheduleBuild == null)
            throw new Exception("ScheduleBuild is empty");
        scheduleBuild.validate();
        if (checkRows(conn, scheduleBuild) == 0) {
            LocalDateTime current = scheduleBuild.getEventBegin();
            while (current.isBefore(scheduleBuild.getEventEnd())) {
                LocalDateTime plus = current.plus(scheduleBuild.getInterval(), ChronoUnit.MINUTES);
                Schedule schedule = new Schedule(0l, scheduleBuild.getStaffID(), scheduleBuild.getWorkplaceID(), null, current, plus, ScheduleStatus.Open, scheduleBuild.getNote());
                insert(conn, schedule);
                current = plus;
            }
        }
    }

    public static int checkRows(Connection conn, ScheduleBuild scheduleBuild) throws Exception{
        String sql = "select count(*) as items from Schedules where StaffID = ? and EventBegin >= ? and EventEnd <= ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, scheduleBuild.getStaffID());
        pstm.setTimestamp(2, java.sql.Timestamp.valueOf(scheduleBuild.getEventBegin()));
        pstm.setTimestamp(3, java.sql.Timestamp.valueOf(scheduleBuild.getEventEnd()));
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return rs.getInt("items");
        }
        return 0;
    }

    public static boolean clear(Connection conn, ScheduleBuild scheduleBuild) throws Exception{
        String sql = "delete from Schedules where StaffID = ? and EventBegin >= ? and EventEnd <= ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, scheduleBuild.getStaffID());
        pstm.setTimestamp(2, java.sql.Timestamp.valueOf(scheduleBuild.getEventBegin()));
        pstm.setTimestamp(3, java.sql.Timestamp.valueOf(scheduleBuild.getEventEnd()));
        return pstm.execute();
    }
}
