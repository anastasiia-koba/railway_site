package system.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import system.entity.Ticket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Object represents pdf-document for Ticket.
 */
public class PDFBuilder extends AbstractITextPdfView {

    @Override
    protected void buildPdfDocument(
            Map<String, Object> model,
            Document document,
            PdfWriter writer,
            HttpServletRequest request,
            HttpServletResponse response) throws DocumentException {

        Ticket ticket = (Ticket) model.get("ticket");

        document.add(new Paragraph("Ticket"));

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {3.0f, 7.0f});
        table.setSpacingBefore(10);


        table.addCell("Name");
        table.addCell(ticket.getProfile().getSurname()+" "+ ticket.getProfile().getFirstname());
        table.addCell("Train");
        table.addCell(ticket.getFinalRout().getRout().getRoutName()+": "+
                ticket.getFinalRout().getRout().getStartStation().getStationName()+" -> "+
                ticket.getFinalRout().getRout().getEndStation().getStationName());
        table.addCell("Date");
        table.addCell(ticket.getFinalRout().getDate().toString());

        table.addCell("From");
        table.addCell(ticket.getStartStation().getStationName());
        table.addCell("To");
        table.addCell(ticket.getEndStation().getStationName());
        table.addCell("Price");
        table.addCell(String.valueOf(ticket.getPrice()));

        document.add(table);
    }
}