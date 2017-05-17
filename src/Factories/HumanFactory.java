package Factories;

import Beans.Human;
import Types.RoleScope;
import Data.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 11.04.2017.
 */
public class HumanFactory extends CommonFactory {
    public static Human select(Connection conn, Long id) throws Exception {
        String sql = "Select a.ID, a.FirstName, a.LastName, a.Birthday, a.Sex, a.Enabled, a.Note from Humans a where a.ID = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, id);

        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return loadHuman(conn, rs);
        }
        return null;
    }

    public static List<Human> query(Connection conn, String firstName, String lastName) throws Exception {
        List<Human> list = new ArrayList<Human>();
        String sql = "Select a.ID, a.FirstName, a.LastName, a.Birthday, a.Sex, a.Enabled, a.Note from Humans a " +
                "where a.FirstName like ? and a.LastName like ? order by a.LastName, a.FirstName";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, firstName == null || firstName.isEmpty() ? "%" : firstName);
        pstm.setString(2, lastName == null || lastName.isEmpty() ? "%" : lastName);

        ResultSet rs = pstm.executeQuery();
        while(rs.next()) {
            list.add(loadHuman(conn, rs));
        }
        return list;
    }

    private static Human loadHuman(Connection conn, ResultSet rs) throws Exception {
        Human human = new Human();
        human.setId(rs.getLong("ID"));
        human.setFirstName(rs.getString("FirstName"));
        human.setLastName(rs.getString("LastName"));
        human.setBirthday(rs.getDate("Birthday").toLocalDate());
        human.setSex(rs.getString("Sex"));
        human.setEnabled(rs.getBoolean("Enabled"));
        human.setNote(rs.getString("Note"));
        human.setRoles(RoleFactory.query(conn, human.getId(), RoleScope.Unknown, true));
        return human;
    }

    public static Boolean insert(Connection conn, Human human) throws Exception{
        String sql = "insert into Humans(FirstName, LastName, Birthday, Sex, Enabled, Note) values(?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setString(1, human.getFirstName());
        preparedStatement.setString(2, human.getLastName());
        preparedStatement.setDate(3, Date.valueOf(human.getBirthday()));
        preparedStatement.setString(4, human.getSex());
        preparedStatement.setBoolean(5, human.getEnabled());
        preparedStatement.setString(6, human.getNote());

        preparedStatement.executeUpdate();
        human.setId(Database.getID(conn, "Humans"));
        return  human.getId() > 0L;
    }

    public static Boolean update(Connection conn, Human human) throws Exception {
        String sql = "update Humans set FirstName=?, LastName=?, Birthday=?, Sex=?, Enabled=?, Note=? where ID=?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setString(1, human.getFirstName());
        preparedStatement.setString(2, human.getLastName());
        preparedStatement.setDate(3, Date.valueOf(human.getBirthday()));
        preparedStatement.setString(4, human.getSex());
        preparedStatement.setBoolean(5, human.getEnabled());
        preparedStatement.setString(6, human.getNote());
        preparedStatement.setLong(7, human.getId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static Boolean delete(Connection conn, Long id) throws Exception {
        if(id == null || id < 1)
            return false;

        String sql = "exec spHumanDelete @humanID = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setLong(1, id);

        return preparedStatement.executeUpdate() > 0;
    }

    public static Boolean enabled(Connection conn, Long id, boolean value) throws Exception {
        if(id == null || id < 1)
            return false;

        String sql = "update Humans set Enabled = ? where ID = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setBoolean(1, value);
        preparedStatement.setLong(2, id);
        return preparedStatement.executeUpdate() > 0;
    }}
