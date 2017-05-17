package Factories;

import Beans.Image;
import Beans.ImageDescriptor;
import Types.ImageScope;
import Data.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 15.04.2017.
 */
public class ImageFactory extends CommonFactory {
    public static Image select(Connection conn, Long id) throws Exception {
        String sql = "Select a.ID, a.HumanID, a.Scope, a.ContentType, a.Data, a.Enabled, a.Note from Images a where a.ID = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, id);

        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return loadImage(rs);
        }
        return null;
    }

    public static List<Image> query(Connection conn, Long humanID, ImageScope scope, String contentType) throws Exception {
        List<Image> list = new ArrayList<Image>();

        String sql = "Select a.ID, a.HumanID, a.Scope, a.ContentType, a.Data, a.Enabled, a.Note from Images a " +
                "where a.HumanID = ? and a.Scope = ? and a.ContentType like ? order by a.ID desc";


        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, humanID);
        pstm.setInt(2, scope.ordinal());
        pstm.setString(3, contentType == null || contentType.isEmpty() ? "%" : contentType);

        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            list.add(loadImage(rs));
        }
        return list;
    }

    public static List<ImageDescriptor> queryDescriptions(Connection conn, Long humanID, ImageScope scope, String contentType) throws Exception {
        List<ImageDescriptor> list = new ArrayList<ImageDescriptor>();

        String sql = "Select a.ID, a.HumanID, a.Scope, a.ContentType, a.Enabled, a.Note from Images a where  a.ContentType like ? ";
        if(humanID > -1)
            sql += " and a.HumanID = " + humanID;
        if(scope != ImageScope.Unknown)
            sql += " and a.Scope = " + scope.ordinal();
        sql += " order by a.ID";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, contentType == null || contentType.isEmpty() ? "%" : contentType);


        ResultSet rs = pstm.executeQuery();
        Integer index = 0;
        while (rs.next()) {
            list.add(loadImageDescriptor(rs, index));
            index++;
        }
        return list;
    }

    private static ImageDescriptor loadImageDescriptor(ResultSet rs, Integer index) throws Exception {
        ImageDescriptor image = new ImageDescriptor();
        image.setId(rs.getLong("ID"));
        image.setHumanID(rs.getLong("HumanID"));
        image.setScope(ImageScope.fromInteger(rs.getInt("Scope")));
        image.setContentType(rs.getString("ContentType"));
        image.setEnabled(rs.getBoolean("Enabled"));
        image.setNote(rs.getString("Note"));
        image.setIndex(index);
        return image;
    }

    private static Image loadImage(ResultSet rs) throws Exception {
        Image image = new Image();
        image.setId(rs.getLong("ID"));
        image.setHumanID(rs.getLong("HumanID"));
        image.setScope(ImageScope.fromInteger(rs.getInt("Scope")));
        image.setContentType(rs.getString("ContentType"));
        image.setData(rs.getBytes("Data"));
        image.setEnabled(rs.getBoolean("Enabled"));
        image.setNote(rs.getString("Note"));
        return image;
    }

    public static Boolean insert(Connection conn, Image image) throws SQLException{
        String sql = "insert into Images(HumanID, Scope, ContentType, Data, Enabled, Note) values(?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setLong(1, image.getHumanID());
        preparedStatement.setInt(2, image.getScope().ordinal());
        preparedStatement.setString(3, image.getContentType());
        preparedStatement.setBytes(4, image.getData());
        preparedStatement.setBoolean(5, image.getEnabled());
        preparedStatement.setString(6, image.getNote());


        preparedStatement.executeUpdate();
        image.setId(Database.getID(conn, "Images"));
        return  image.getId() > 0L;
    }

    public static Boolean update(Connection conn, Image image) throws SQLException{
        String sql = "update Images set HumanID=?, Scope=?, ContentType=?, Data=?, Enabled=?, Note=? where ID=?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setLong(1, image.getHumanID());
        preparedStatement.setInt(2, image.getScope().ordinal());
        preparedStatement.setString(3, image.getContentType());
        preparedStatement.setBytes(4, image.getData());
        preparedStatement.setBoolean(5, image.getEnabled());
        preparedStatement.setString(6, image.getNote());
        preparedStatement.setLong(7, image.getId());


        preparedStatement.executeUpdate();
        image.setId(Database.getID(conn, "Images"));
        return  image.getId() > 0L;
    }

    public static Boolean delete(Connection conn, Long id) throws SQLException {
        if(id == null || id < 1)
            return false;

        String sql = "delete from Images where ID=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setLong(1, id);

        return preparedStatement.executeUpdate() > 0;
    }
}
