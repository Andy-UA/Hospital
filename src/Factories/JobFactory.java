package Factories;

import Beans.Job;
import Data.Database;
import Types.JobScope;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andre on 06.05.2017.
 */
public class JobFactory {
    public static Job select(Connection conn, Long id) throws Exception{
        String sql = "select ID, RoleID, Scope, Note from Jobs where ID = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()) {
            return loadJob(rs);
        }
        return null;
    }

    public static List<Job> query(Connection conn, Long roleID) throws Exception{
        List<Job> list = new ArrayList<Job>();
        String sql = "select ID, RoleID, Scope, Note from Jobs where RoleID = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setLong(1, roleID);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            list.add(loadJob(rs));
        }
        return  list;
    }

    private static Job loadJob(ResultSet rs) throws Exception{
        return new Job(
                rs.getLong("ID"),
                rs.getLong("RoleID"),
                JobScope.fromInteger(rs.getInt("Scope")),
                rs.getString("Note")
        );
    }


    public static Boolean insert(Connection conn, Job job) throws Exception{
        String sql = "insert into Jobs(Scope, RoleID, Note) values(?, ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setInt(1, job.getScope().ordinal());
        preparedStatement.setLong(2, job.getRoleID());
        preparedStatement.setString(3, job.getNote());

        preparedStatement.executeUpdate();
        job.setId(Database.getID(conn, "Jobs"));
        return  job.getId() > 0L;
    }

    public static Boolean update(Connection conn, Job job) throws Exception {
        String sql = "update Jobs set Scope=?, RoleID=?, Note=? where ID=?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setInt(1, job.getScope().ordinal());
        preparedStatement.setLong(2, job.getRoleID());
        preparedStatement.setString(3, job.getNote());
        preparedStatement.setLong(4, job.getId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static Boolean delete(Connection conn, Long id) throws Exception {
        if(id == null || id < 1)
            return false;
        String sql = "delete from Jobs where ID=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        return preparedStatement.executeUpdate() > 0;
    }
}
