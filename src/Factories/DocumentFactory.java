package Factories;

import Beans.Document;
import Types.DocumentType;
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
public class DocumentFactory extends CommonFactory {
    public static Document select(Connection conn, Long id) throws Exception {
        String sql = "Select a.ID, a.HumanID, a.Type, a.Value, a.Note from Documents a where a.ID = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, id);

        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            Document document = new Document();
            document.setId(rs.getLong("ID"));
            document.setHumanID(rs.getLong("HumanID"));
            document.setType(DocumentType.fromInteger(rs.getInt("Type")));
            document.setValue(rs.getString("Value"));
            document.setNote(rs.getString("Note"));
            return document;
        }
        return null;
    }

    public static List<Document> query(Connection conn, Long humanID) throws Exception {
        List<Document> list = new ArrayList<Document>();
        String sql = "Select a.ID, a.HumanID, a.Type, a.Value, a.Note from Documents a where a.HumanID = ? order by a.Type, a.ID";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, humanID);

        ResultSet rs = pstm.executeQuery();
        while(rs.next()) {
            Document document = new Document();
            document.setId(rs.getLong("ID"));
            document.setHumanID(rs.getLong("HumanID"));
            document.setType(DocumentType.fromInteger(rs.getInt("Type")));
            document.setValue(rs.getString("Value"));
            document.setNote(rs.getString("Note"));
            list.add(document);
        }
        return list;
    }

    public static Boolean insert(Connection conn, Document document) throws Exception{
        String sql = "insert into Documents(HumanID, Type, Value, Note) values(?, ?, ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setLong(1, document.getHumanID());
        preparedStatement.setInt(2, document.getType().ordinal());
        preparedStatement.setString(3, document.getValue());
        preparedStatement.setString(4, document.getNote());

        preparedStatement.executeUpdate();
        document.setId(Database.getID(conn, "Documents"));
        return  document.getId() > 0L;
    }

    public static Boolean update(Connection conn, Document document) throws Exception {
        String sql = "update Documents set HumanID=?, Type=?, Value=?, Note=? where ID=?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setLong(1, document.getHumanID());
        preparedStatement.setInt(2, document.getType().ordinal());
        preparedStatement.setString(3, document.getValue());
        preparedStatement.setString(4, document.getNote());
        preparedStatement.setLong(5, document.getId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static Boolean delete(Connection conn, Long id) throws Exception {
        if(id == null || id < 1)
            return false;

        String sql = "delete from Documents where ID=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setLong(1, id);

        return preparedStatement.executeUpdate() > 0;
    }
}
