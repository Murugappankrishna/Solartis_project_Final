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

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
// function to generate PDF
public class CreatePDF {
    CartDAO cartDAO = new CartImpl();

    void createInvoice(int userid, String modeOfPayment, String UserName) {
        ResultSet rs = cartDAO.generateBillPDF(userid);// values in cart table
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        double totalBeforeTax = 0.0;
        int totalQuantity = 0;
        double totalPriceInclusiveOfTax = 0.0;
        double totalTax = 0.0;
        String invoiceSequence = "INV-" + currentDate + "-" + currentTime + "";
        String path = "invoice.pdf";
        PdfWriter writer = null;
        Document billReceipt = null;
        Table productDetails = new Table(new float[]{3, 1, 1, 1, 2, 1, 2});// Table for  Product Values in BillReceipt
        productDetails.setMarginBottom(0);
        productDetails.setMarginTop(0);
        productDetails.setWidth(UnitValue.createPercentValue(100));
        productDetails.addCell(new Cell().add("Product Name").setBorder(new SolidBorder(1f)));
        productDetails.addCell(new Cell().add("Quantity").setBorder(new SolidBorder(1f)));
        productDetails.addCell(new Cell().add("Unit Price").setBorder(new SolidBorder(1f)));
        productDetails.addCell(new Cell().add("Tax Percent").setBorder(new SolidBorder(1f)));
        productDetails.addCell(new Cell().add("Total Price Before Tax").setBorder(new SolidBorder(1f)));
        productDetails.addCell(new Cell().add("Tax Price").setBorder(new SolidBorder(1f)));
        productDetails.addCell(new Cell().add("Price Inclusive of Tax").setBorder(new SolidBorder(1f)));
        try {
            //Retriving And Injecting To The productDetails Table in PDF
            while (rs.next()) {
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
                totalTax = totalPriceInclusiveOfTax - totalBeforeTax;
                productDetails.addCell(new Cell().add(productName));
                productDetails.addCell(new Cell().add(String.valueOf(quantity)));
                productDetails.addCell(new Cell().add(String.valueOf(priceBeforeTax)));
                productDetails.addCell(new Cell().add(String.valueOf(taxPercent)));
                productDetails.addCell(new Cell().add(String.valueOf(totalPriceBeforeTax)));
                productDetails.addCell(new Cell().add(String.valueOf(taxAmount)));
                productDetails.addCell(new Cell().add(String.valueOf(priceInclusiveOfTax)));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            writer = new PdfWriter(path);
            PdfDocument pdfDoc = new PdfDocument(writer);
            billReceipt = new Document(pdfDoc);
            PageSize pageSize = pdfDoc.getDefaultPageSize();
            Paragraph details = new Paragraph("GSTIN:32AAHCR7467A1ZI              TaxInvoice                   OriginalRecipient");//Paragraph for  TopDetails Values in BillReceipt
            details.setMarginBottom(0);
            details.setMarginTop(0);
            details.setTextAlignment(TextAlignment.CENTER);
            details.setBorder(new SolidBorder(1f));
            details.setWidth(UnitValue.createPointValue(pageSize.getWidth()));
            billReceipt.add(details);
            Paragraph address = new Paragraph("Hello World Plaza\nNo 6 Main Street Kanchipuram Tamil Nadu India\nMobile No:9090897490,9089456787\nEmail:heloworld@admin.com");//Paragraph for  Address Values in BillReceipt
            address.setMarginBottom(0);
            address.setMarginTop(0);
            address.setTextAlignment(TextAlignment.CENTER);
            address.setBorder(new SolidBorder(1f));
            address.setWidth(UnitValue.createPointValue(pageSize.getWidth()));
            billReceipt.add(address);
            Table table = new Table(new float[]{1, 1});
            table.setMarginBottom(0);
            table.setMarginTop(0);
            table.setWidth(UnitValue.createPercentValue(100));
            Paragraph billToParagraph = new Paragraph().add("Bill To:" + UserName);// Paragraph for  Bill To  Values in BillReceipt
            billToParagraph.setTextAlignment(TextAlignment.LEFT);
            table.addCell(new Paragraph().add(billToParagraph));
            Paragraph detailsParagraph = new Paragraph()
                    .add("Date: " + currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n")
                    .add("Invoice No: " + invoiceSequence + "\n")
                    .add("Payment Mode: " + modeOfPayment);
            detailsParagraph.setTextAlignment(TextAlignment.LEFT);
            table.addCell(new Paragraph().add(detailsParagraph));
            billReceipt.add(table);
            Paragraph notes = new Paragraph().add("Notes:");// Paragraph For Notes Section
            notes.setMarginBottom(0);
            notes.setMarginTop(0);
            notes.setTextAlignment(TextAlignment.LEFT);
            notes.setBorder(new SolidBorder(1f));
            notes.setWidth(UnitValue.createPercentValue(pageSize.getWidth()));
            billReceipt.add(notes);
            billReceipt.add(productDetails);
            Table bankDetailsTable = new Table(new float[]{1, 2});
            bankDetailsTable.setWidth(UnitValue.createPercentValue(100));
            bankDetailsTable.setMarginBottom(0);
            bankDetailsTable.setMarginTop(0);
            bankDetailsTable.addCell(new Cell().add(new Paragraph("Company's Bank Details\nAccount Name:Hello World Plaza\nAccount No:3645217692453156\nBranch Name:Kanchipuram Main Branch \nIFSC Code:IDIBP00036\n")));// Paragraph To Add Bank Details
            bankDetailsTable.addCell(new Cell().add(new Paragraph("Total Quantity:" + totalQuantity + "\nTotal Amount Before Tax:" + totalBeforeTax + "\nTotal Amount After Tax:" + totalPriceInclusiveOfTax + "\nTotal Tax:" + totalTax + "")));// Paragraph To Add The Heading
            billReceipt.add(bankDetailsTable);
            Table tAndc_Signature = new Table(new float[]{1, 1, 1});
            tAndc_Signature.setMarginBottom(0);
            tAndc_Signature.setMarginTop(0);
            tAndc_Signature.setWidth(UnitValue.createPercentValue(100));
            tAndc_Signature.addCell(new Cell().add(new Paragraph("Terms and Conditions\n 1. Subject To Kanchipuram Jurisdiction E.&O.E.\n2. Items once sold cannot be returned.\n3. Any Warranty Claim will Not BE Accepted Without Warranty Card ")));// Paragraph To  Add T&C
            tAndc_Signature.addCell(new Cell().add(new Paragraph("\n\n\nReceiver's Signature").setBold()));// Paragraph To Add Signature
            tAndc_Signature.addCell(new Cell().add(new Paragraph("\n\n\nAuthorized Signature").setBold()));// Paragraph To Add Signature
            billReceipt.add(tAndc_Signature);
            Paragraph thanksNote = new Paragraph("THANKYOU FOR YOUR BUSINESS");// Paragraph To Add ThankYou Note
            thanksNote.setTextAlignment(TextAlignment.CENTER);
            thanksNote.setBorder(new SolidBorder(1f));
            thanksNote.setWidth(UnitValue.createPointValue(pageSize.getWidth()));
            thanksNote.setMarginBottom(0);
            thanksNote.setMarginTop(0);
            billReceipt.add(thanksNote);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            billReceipt.close();
        }

    }
    //  Method To Oppen The Generated Bill
    void oppenPDF(){
        String filePath = "invoice.pdf";
        File pdfFile = new File(filePath);
        if (pdfFile.exists()) {
            try {

                Desktop.getDesktop().open(pdfFile);// opening the Bill PDF File
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}