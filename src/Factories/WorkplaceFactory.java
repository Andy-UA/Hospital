package Factories;

import Beans.Workplace;
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
public class WorkplaceFactory extends CommonFactory {
    public static Workplace select(Connection conn, Long id) throws Exception {
        String sql = "Select a.ID, a.Building, a.Floor, a.Room, a.Note from Workplaces a where a.ID = ?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, id);

        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            Workplace workplace = new Workplace();
            workplace.setId(rs.getLong("ID"));
            workplace.setBuilding(rs.getString("Building"));
            workplace.setFloor(rs.getString("Floor"));
            workplace.setRoom(rs.getString("Room"));
            workplace.setNote(rs.getString("Note"));
            return workplace;
        }
        return null;
    }

    public static List<Workplace> query(Connection conn, String building, String room) throws Exception {
        List<Workplace> list = new ArrayList<Workplace>();
        String sql = "Select a.ID, a.Building, a.Floor, a.Room, a.Note from Workplaces a " +
                "where a.Building like ? and a.Room like ? order by a.Building, a.Floor, a.Room";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, building== null || building.isEmpty() ? "%" : building);
        pstm.setString(2, room == null || room.isEmpty() ? "%" : room);

        ResultSet rs = pstm.executeQuery();
        while(rs.next()) {
            Workplace workplace = new Workplace();
            workplace.setId(rs.getLong("ID"));
            workplace.setBuilding(rs.getString("Building"));
            workplace.setFloor(rs.getString("Floor"));
            workplace.setRoom(rs.getString("Room"));
            workplace.setNote(rs.getString("Note"));
            list.add(workplace);
        }
        return list;
    }

    public static Boolean insert(Connection conn, Workplace workplace) throws Exception{
        String sql = "insert into Workplaces(Building, Floor, Room, Note) values(?, ?, ?, ?)";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setString(1, workplace.getBuilding());
        preparedStatement.setString(2, workplace.getFloor());
        preparedStatement.setString(3, workplace.getRoom());
        preparedStatement.setString(4, workplace.getNote());

        preparedStatement.executeUpdate();
        workplace.setId(Database.getID(conn, "Workplaces"));
        return  workplace.getId() > 0L;
    }

    public static Boolean update(Connection conn, Workplace workplace) throws Exception {
        String sql = "update Workplaces set Building=?, Floor=?, Room=?, Note=? where ID=?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        preparedStatement.setString(1, workplace.getBuilding());
        preparedStatement.setString(2, workplace.getFloor());
        preparedStatement.setString(3, workplace.getRoom());
        preparedStatement.setString(4, workplace.getNote());
        preparedStatement.setLong(5, workplace.getId());

        return preparedStatement.executeUpdate() > 0;
    }

    public static Boolean delete(Connection conn, Long id) throws Exception {
        if(id == null || id < 1)
            return false;

        String sql = "delete from Workplaces where ID=?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setLong(1, id);

        return preparedStatement.executeUpdate() > 0;
    }
}
