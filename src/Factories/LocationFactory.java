package Factories;

import Beans.Location;
import Types.LocationType;
import Data.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrew on 11.04.2017.
 */
public class LocationFactory extends CommonFactory {
    public static Location select(Connection conn, Long id) throws Exception {

        String sql = "Select a.ID, a.HumanID, a.Type, a.PostIndex, a.Country, a.State, " +
                "a.Area, a.City, a.Street, a.House, a.Apartment, a.Note from Locations a where a.ID = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, id);

        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return loadLocation(rs);
        }
        return null;
    }

    private static Location loadLocation(ResultSet rs) throws Exception {
        Location location = new Location();
        location.setId(rs.getLong("ID"));
        location.setHumanID(rs.getLong("HumanID"));
        location.setType(LocationType.fromInteger(rs.getInt("Type")));
        location.setPostIndex(rs.getString("PostIndex"));
        location.setCountry(rs.getString("Country"));
        location.setState(rs.getString("State"));
        location.setArea(rs.getString("Area"));
        location.setCity(rs.getString("City"));
        location.setStreet(rs.getString("Street"));
        location.setHouse(rs.getString("House"));
        location.setApartment(rs.getString("Apartment"));
        location.setNote(rs.getString("Note"));
        return location;
    }

    public static List<Location> query(Connection conn, Long humanID) throws Exception {
        List<Location> list = new ArrayList<Location>();
        String sql = "Select a.ID, a.HumanID, a.Type, a.PostIndex, a.Country, a.State, " +
                "a.Area, a.City, a.Street, a.House, a.Apartment, a.Note from Locations a where a.HumanID = ? order by a.Type, a.ID";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, humanID);

        ResultSet rs = pstm.executeQuery();
        while(rs.next()) {
            list.add(loadLocation(rs));
        }
        return list;
    }

    public static Boolean insert(Connection conn, Location location) throws Exception{
        String sql = "insert into Locations(HumanID, Type, PostIndex, Country, State, Area, City, Street, " +
                                            "House, Apartment, Note) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setLong(1, location.getHumanID());
        preparedStatement.setInt(2, location.getType().ordinal());
        preparedStatement.setString(3, location.getPostIndex());
        preparedStatement.setString(4, location.getCountry());
        preparedStatement.setString(5, location.getState());
        preparedStatement.setString(6, location.getArea());
        preparedStatement.setString(7, location.getCity());
        preparedStatement.setString(8, location.getStreet());
        preparedStatement.setString(9, location.getHouse());
        preparedStatement.setString(10, location.getApartment());
        preparedStatement.setString(11, location.getNote());

        preparedStatement.executeUpdate();
        location.setId(Database.getID(conn, "Locations"));
        return  location.getId() > 0L;
    }

    public static Boolean update(Connection conn, Location location) throws Exception {
        String sql = "update Locations set HumanID=?, Type=?, PostIndex=?, Country=?, State=?, Area=?, " +
                                        "City=?, Street=?, House=?, Apartment=?, Note=? where ID=?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setLong(1, location.getHumanID());
        preparedStatement.setInt(2, location.getType().ordinal());
        preparedStatement.setString(3, location.getPostIndex());
        preparedStatement.setString(4, location.getCountry());
        preparedStatement.setString(5, location.getState());
        preparedStatement.setString(6, location.getArea());
        preparedStatement.setString(7, location.getCity());
        preparedStatement.setString(8, location.getStreet());
        preparedStatement.setString(9, location.getHouse());
        preparedStatement.setString(10, location.getApartment());
        preparedStatement.setString(11, location.getNote());
        preparedStatement.setLong(12, location.getId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static Boolean delete(Connection conn, Long id) throws Exception {
        if(id == null || id < 1)
            return false;

        String sql = "delete from Locations where ID=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setLong(1, id);

        return preparedStatement.executeUpdate() > 0;
    }
}
