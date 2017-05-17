package Factories;

import Beans.Treatment;
import Types.TreatmentStatus;
import Data.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 11.04.2017.
 */
public class TreatmentFactory extends CommonFactory {
    public static Treatment select(Connection conn, Long id) throws Exception {
        String sql = "Select a.ID, a.DetailID, a.EventDate, a.Amount, a.Status, a.Note from Treatments a where a.ID = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, id);

        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return loadTreatment(rs);
        }
        return null;
    }

    public static List<Treatment> query(Connection conn, Long detailID) throws Exception {
        List<Treatment> list = new ArrayList<Treatment>();
        String sql = "Select a.ID, a.DetailID, a.EventDate, a.Amount, a.Status, a.Note from Treatments a where a.DetailID = ? order by a.EventDate, a.ID";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, detailID);

        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            list.add(loadTreatment(rs));
        }
        return list;
    }

    private static Treatment loadTreatment(ResultSet rs) throws Exception {
        Treatment treatment = new Treatment();
        treatment.setId(rs.getLong("ID"));
        treatment.setDetailID(rs.getLong("DetailID"));
        treatment.setEventDate(rs.getTimestamp("EventDate").toLocalDateTime());
        treatment.setAmount(rs.getDouble("Amount"));
        treatment.setStatus(TreatmentStatus.fromInteger(rs.getInt("Status")));
        treatment.setNote(rs.getString("Note"));
        return treatment;
    }

    public static Boolean insert(Connection conn, Treatment treatment) throws Exception{
        String sql = "insert into Treatments(DetailID, EventDate, Amount, Status, Note) values(?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setLong(1, treatment.getDetailID());
        preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(treatment.getEventDate()));
        preparedStatement.setDouble(3, treatment.getAmount());
        preparedStatement.setInt(4, treatment.getStatus().ordinal());
        preparedStatement.setString(5, treatment.getNote());

        preparedStatement.executeUpdate();
        treatment.setId(Database.getID(conn, "Treatments"));
        return  treatment.getId() > 0L;
    }

    public static Boolean update(Connection conn, Treatment treatment) throws Exception {
        String sql = "update Treatments set DetailID=?, EventDate=?, Amount=?, Status=?, Note=? where ID=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setLong(1, treatment.getDetailID());
        preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(treatment.getEventDate()));
        preparedStatement.setDouble(3, treatment.getAmount());
        preparedStatement.setInt(4, treatment.getStatus().ordinal());
        preparedStatement.setString(5, treatment.getNote());
        preparedStatement.setLong(6, treatment.getId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static Boolean delete(Connection conn, Long id) throws Exception {
        if(id == null || id < 1)
            return false;
        String sql = "delete from Treatments where ID=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        return preparedStatement.executeUpdate() > 0;
    }
}
