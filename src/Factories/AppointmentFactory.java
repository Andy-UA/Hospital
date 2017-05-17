package Factories;

import Beans.Appointment;
import Types.AppointmentType;
import Data.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 11.04.2017.
 */
public class AppointmentFactory extends CommonFactory {
    public static Appointment select(Connection conn, Long id) throws SQLException {
        String sql = "Select a.ID, a.Type, a.Description, a.Unit, a.Note from Appointments a where a.ID = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, id);

        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            Appointment appointment = new Appointment();
            appointment.setId(id);
            appointment.setType(AppointmentType.fromInteger(rs.getInt("Type")));
            appointment.setDescription(rs.getString("Description"));
            appointment.setUnit(rs.getString("Unit"));
            appointment.setNote(rs.getString("Note"));
            return appointment;
        }
        return null;
    }

    public static List<Appointment> query(Connection conn, String description, Boolean allCategories, AppointmentType type) throws SQLException {
        List<Appointment> list = new ArrayList<Appointment>();
        String sql = "Select a.ID, a.Type, a.Description, a.Unit, a.Note from Appointments a " +
                "where a.Description like ? ";
        if(!allCategories)
            sql += "and a.Type = " + type.ordinal() + " ";
        sql += "order by a.Type, a.Description, a.ID";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, description == null || description.isEmpty() ? "%" : description);

        ResultSet rs = pstm.executeQuery();
        while(rs.next()) {
            Appointment appointment = new Appointment();
            appointment.setId(rs.getLong("ID"));
            appointment.setType(AppointmentType.fromInteger(rs.getInt("Type")));
            appointment.setDescription(rs.getString("Description"));
            appointment.setUnit(rs.getString("Unit"));
            appointment.setNote(rs.getString("Note"));
            list.add(appointment);
        }
        return list;
    }

    public static Boolean insert(Connection conn, Appointment appointment) throws SQLException{
        String sql = "insert into Appointments(Type, Description, Unit, Note) values(?, ?, ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setInt(1, appointment.getType().ordinal());
        preparedStatement.setString(2, appointment.getDescription());
        preparedStatement.setString(3, appointment.getUnit());
        preparedStatement.setString(4, appointment.getNote());

        preparedStatement.executeUpdate();
        appointment.setId(Database.getID(conn, "Appointments"));
        return  appointment.getId() > 0L;
    }

    public static Boolean update(Connection conn, Appointment appointment) throws SQLException {
        String sql = "update Appointments set Type=?, Description=?, Unit=?, Note=? where ID=?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setInt(1, appointment.getType().ordinal());
        preparedStatement.setString(2, appointment.getDescription());
        preparedStatement.setString(3, appointment.getUnit());
        preparedStatement.setString(4, appointment.getNote());
        preparedStatement.setLong(5, appointment.getId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static Boolean delete(Connection conn, Long id) throws SQLException {
        if(id == null || id < 1)
            return false;

        String sql = "delete from Appointments where ID=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setLong(1, id);

        return preparedStatement.executeUpdate() > 0;
    }
}
