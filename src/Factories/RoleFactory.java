package Factories;

import Beans.Role;
import Types.RoleCategory;
import Types.RoleScope;
import Data.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 11.04.2017.
 */
public class RoleFactory extends CommonFactory {
    public static Role select(Connection conn, Long id) throws Exception {
        String sql = "Select a.ID, a.Scope, a.Category, a.HumanID, a.Enabled, a.Note from Roles a where a.ID = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, id);

        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return loadRole(conn, rs);
        }
        return null;
    }

    public static List<Role> query(Connection conn, Long humanID, RoleScope scope, boolean enabled)  throws Exception{
        List<Role> list = new ArrayList<Role>();

        String sql = "Select a.ID, a.Scope, a.Category, a.HumanID, a.Enabled, a.Note from Roles a where a.HumanID = ? and a.Enabled = ? ";
        if (scope != RoleScope.Unknown)
            sql += "and a.Scope = " + scope.ordinal() + " ";
        sql += "order by a.Scope, a.ID";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setLong(1, humanID);
        preparedStatement.setBoolean(2, enabled);

        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            list.add(loadRole(conn, rs));
        }
        return list;
    }

    private static Role loadRole(Connection conn, ResultSet rs) throws Exception {
        Role role = new Role();
        role.setId(rs.getLong("ID"));
        role.setScope(RoleScope.fromInteger(rs.getInt("Scope")));
        role.setCategory(RoleCategory.fromInteger(rs.getInt("Category")));
        role.setHumanID(rs.getLong("HumanID"));
        role.setEnabled(rs.getBoolean("Enabled"));
        role.setNote(rs.getString("Note"));
        role.setJobs(JobFactory.query(conn, role.getId()));
        return role;
    }

    public static Boolean insert(Connection conn, Role role) throws Exception{
        String sql = "insert into Roles(Scope, Category, HumanID, Enabled, Note) values(?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setInt(1, role.getScope().ordinal());
        preparedStatement.setInt(2, role.getCategory().ordinal());
        preparedStatement.setLong(3, role.getHumanID());
        preparedStatement.setBoolean(4, role.getEnabled());
        preparedStatement.setString(5, role.getNote());

        preparedStatement.executeUpdate();
        role.setId(Database.getID(conn, "Roles"));
        return  role.getId() > 0L;
    }

    public static Boolean update(Connection conn, Role role) throws Exception {
        String sql = "update Roles set Scope=?, Category=?, HumanID=?, Enabled=?, Note=? where ID=?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setInt(1, role.getScope().ordinal());
        preparedStatement.setInt(2, role.getCategory().ordinal());
        preparedStatement.setLong(3, role.getHumanID());
        preparedStatement.setBoolean(4, role.getEnabled());
        preparedStatement.setString(5, role.getNote());
        preparedStatement.setLong(6, role.getId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static Boolean delete(Connection conn, Long id) throws Exception {
        if(id == null || id < 1)
            return false;

        String sql = "delete from Roles where ID=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setLong(1, id);

        return preparedStatement.executeUpdate() > 0;
    }

    public static Boolean enabledRole(Connection conn, Long humanID, RoleScope scope, Boolean enabled, String note) throws Exception {
        String sql = "update Roles set Enabled=?, Note=? where HumanID=? and Scope=?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setBoolean(1, enabled);
        preparedStatement.setString(2, note);
        preparedStatement.setLong(3, humanID);
        preparedStatement.setInt(4, scope.ordinal());

        return preparedStatement.executeUpdate() > 0;
    }
}
