package Factories;

import Beans.RecordDetailInfo;
import Types.AppointmentType;
import Types.RecordDetailStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andre on 22.04.2017.
 */
public class RecordDetailInfoFactory extends CommonFactory {

    public static List<RecordDetailInfo> query(Connection conn, Long recordID) throws Exception {
        List<RecordDetailInfo> list = new ArrayList<RecordDetailInfo>();
        String sql = "select ID,RecordID,AppointmentID,AppointmentDescription,AppointmentUnit,AppointmentType,AppointmentNote,Status,Amount,DetailNote from vRecordDetails where RecordID = ? order by ID";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setLong(1, recordID);

        ResultSet rs = pstm.executeQuery();
        while (rs.next()) {
            list.add(loadRecordDetailInfo(conn, rs));
        }
        return list;
    }

    private static RecordDetailInfo loadRecordDetailInfo(Connection conn, ResultSet rs) throws Exception {
        RecordDetailInfo recordDetail = new RecordDetailInfo();
        recordDetail.setId(rs.getLong("ID"));
        recordDetail.setRecordID(rs.getLong("RecordID"));
        recordDetail.setAppointmentID(rs.getLong("AppointmentID"));
        recordDetail.setAppointmentDescription(rs.getString("AppointmentDescription"));
        recordDetail.setAppointmentUnit(rs.getString("AppointmentUnit"));
        recordDetail.setAppointmentType(AppointmentType.fromInteger(rs.getInt("AppointmentType")));
        recordDetail.setAppointmentNote(rs.getString("AppointmentNote"));
        recordDetail.setStatus(RecordDetailStatus.fromInteger(rs.getInt("Status")));
        recordDetail.setAmount(rs.getDouble("Amount"));
        recordDetail.setDetailNote(rs.getString("DetailNote"));
        recordDetail.setTreatments(TreatmentFactory.query(conn, recordDetail.getId()));
        return recordDetail;
    }
}
