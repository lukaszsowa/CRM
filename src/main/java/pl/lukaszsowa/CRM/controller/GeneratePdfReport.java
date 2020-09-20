package pl.lukaszsowa.CRM.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import pl.lukaszsowa.CRM.model.Contact;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneratePdfReport {

    public static ByteArrayInputStream contactsPdf(List<Contact> contactList) {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(90);
            table.setWidths(new int[]{2, 2, 4});

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("First  name", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Last name", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("E-mail", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(hcell);

            for (Contact contact : contactList) {

                PdfPCell cell;

                cell = new PdfPCell(new Phrase(contact.getFirstName()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(contact.getLastName()));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.valueOf(contact.getEmail())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setPaddingRight(5);
                table.addCell(cell);
            }

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(table);

            document.close();

        } catch (DocumentException ex) {

            Logger.getLogger(GeneratePdfReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
