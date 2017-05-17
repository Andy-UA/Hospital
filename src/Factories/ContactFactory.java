package Factories;

import Beans.Contact;
import Types.ContactType;
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
public class ContactFactory extends CommonFactory {
    public static Contact select(Connection conn, Long id) throws SQLException {

        String sql = "Select a.ID, a.HumanID, a.Type, a.Value, a.Note from Contacts a where a.ID = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, id);

        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            Contact contact = new Contact();
            contact.setId(rs.getLong("ID"));
            contact.setHumanID(rs.getLong("HumanID"));
            contact.setType(ContactType.fromInteger(rs.getInt("Type")));
            contact.setValue(rs.getString("Value"));
            contact.setNote(rs.getString("Note"));
            return contact;
        }
        return null;
    }

    public static List<Contact> query(Connection conn, Long humanid) throws SQLException {
        List<Contact> list = new ArrayList<>();
        String sql = "Select a.ID, a.HumanID, a.Type, a.Value, a.Note from Contacts a where a.HumanID = ? order by a.Type, a.ID";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, humanid);

        ResultSet rs = pstm.executeQuery();
        while(rs.next()) {
            Contact contact = new Contact();
            contact.setId(rs.getLong("ID"));
            contact.setHumanID(rs.getLong("HumanID"));
            contact.setType(ContactType.fromInteger(rs.getInt("Type")));
            contact.setValue(rs.getString("Value"));
            contact.setNote(rs.getString("Note"));
            list.add(contact);
        }
        return list;
    }

    public static Boolean insert(Connection conn, Contact contact) throws SQLException{
        String sql = "insert into Contacts(HumanID, Type, Value, Note) values(?, ?, ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setLong(1, contact.getHumanID());
        preparedStatement.setInt(2, contact.getType().ordinal());
        preparedStatement.setString(3, contact.getValue());
        preparedStatement.setString(4, contact.getNote());

        preparedStatement.executeUpdate();
        contact.setId(Database.getID(conn, "Contacts"));
        return  contact.getId() > 0L;
    }

    public static Boolean update(Connection conn, Contact contact) throws SQLException {
        String sql = "update Contacts set HumanID=?, Type=?, Value=?, Note=? where ID=?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setLong(1, contact.getHumanID());
        preparedStatement.setInt(2, contact.getType().ordinal());
        preparedStatement.setString(3, contact.getValue());
        preparedStatement.setString(4, contact.getNote());
        preparedStatement.setLong(5, contact.getId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static Boolean delete(Connection conn, Long id) throws SQLException {
        if(id == null || id < 1)
            return false;

        String sql = "delete from Contacts where ID=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setLong(1, id);

        return preparedStatement.executeUpdate() > 0;
    }
}
