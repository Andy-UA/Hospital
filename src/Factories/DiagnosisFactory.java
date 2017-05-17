package Factories;

import Beans.Diagnosis;
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
public class DiagnosisFactory extends CommonFactory {
    public static Diagnosis select(Connection conn, Long id) throws SQLException {
        String sql = "Select a.ID, a.Code, a.Name, a.Note from Diagnosis a where a.ID = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, id);

        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            Diagnosis diagnosis = new Diagnosis();
            diagnosis.setId(rs.getLong("ID"));
            diagnosis.setCode(rs.getString("Code"));
            diagnosis.setName(rs.getString("Name"));
            diagnosis.setNote(rs.getString("Note"));
            return diagnosis;
        }
        return null;
    }

    public static List<Diagnosis> query(Connection conn, String code, String name) throws SQLException {
        List<Diagnosis> list = new ArrayList<Diagnosis>();
        String sql = "Select a.ID, a.Code, a.Name, a.Note from Diagnosis a " +
                "where a.Code like ? and a.Name like ? order by a.Code, a.Name, a.ID";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, code == null || code.isEmpty() ? "%" : code);
        pstm.setString(2, name == null || name.isEmpty() ? "%" : name);

        ResultSet rs = pstm.executeQuery();
        while(rs.next()) {
            Diagnosis diagnosis = new Diagnosis();
            diagnosis.setId(rs.getLong("ID"));
            diagnosis.setCode(rs.getString("Code"));
            diagnosis.setName(rs.getString("Name"));
            diagnosis.setNote(rs.getString("Note"));
            list.add(diagnosis);
        }
        return list;
    }

    public static Boolean insert(Connection conn, Diagnosis diagnosis) throws SQLException{
        String sql = "insert into Diagnosis(Code, Name, Note) values(?, ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setString(1, diagnosis.getCode());
        preparedStatement.setString(2, diagnosis.getName());
        preparedStatement.setString(3, diagnosis.getNote());

        preparedStatement.executeUpdate();
        diagnosis.setId(Database.getID(conn, "Diagnosis"));
        return  diagnosis.getId() > 0L;
    }

    public static Boolean update(Connection conn, Diagnosis diagnosis) throws SQLException {
        String sql = "update Diagnosis set Code=?, Name=?, Note=? where ID=?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setString(1, diagnosis.getCode());
        preparedStatement.setString(2, diagnosis.getName());
        preparedStatement.setString(3, diagnosis.getNote());
        preparedStatement.setLong(4, diagnosis.getId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static Boolean delete(Connection conn, Long id) throws SQLException {
        if(id == null || id < 1)
            return false;

        String sql = "delete from Diagnosis where ID=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setLong(1, id);

        return preparedStatement.executeUpdate() > 0;
    }
}
