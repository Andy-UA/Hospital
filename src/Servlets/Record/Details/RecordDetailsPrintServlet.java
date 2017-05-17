package Servlets.Record.Details;

import Beans.*;
import Factories.*;
import Servlets.Common.HttpServletUtil;
import Types.ImageScope;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 * Created by andre on 22.04.2017.
 */
@WebServlet(urlPatterns = { "/recordDetailsPrint" })
public class RecordDetailsPrintServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try{
            HttpSession session = request.getSession(true);
            Long detailID = HttpServletUtil.getLongParam(request, "detailID", 0L);
            Long recordID = HttpServletUtil.getLongParam(request, "recordID", 0L);
            if (recordID == 0L)
                throw new Exception("Undefined value for recordID");
            Connection conn = SessionFactory.getStoredConnection(request);
            Record record = RecordFactory.select(conn, recordID);
            if (record == null)
                throw new Exception("Patient record " + recordID + " is not exists!");
            Schedule schedule = ScheduleFactory.select(conn, record.getScheduleID());
            if (schedule == null)
                throw new Exception("Schedule " + record.getScheduleID() + " is not exists!");
            Patient patient = PatientFactory.select(conn, schedule.getPatientID());
            if (patient == null)
                throw new Exception("Patient " + schedule.getPatientID() + " is not exists!");

            RecordInfo patientRecord = RecordInfoFactory.select(conn, record.getId());
            List<RecordDetailInfo> detailInfos = RecordDetailInfoFactory.query(conn, record.getId());
            List<ImageDescriptor> images = ImageFactory.queryDescriptions(conn, patient.getHumanID(), ImageScope.Photo, null);
            List<Location> locations = LocationFactory.query(conn, patient.getHumanID());
            List<Contact> contacts = ContactFactory.query(conn, patient.getHumanID());

            String patientName = patient.getFirstName() + " " + patient.getLastName();
            String patientInfo = "gender: " + patient.getSex() + ", birthday: " + patient.getBirthdayText();

            //PDF Output
            ByteArrayOutputStream documentStream = new ByteArrayOutputStream();
            com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter writer = PdfWriter.getInstance(document, documentStream);
            document.open();

            //Patient Avatar
            if (images.size() > 0) {
                com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(ImageFactory.select(conn, images.get(0).getId()).getData());
                image.scaleToFit(150f, 150f);
                image.setAbsolutePosition(document.getPageSize().getWidth()-image.getScaledWidth()-50, document.getPageSize().getHeight()-image.getScaledHeight()-75);
                document.add(image);
            }

            //Patient
            Paragraph p;
            p = new Paragraph(new Phrase(patientName, HttpServletUtil.getFont(18f, Font.BOLD | Font.UNDERLINE, BaseColor.BLUE)));
            document.add(p);

            //Patient info
            p = new Paragraph(new Phrase(patientInfo, HttpServletUtil.getFont(12f, Font.BOLDITALIC, BaseColor.DARK_GRAY)));
            p.setSpacingBefore(15);
            document.add(p);

            //Locations
            p = new Paragraph(new Phrase("locations:", HttpServletUtil.getFont(12f, Font.BOLDITALIC, BaseColor.DARK_GRAY)));
            p.setSpacingBefore(4);
            document.add(p);
            for (Location location: locations) {
                p = new Paragraph(new Phrase(location.toString(), HttpServletUtil.getFont(12f, Font.ITALIC, BaseColor.BLACK)));
                p.setSpacingBefore(1);
                document.add(p);
            }

            //Contacts
            p = new Paragraph(new Phrase("contacts:", HttpServletUtil.getFont(12f, Font.BOLDITALIC, BaseColor.DARK_GRAY)));
            p.setSpacingBefore(4);
            document.add(p);
            for (Contact contact: contacts) {
                p = new Paragraph(new Phrase(contact.toString(), HttpServletUtil.getFont(12f, Font.ITALIC, BaseColor.BLACK)));
                p.setSpacingBefore(2);
                document.add(p);
            }

            //Patient Record
            p = new Paragraph(new Phrase("patient record:", HttpServletUtil.getFont(12f, Font.BOLDITALIC, BaseColor.DARK_GRAY)));
            p.setSpacingBefore(4);
            document.add(p);
            for (Contact contact: contacts) {
                p = new Paragraph(new Phrase(patientRecord.toString(), HttpServletUtil.getFont(12f, Font.ITALIC, BaseColor.BLACK)));
                p.setSpacingBefore(2);
                document.add(p);
            }

            //Record details
            p = new Paragraph(new Phrase("Patient Record Details:", HttpServletUtil.getFont(16f, Font.BOLD, BaseColor.DARK_GRAY)));
            p.setSpacingBefore(10);
            document.add(p);

            Font headerFont = HttpServletUtil.getFont(9f, Font.BOLD, BaseColor.DARK_GRAY);
            Font detailFont = HttpServletUtil.getFont(9f, Font.NORMAL, BaseColor.BLACK);

            PdfPTable table = new PdfPTable(new float[]{60f, 250f, 70f, 80f, 250f});
            table.setSpacingBefore(5);
            table.setWidthPercentage(100);
            table.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(new PdfPCell(new Phrase("ID", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Appointment", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Unit", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Amount", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Notes", headerFont)));
            table.completeRow();
            for (RecordDetailInfo rec: detailInfos){
                table.addCell(new PdfPCell(new Phrase(rec.getId().toString(), detailFont)));
                table.addCell(new PdfPCell(new Phrase(rec.getAppointmentDescription(), detailFont)));
                table.addCell(new PdfPCell(new Phrase(rec.getAppointmentUnit(), detailFont)));
                table.addCell(new PdfPCell(new Phrase(rec.getAmount().toString(), detailFont)));
                table.addCell(new PdfPCell(new Phrase(rec.getDetailNote(), detailFont)));
                table.completeRow();
            }
            document.add(table);

            //document.add(createFirstTable());

            document.close();

            //Set HTTP headers
            response.setHeader("Content-disposition", HttpServletUtil.getPDFContentDisposition("PatientRecordDetails"));
            response.setContentType("application/pdf");
            response.setHeader("Cache-Control", "max-age=30");
            response.setContentLength(documentStream.size());
            //Output document
            ServletOutputStream outputStream = response.getOutputStream();
            documentStream.writeTo(outputStream);
            outputStream.flush();
            return;
        }catch (Exception e) {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }
        if (errorString != null){
            HttpServletUtil.showError(request, response, errorString, "/patientList");
        }
    }
}
