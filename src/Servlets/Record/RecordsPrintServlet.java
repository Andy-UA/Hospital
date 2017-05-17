package Servlets.Record;

import Beans.*;
import Types.ImageScope;
import Factories.*;
import Factories.SessionFactory;
import Servlets.Common.HttpServletUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;

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
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by andre on 20.04.2017.
 */
@WebServlet(urlPatterns = { "/recordsPrint" })
public class RecordsPrintServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try {
            HttpSession session = request.getSession(true);
            Long patientID = HttpServletUtil.getLongParam(request, "patientID", 0L);
            Long scheduleID = HttpServletUtil.getLongParam(request, "scheduleID", 0L);
            if (patientID == 0L)
                throw new Exception("Undefined value for patientID");

            String sBegin = request.getParameter("eventBegin");
            String sEnd = request.getParameter("eventEnd");
            LocalDateTime eventBegin;
            LocalDateTime eventEnd;
            if (sBegin == null || sEnd == null) {
                eventBegin = SessionFactory.getSessionEventBegin(session);
                if (eventBegin == null )
                    eventBegin = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
                request.setAttribute("eventBegin", eventBegin);

                eventEnd = SessionFactory.getSessionEventEnd(session);
                if (eventEnd == null )
                    eventEnd = LocalDateTime.now().withHour(23).withMinute(0).withSecond(0).withNano(0);
                request.setAttribute("eventEnd", eventEnd);
            } else {
                eventBegin = LocalDateTime.parse(sBegin).withSecond(0).withNano(0);
                request.setAttribute("eventBegin", eventBegin);
                eventEnd = LocalDateTime.parse(sEnd).withSecond(0).withNano(0);
                request.setAttribute("eventEnd", eventEnd);
            }

            Connection conn = SessionFactory.getStoredConnection(request);
            Patient patient = PatientFactory.select(conn, patientID);
            if (patient == null)
                throw new Exception("Requested patient role isn't exists");
            RecordsRequest recordsRequest = new RecordsRequest();
            recordsRequest.setPatientID(patient.getRoleID());
            recordsRequest.setEventBegin(eventBegin);
            recordsRequest.setEventEnd(eventEnd);
            List<RecordInfo> patientRecords = RecordInfoFactory.query(conn, recordsRequest);
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

            Paragraph p;
            p = new Paragraph(new Phrase(patientName, HttpServletUtil.getFont(18f, Font.BOLD | Font.UNDERLINE, BaseColor.BLUE)));
            document.add(p);

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

            p = new Paragraph(new Phrase("Patient Records:", HttpServletUtil.getFont(16f, Font.BOLD, BaseColor.DARK_GRAY)));
            p.setSpacingBefore(10);
            document.add(p);

            if (images.size() > 0) {
                com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(ImageFactory.select(conn, images.get(0).getId()).getData());
                image.scaleToFit(150f, 150f);
                image.setAbsolutePosition(document.getPageSize().getWidth()-image.getScaledWidth()-50, document.getPageSize().getHeight()-image.getScaledHeight()-75);
                document.add(image);
            }

            Font headerFont = HttpServletUtil.getFont(9f, Font.BOLD, BaseColor.DARK_GRAY);
            Font detailFont = HttpServletUtil.getFont(9f, Font.NORMAL, BaseColor.BLACK);

            PdfPTable table = new PdfPTable(new float[]{60f, 150f, 70f, 220f, 250f});
            table.setSpacingBefore(5);
            table.setWidthPercentage(100);
            table.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(new PdfPCell(new Phrase("ID", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Date & Time", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Code", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Diagnosis name", headerFont)));
            table.addCell(new PdfPCell(new Phrase("Notes", headerFont)));
            table.completeRow();
            for (RecordInfo rec: patientRecords){
                table.addCell(new PdfPCell(new Phrase(rec.getId().toString(), detailFont)));
                table.addCell(new PdfPCell(new Phrase(rec.getEventDateText(), detailFont)));
                table.addCell(new PdfPCell(new Phrase(rec.getDiagnosisCode(), detailFont)));
                table.addCell(new PdfPCell(new Phrase(rec.getDiagnosisName(), detailFont)));
                table.addCell(new PdfPCell(new Phrase(rec.getRecordNote(), detailFont)));
                table.completeRow();
            }
            document.add(table);

            //document.add(createFirstTable());

            document.close();

            //Set HTTP headers
            response.setHeader("Content-disposition", HttpServletUtil.getPDFContentDisposition("PatientRecords"));
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
