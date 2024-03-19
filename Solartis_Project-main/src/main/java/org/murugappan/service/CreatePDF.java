package org.murugappan.service;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.murugappan.DAO.CartDAO;
import org.murugappan.DAO.CartImpl;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CreatePDF {
    CartDAO ci=new CartImpl();

    void createInvoice(int userid,String modeOfPayment,String UserName) {
        ResultSet rs=ci.generateBillPDF(userid);
       
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime=LocalTime.now();
        double totalBeforeTax = 0.0;
        int totalQuantity = 0;
        double totalPriceInclusiveOfTax = 0.0;
        double totalTax=0.0;

        String invoiceSequence = "INV-"+currentDate+"-"+currentTime+"";
        String path = "invoice.pdf";
        PdfWriter writer = null;
        Document document = null;
        Table table1 = new Table(new float[]{3, 1, 1, 1, 2, 1, 2});
        table1.setMarginBottom(0);
        table1.setMarginTop(0);
        table1.setWidth(UnitValue.createPercentValue(100));
        table1.addCell(new Cell().add("Product Name").setBorder(new SolidBorder(1f)));
        table1.addCell(new Cell().add("Quantity").setBorder(new SolidBorder(1f)));
        table1.addCell(new Cell().add("Unit Price").setBorder(new SolidBorder(1f)));
        table1.addCell(new Cell().add("Tax Percent").setBorder(new SolidBorder(1f)));
        table1.addCell(new Cell().add("Total Price Before Tax").setBorder(new SolidBorder(1f)));
        table1.addCell(new Cell().add("Tax Price").setBorder(new SolidBorder(1f)));
        table1.addCell(new Cell().add("Price Inclusive of Tax").setBorder(new SolidBorder(1f)));
        try{

            while (rs.next()){
                String productName = rs.getString("product_name");
                int quantity = rs.getInt("quantity");
                double priceBeforeTax = rs.getDouble("unit_price");
                int taxPercent = rs.getInt("tax_percent");
                double totalPriceBeforeTax = rs.getDouble("total_price_before_tax");
                double taxAmount = rs.getDouble("tax_amount");
                double priceInclusiveOfTax = rs.getDouble("price_inclusive_of_tax");
                totalBeforeTax += priceBeforeTax * quantity;
                totalQuantity += quantity;
                totalPriceInclusiveOfTax += priceInclusiveOfTax;
                totalTax=totalPriceInclusiveOfTax-totalBeforeTax;
                table1.addCell(new Cell().add(productName));
                table1.addCell(new Cell().add(String.valueOf(quantity)));
                table1.addCell(new Cell().add(String.valueOf(priceBeforeTax)));
                table1.addCell(new Cell().add(String.valueOf(taxPercent)));
                table1.addCell(new Cell().add(String.valueOf(totalPriceBeforeTax)));
                table1.addCell(new Cell().add(String.valueOf(taxAmount)));
                table1.addCell(new Cell().add(String.valueOf(priceInclusiveOfTax)));
               
            }}
        catch (SQLException e){
            e.printStackTrace();
        }
        try {
            writer = new PdfWriter(path);
            PdfDocument pdfDoc = new PdfDocument(writer);
            document = new Document(pdfDoc);
            PageSize pageSize = pdfDoc.getDefaultPageSize();
            Paragraph paragraph = new Paragraph("GSTIN:32AAHCR7467A1ZI              TaxInvoice                   OriginalRecipient");
            paragraph.setMarginBottom(0);
            paragraph.setMarginTop(0);
            paragraph.setTextAlignment(TextAlignment.CENTER);
            paragraph.setBorder(new SolidBorder(1f));
            paragraph.setWidth(UnitValue.createPointValue(pageSize.getWidth()));
            document.add(paragraph);
            Paragraph paragraph2 = new Paragraph("Hello World Plaza\nNo 6 Main Street Kanchipuram Tamil Nadu India\nMobile No:9090897490,9089456787\nEmail:heloworld@admin.com");
            paragraph2.setMarginBottom(0);
            paragraph2.setMarginTop(0);
            paragraph2.setTextAlignment(TextAlignment.CENTER);
            paragraph2.setBorder(new SolidBorder(1f));
            paragraph2.setWidth(UnitValue.createPointValue(pageSize.getWidth()));
            document.add(paragraph2);
            Table table = new Table(new float[]{1, 1});
            table.setMarginBottom(0);
            table.setMarginTop(0);
            table.setWidth(UnitValue.createPercentValue(100));
            Paragraph billToParagraph = new Paragraph().add("Bill To:" + UserName);
            billToParagraph.setTextAlignment(TextAlignment.LEFT);
            table.addCell(new Paragraph().add(billToParagraph));
            Paragraph detailsParagraph = new Paragraph()
                    .add("Date: " + currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n")
                    .add("Invoice No: " + invoiceSequence + "\n")
                    .add("Payment Mode: " + modeOfPayment);
            detailsParagraph.setTextAlignment(TextAlignment.LEFT);
            table.addCell(new Paragraph().add(detailsParagraph));
            document.add(table);
            Paragraph notes = new Paragraph().add("Notes:");
            notes.setMarginBottom(0);
            notes.setMarginTop(0);
            notes.setTextAlignment(TextAlignment.LEFT);
            notes.setBorder(new SolidBorder(1f));
            notes.setWidth(UnitValue.createPercentValue(pageSize.getWidth()));
            document.add(notes);
            document.add(table1);
            Table bankDetailsTable = new Table(new float[]{1, 2});
            bankDetailsTable.setWidth(UnitValue.createPercentValue(100));
            bankDetailsTable.setMarginBottom(0);
            bankDetailsTable.setMarginTop(0);
            bankDetailsTable.addCell(new Cell().add(new Paragraph("Company's Bank Details\nAccount Name:Hello World Plaza\nAccount No:3645217692453156\nBranch Name:Kanchipuram Main Branch \nIFSC Code:IDIBP00036\n")));
            bankDetailsTable.addCell(new Cell().add(new Paragraph("Total Quantity:"+totalQuantity+"\nTotal Amount Before Tax:"+totalBeforeTax+"\nTotal Amount After Tax:"+totalPriceInclusiveOfTax+"\nTotal Tax:"+totalTax+"")));
            document.add(bankDetailsTable);
            Table table2 = new Table(new float[]{1, 1, 1});
            table2.setMarginBottom(0);
            table2.setMarginTop(0);
            table2.setWidth(UnitValue.createPercentValue(100));
            table2.addCell(new Cell().add(new Paragraph("Terms and Conditions\n 1. Subject To Kanchipuram Jurisdiction E.&O.E.\n2. Items once sold cannot be returned.\n3. Any Warranty Claim will Not BE Accepted Without Warranty Card ")));
            table2.addCell(new Cell().add(new Paragraph("\n\n\nReceiver's Signature").setBold()));
            table2.addCell(new Cell().add(new Paragraph("\n\n\nAuthorized Signature").setBold()));
            document.add(table2);
            Paragraph paragraph3 = new Paragraph("THANKYOU FOR YOUR BUSINESS");
            paragraph3.setTextAlignment(TextAlignment.CENTER);
            paragraph3.setBorder(new SolidBorder(1f));
            paragraph3.setWidth(UnitValue.createPointValue(pageSize.getWidth()));
            paragraph3.setMarginBottom(0);
            paragraph3.setMarginTop(0);
            document.add(paragraph3);

        } catch (FileNotFoundException e ){
            e.printStackTrace();
        } finally {
            document.close();
        }
        
    }
}