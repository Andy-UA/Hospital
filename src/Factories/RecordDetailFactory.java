package Factories;

import Beans.RecordDetail;
import Types.RecordDetailStatus;
import Data.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 11.04.2017.
 */
public class RecordDetailFactory extends CommonFactory {
    public static RecordDetail select(Connection conn, Long id) throws Exception {
        String sql = "Select a.ID, a.RecordID, a.AppointmentID, a.Status, a.Amount, a.Note from RecordDetails a where a.ID = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, id);

        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return loadRecordDetail(rs);
        }
        return null;
    }

    private static RecordDetail loadRecordDetail(ResultSet rs) throws Exception {
        RecordDetail recordDetail = new RecordDetail();
        recordDetail.setId(rs.getLong("ID"));
        recordDetail.setRecordID(rs.getLong("RecordID"));
        recordDetail.setAppointmentID(rs.getLong("AppointmentID"));
        recordDetail.setStatus(RecordDetailStatus.fromInteger(rs.getInt("Status")));
        recordDetail.setAmount(rs.getDouble("Amount"));
        recordDetail.setNote(rs.getString("Note"));
        return recordDetail;
    }

    public static Boolean insert(Connection conn, RecordDetail recordDetail) throws Exception{
        String sql = "insert into RecordDetails(RecordID, AppointmentID, Status, Amount, Note) values(?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setLong(1, recordDetail.getRecordID());
        preparedStatement.setLong(2, recordDetail.getAppointmentID());
        preparedStatement.setInt(3, recordDetail.getStatus().ordinal());
        preparedStatement.setDouble(4, recordDetail.getAmount());
        preparedStatement.setString(5, recordDetail.getNote());

        preparedStatement.executeUpdate();
        recordDetail.setId(Database.getID(conn, "RecordDetails"));
        return  recordDetail.getId() > 0L;
    }

    public static Boolean update(Connection conn, RecordDetail recordDetail) throws Exception {
        String sql = "update RecordDetails set RecordID=?, AppointmentID=?, Status=?, Amount=?, Note=? where ID=?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setLong(1, recordDetail.getRecordID());
        preparedStatement.setLong(2, recordDetail.getAppointmentID());
        preparedStatement.setInt(3, recordDetail.getStatus().ordinal());
        preparedStatement.setDouble(4, recordDetail.getAmount());
        preparedStatement.setString(5, recordDetail.getNote());
        preparedStatement.setLong(6, recordDetail.getId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static Boolean delete(Connection conn, Long id) throws Exception {
        if(id == null || id < 1)
            return false;

        String sql = "delete from RecordDetails where ID=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setLong(1, id);

        return preparedStatement.executeUpdate() > 0;
    }

    public static List<RecordDetail> query(Connection conn, Long recordID) throws Exception {
        List<RecordDetail> list = new ArrayList<RecordDetail>();
        String sql = "select a.ID, a.RecordID, a.AppointmentID, a.Status, a.Amount, a.Note from RecordDetails a where a.RecordID = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, recordID);

        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            list.add(loadRecordDetail(rs));
        }
        return list;
    }
}
