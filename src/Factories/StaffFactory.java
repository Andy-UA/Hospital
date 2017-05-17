package Factories;

import Beans.Staff;
import Types.RoleCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 15.04.2017.
 */
public class StaffFactory extends CommonFactory {
    public static Staff select(Connection conn, Long staffID) throws Exception {
        String sql = "Select a.RoleID, a.Category, a.RoleEnabled, a.RoleNote, a.FirstName, a.LastName, a.Birthday, " +
                                    "a.HumanEnabled, a.Sex, a.HumanID, a.HumanNote from vStaff a where a.RoleID = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, staffID);

        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return loadStaff(rs);
        }
        return null;
    }

    public static List<Staff> query(Connection conn, String firstName, String lastName, Boolean allCategories, RoleCategory category) throws Exception {
        List<Staff> list = new ArrayList<Staff>();
        String sql = "Select a.RoleID, a.Category, a.RoleEnabled, a.RoleNote, a.FirstName, a.LastName, a.Birthday, a.HumanEnabled, a.Sex, a.HumanID, a.HumanNote " +
                "from vStaff a where a.FirstName like ?  and a.LastName like ? ";
        if (!allCategories)
          sql += "and a.Category = " + category.ordinal() + " ";
        sql += "order by a.FirstName, a.LastName, a.HumanID, a.RoleID";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, firstName == null || firstName.isEmpty() ? "%" : firstName);
        pstm.setString(2, lastName == null || lastName.isEmpty() ? "%" : lastName);

        ResultSet rs = pstm.executeQuery();
        while(rs.next()) {
            list.add(loadStaff(rs));
        }
        return list;
    }

    private static Staff loadStaff(ResultSet rs) throws Exception {
        Staff staff = new Staff();
        staff.setRoleID(rs.getLong("RoleID"));
        staff.setCategory(RoleCategory.fromInteger(rs.getInt("Category")));
        staff.setRoleEnabled(rs.getBoolean("RoleEnabled"));
        staff.setRoleNote(rs.getString("RoleNote"));
        staff.setFirstName(rs.getString("FirstName"));
        staff.setLastName(rs.getString("LastName"));
        staff.setBirthday(rs.getDate("Birthday").toLocalDate());
        staff.setHumanEnabled(rs.getBoolean("HumanEnabled"));
        staff.setSex(rs.getString("Sex"));
        staff.setHumanID(rs.getLong("HumanID"));
        staff.setHumanNote(rs.getString("HumanNote"));
        return staff;
    }
}
