package Factories;

import Beans.Record;
import Data.Database;

import java.sql.*;

/**
 * Created by Andrew on 11.04.2017.
 */
public class RecordFactory extends CommonFactory {
    public static Record select(Connection conn, Long id) throws Exception {
        String sql = "Select a.ID, a.EventDate, a.DiagnosisID, a.ScheduleID, a.Note from Records a where a.ID = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, id);

        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            Record record = new Record();
            record.setId(rs.getLong("ID"));
            record.setEventDate(rs.getTimestamp("EventDate").toLocalDateTime());
            record.setDiagnosisID(rs.getLong("DiagnosisID"));
            record.setScheduleID(rs.getLong("ScheduleID"));
            record.setNote(rs.getString("Note"));
            return record;
        }
        return null;
    }

    public static Boolean insert(Connection conn, Record record) throws Exception{
        String sql = "insert into Records(EventDate, DiagnosisID, ScheduleID, Note) values(?, ?, ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setTimestamp(1, java.sql.Timestamp.valueOf(record.getEventDate()));
        preparedStatement.setLong(2, record.getDiagnosisID());
        preparedStatement.setLong(3, record.getScheduleID());
        preparedStatement.setString(4, record.getNote());

        preparedStatement.executeUpdate();
        record.setId(Database.getID(conn, "Records"));
        return  record.getId() > 0L;
    }

    public static Boolean update(Connection conn, Record record) throws Exception {
        String sql = "update Records set EventDate=?, DiagnosisID=?, ScheduleID=?, Note=? where ID=?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setTimestamp(1, java.sql.Timestamp.valueOf(record.getEventDate()));
        preparedStatement.setLong(2, record.getDiagnosisID());
        preparedStatement.setLong(3, record.getScheduleID());
        preparedStatement.setString(4, record.getNote());
        preparedStatement.setLong(5, record.getId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static Boolean delete(Connection conn, Long id) throws Exception {
        if(id == null || id < 1)
            return false;

        String sql = "delete from Records where ID=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setLong(1, id);

        return preparedStatement.executeUpdate() > 0;
    }
}
