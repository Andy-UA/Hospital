package Factories;

import java.security.MessageDigest;
import Beans.Account;
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
public class AccountFactory extends CommonFactory{
    public static Account select(Connection conn, String login, String password) throws Exception{

        String sql = "Select a.ID, a.HumanID, a.Login, a.Password, a.Enabled, a.Note from Accounts a where a.Login = ? and a.Password= ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, login);
        pstm.setString(2, password);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return loadAccount(rs);
        }
        return null;
    }

    public static Account select(Connection conn, String login) throws Exception {

        String sql = "Select a.ID, a.HumanID, a.Login, a.Password, a.Enabled, a.Note from Accounts a where a.Login = ? ";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, login);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return loadAccount(rs);
        }
        return null;
    }

    public static Account select(Connection conn, Long id) throws Exception {

        String sql = "select a.ID, a.HumanID, a.Login, a.Password, a.Enabled, a.Note from Accounts a where a.ID = ? ";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, id);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return loadAccount(rs);
        }
        return null;
    }

    public static List<Account> query(Connection conn, Long humanID) throws Exception{
        List<Account> list = new ArrayList<Account>();
        String sql = "select a.ID, a.HumanID, a.Login, a.Password, a.Enabled, a.Note from Accounts a where a.HumanID = ? order by a.ID";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, humanID);

        ResultSet rs = pstm.executeQuery();
        while(rs.next()) {
            list.add(loadAccount(rs));
        }
        return list;
    }

    private static Account loadAccount(ResultSet rs)  throws Exception {
        Account account = new Account();
        account.setId(rs.getLong("ID"));
        account.setHumanID(rs.getLong("HumanID"));
        account.setLogin(rs.getString("Login"));
        account.setPassword(rs.getString("Password"));
        account.setEnabled(rs.getBoolean("Enabled"));
        account.setNote(rs.getString("Note"));
        return account;
    }

    public static Boolean insert(Connection conn, Account account) throws Exception{
        String sql = "insert into Accounts(HumanID, Login, Password, Enabled, Note) values(?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setLong(1, account.getHumanID());
        preparedStatement.setString(2, account.getLogin());
        preparedStatement.setString(3, account.getPassword());
        preparedStatement.setBoolean(4, account.getEnabled());
        preparedStatement.setString(5, account.getNote());

        preparedStatement.executeUpdate();
        account.setId(Database.getID(conn, "Accounts"));
        return  account.getId() > 0L;
    }

    public static Boolean update(Connection conn, Account account) throws Exception {
        String sql = "update Accounts set HumanID=?, Login=?, Password=?, Enabled=?, Note=? where ID=?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setLong(1, account.getHumanID());
        preparedStatement.setString(2, account.getLogin());
        preparedStatement.setString(3, account.getPassword());
        preparedStatement.setBoolean(4, account.getEnabled());
        preparedStatement.setString(5, account.getNote());
        preparedStatement.setLong(6, account.getId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static Boolean delete(Connection conn, Long id) throws Exception {
        if(id == null || id < 1)
            return false;

        String sql = "delete from Accounts where ID=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setLong(1, id);

        return preparedStatement.executeUpdate() > 0;
    }

    public static String hashValue(String value) throws Exception {
        if (value != null) {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(value.getBytes());
            byte[] digest = messageDigest.digest();
            //convert the byte to hex format method 1
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                String hex = Integer.toHexString(0xff & digest[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        return null;
    }

    public static boolean comparePassword(String password, String originHash) throws Exception {
        if (password != null && originHash != null){
            String passwordHash = hashValue(password);
            return password.equalsIgnoreCase(originHash);
        }
        return false;
    }
}
