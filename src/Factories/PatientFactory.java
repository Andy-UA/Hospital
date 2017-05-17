package Factories;

import Beans.Patient;
import Types.RoleCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 17.04.2017.
 */
public class PatientFactory extends CommonFactory {
    public static Patient select(Connection conn, Long patientID) throws SQLException {
        String sql = "Select a.RoleID, a.Category, a.RoleEnabled, a.RoleNote, a.FirstName, a.LastName, a.Birthday, " +
                "a.HumanEnabled, a.Sex, a.HumanID, a.HumanNote from vPatients a where a.RoleID = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, patientID);

        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return loadPatient(rs);
        }
        return null;
    }

    public static List<Patient> query(Connection conn, String firstName, String lastName, Boolean allCategories, RoleCategory category) throws SQLException {
        List<Patient> list = new ArrayList<Patient>();
        String sql = "Select a.RoleID, a.Category, a.RoleEnabled, a.RoleNote, a.FirstName, a.LastName, a.Birthday, a.HumanEnabled, a.Sex, a.HumanID, a.HumanNote " +
                "from vPatients a where a.FirstName like ?  and a.LastName like ? ";
        if (!allCategories)
            sql+= "and a.Category = " + category.ordinal() + " ";
        sql += "order by a.FirstName, a.LastName, a.HumanID, a.RoleID";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, firstName == null || firstName.isEmpty() ? "%" : firstName);
        pstm.setString(2, lastName == null || lastName.isEmpty() ? "%" : lastName);

        ResultSet rs = pstm.executeQuery();
        while(rs.next()) {
            list.add(loadPatient(rs));
        }
        return list;
    }

    private static Patient loadPatient(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setRoleID(rs.getLong("RoleID"));
        patient.setCategory(RoleCategory.fromInteger(rs.getInt("Category")));
        patient.setRoleEnabled(rs.getBoolean("RoleEnabled"));
        patient.setRoleNote(rs.getString("RoleNote"));
        patient.setFirstName(rs.getString("FirstName"));
        patient.setLastName(rs.getString("LastName"));
        patient.setBirthday(rs.getDate("Birthday").toLocalDate());
        patient.setHumanEnabled(rs.getBoolean("HumanEnabled"));
        patient.setSex(rs.getString("Sex"));
        patient.setHumanID(rs.getLong("HumanID"));
        patient.setHumanNote(rs.getString("HumanNote"));
        return patient;
    }
}
