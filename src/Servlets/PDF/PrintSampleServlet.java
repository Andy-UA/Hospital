package Servlets.PDF;

import Servlets.Common.HttpServletUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

// import the iText packages

/**
 * Created by andre on 23.04.2017.
 */
@WebServlet(urlPatterns = { "/printSample" })
public class PrintSampleServlet extends HttpServlet {
    public PrintSampleServlet() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String errorString = null;
        try {
            ByteArrayOutputStream documentStream = new ByteArrayOutputStream();

            // Listing 1. Instantiation of document object
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            // Listing 2. Creation of PdfWriter object
            PdfWriter writer = PdfWriter.getInstance(document, documentStream);
            document.open();
            // Listing 3. Creation of paragraph object
            Anchor anchorTarget = new Anchor("First page of the document.");
            anchorTarget.setName("BackToTop");
            Paragraph paragraph1 = new Paragraph();
            paragraph1.setSpacingBefore(50);
            paragraph1.add(anchorTarget);
            document.add(paragraph1);

            String FONT_LOCATION = "c:/Windows/Fonts/tahoma.ttf";
            BaseFont baseFont = BaseFont.createFont(FONT_LOCATION, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font font = new Font(baseFont, Font.DEFAULTSIZE, Font.NORMAL);
            document.add(new Paragraph("Русский", font));
            document.add(new Paragraph("Привет из Госпиталя )))", font));

            document.add(new Paragraph("Some more text on the first page with different color and font type.",
                            FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, new CMYKColor(0, 255, 0, 0))));

            // Listing 4. Creation of chapter object
            Paragraph title1 = new Paragraph("Chapter 1", FontFactory.getFont(
                    FontFactory.HELVETICA, 18, Font.BOLDITALIC, new CMYKColor(0,255, 255, 17)));
            Chapter chapter1 = new Chapter(title1, 1);
            chapter1.setNumberDepth(0);

            // Listing 5. Creation of section object
            Paragraph title11 = new Paragraph("This is Section 1 in Chapter 1",
                    FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new CMYKColor(0, 255, 255, 17)));
            Section section1 = chapter1.addSection(title11);
            Paragraph someSectionText = new Paragraph("This text comes as part of section 1 of chapter 1.");
            section1.add(someSectionText);
            someSectionText = new Paragraph("Following is a 3 X 2 table.");
            section1.add(someSectionText);

            // Listing 6. Creation of table object
            PdfPTable t = new PdfPTable(3);

            t.setSpacingBefore(25);
            t.setSpacingAfter(25);
            PdfPCell c1 = new PdfPCell(new Phrase("Header1"));
            t.addCell(c1);
            PdfPCell c2 = new PdfPCell(new Phrase("Header2"));
            t.addCell(c2);
            PdfPCell c3 = new PdfPCell(new Phrase("Header3"));
            t.addCell(c3);
            t.addCell("1.1");
            t.addCell("1.2");
            t.addCell("1.3");
            t.setSummary("total");
            t.addCell("2.1");
            t.addCell("2.2");
            //t.addCell("2.3");
            t.completeRow();

            section1.add(t);

            // Listing 7. Creation of list object
            List l = new List(true, false, 10);
            l.add(new ListItem("First item of list"));
            l.add(new ListItem("Second item of list"));
            section1.add(l);

            // Listing 8. Adding image to the main document

            String imgFile = request.getSession().getServletContext().getRealPath("/images") + "/worldclients.png";
            Image image2 = Image.getInstance(imgFile);
            image2.scaleAbsolute(120f, 120f);
            section1.add(image2);

            // Listing 9. Adding Anchor to the main document.
            Paragraph title2 = new Paragraph("Using Anchor", FontFactory.getFont(
                    FontFactory.HELVETICA, 16, Font.BOLD, new CMYKColor(0, 255, 0,
                            0)));
            section1.add(title2);

            title2.setSpacingBefore(5000);
            Anchor anchor2 = new Anchor("Back To Top");
            anchor2.setReference("#BackToTop");

            section1.add(anchor2);

            // Listing 10. Addition of a chapter to the main document
            document.add(chapter1);
            document.close();

            //Set HTTP headers
            StringBuffer sbFilename = new StringBuffer();
            sbFilename.append("hospital_");
            sbFilename.append(System.currentTimeMillis());
            sbFilename.append(".pdf");
            StringBuffer sbContentDispValue = new StringBuffer();
            sbContentDispValue.append("inline");
            sbContentDispValue.append("; filename=");
            sbContentDispValue.append(sbFilename);

            response.setHeader("Content-disposition", sbContentDispValue.toString());
            response.setContentType("application/pdf");
            response.setHeader("Cache-Control", "max-age=30");
            response.setContentLength(documentStream.size());
            //Output document
            ServletOutputStream outputStream = response.getOutputStream();
            documentStream.writeTo(outputStream);
            outputStream.flush();
            return;
        }catch (Exception e)
        {
            errorString = HttpServletUtil.getExeptionMessage(e);
        }
        if (errorString != null){
            HttpServletUtil.showError(request, response, errorString, "/home");
        }
    }


    public PdfPTable createFirstTable() {
        // a table with three columns
        PdfPTable table = new PdfPTable(3);
        // the cell object
        PdfPCell cell;
        // we add a cell with colspan 3
        cell = new PdfPCell(new Phrase("Cell with colspan 3"));
        cell.setColspan(3);
        table.addCell(cell);
        // now we add a cell with rowspan 2
        cell = new PdfPCell(new Phrase("Cell with rowspan 2"));
        cell.setRowspan(2);
        table.addCell(cell);
        // we add the four remaining cells with addCell()
        table.addCell("row 1; cell 1");
        table.addCell("row 1; cell 2");
        table.addCell("row 2; cell 1");
        table.addCell("row 2; cell 2");
        table.setWidthPercentage(80);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        return table;
    }

}
