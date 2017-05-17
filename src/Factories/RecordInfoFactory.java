package Factories;

import Beans.Record;
import Beans.RecordInfo;
import Beans.RecordsRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andre on 20.04.2017.
 */
public class RecordInfoFactory extends CommonFactory {

    public static RecordInfo select(Connection conn, Long recordID) throws Exception {
        String sql = "select r.ID, r.RecordNote, r.EventDate, r.ScheduleID, r.StaffID, r.PatientID, r.DiagnosisID, r.DiagnosisCode, r.DiagnosisName, r.DiagnosisNote " +
                "from vRecords r where r.ID = ?";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setLong(1, recordID);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            return loadRecordInfo(conn, rs);
        }
        return null;
    }

    public static List<RecordInfo> query(Connection conn, RecordsRequest request) throws Exception {
        List<RecordInfo> list = new ArrayList<RecordInfo>();
        
        String sql = "select r.ID, r.RecordNote, r.EventDate, r.ScheduleID, r.StaffID, r.PatientID, r.DiagnosisID, r.DiagnosisCode, r.DiagnosisName, r.DiagnosisNote from vRecords r where r.PatientID = ? ";
        sql += request.getStaffID() > 0L ? " and StaffID = " + request.getStaffID() : "";
        sql += " and r.EventDate between ? and ? order by r.EventDate asc, r.ID asc";
        
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setLong(1, request.getPatientID());
        preparedStatement.setTimestamp(2, java.sql.Timestamp.valueOf(request.getEventBegin()));
        preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(request.getEventEnd()));
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()) {
            list.add(loadRecordInfo(conn, rs));
        }
        return list;
    }

    private static RecordInfo loadRecordInfo(Connection conn, ResultSet rs) throws Exception {
        RecordInfo recordInfo = new RecordInfo();
        recordInfo.setId(rs.getLong("ID"));
        recordInfo.setRecordNote(rs.getString("RecordNote"));
        recordInfo.setEventDate(rs.getTimestamp("EventDate").toLocalDateTime());
        recordInfo.setScheduleID(rs.getLong("ScheduleID"));
        recordInfo.setPatientID(rs.getLong("PatientID"));
        recordInfo.setStaffID(rs.getLong("StaffID"));
        recordInfo.setDiagnosisID(rs.getLong("DiagnosisID"));
        recordInfo.setDiagnosisCode(rs.getString("DiagnosisCode"));
        recordInfo.setDiagnosisName(rs.getString("DiagnosisName"));
        recordInfo.setDiagnosisNote(rs.getString("DiagnosisNote"));
        recordInfo.setDetails(RecordDetailInfoFactory.query(conn, recordInfo.getId()));
        return recordInfo;
    }
}
