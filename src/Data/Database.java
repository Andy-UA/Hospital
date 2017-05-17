package Data;

import java.sql.*;

/**
 * Created by Andrew on 10.04.2017.
 */
public class Database {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {

        // Note: Change the connection parameters accordingly.
        String hostName = "localhost";
        String dbName = "Hospital";
        String userName = "sa";
        String password = "123321";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://" + hostName + ":1433;databaseName=" + dbName;

        Connection conn = DriverManager.getConnection(connectionURL, userName, password);
        return conn;
    }

    public static void closeQuietly(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
        }
    }

    public static void rollbackQuietly(Connection conn) {
        try {
            conn.rollback();
        } catch (Exception e) {
        }
    }

    public static Long getID(Connection conn, String tableName) throws SQLException{
        String sql = "select IDENT_CURRENT(?) as ID";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setString(1, tableName);
        ResultSet rs = preparedStatement.executeQuery();
        if(rs.next()){
            return rs.getLong(1);
        }
        return 0L;
    }
}
