package com.ssde.desktop.sci.pdf;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ssde.desktop.sci.model.Provider;

public class ReportProviders
{
  static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
  static SimpleDateFormat sdf_date = new SimpleDateFormat("dd/MM/yyyy");
  static SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm");
  private static String TIMESTAMP = "";
  private static String DATE = "";
  private static String TIME = "";
  private static String FILE = "PDF/Reportes/Proveedores/Reporte_";
  private static Font catFont =  new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL, BaseColor.BLACK);
  private static Font subFont =  new Font(Font.FontFamily.HELVETICA,  8, Font.NORMAL, BaseColor.BLACK);
  private static Font headFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
  
  public static void createReport(List<Provider> content)
  {
    try
    {
      Document document = new Document();
      Date tmp = new Date();
      TIMESTAMP = sdf.format(tmp);
      DATE = sdf_date.format(tmp);
      TIME = sdf_time.format(tmp);
      PdfWriter.getInstance(document, new FileOutputStream(FILE + TIMESTAMP + ".pdf"));
      document.open();
      addMetaData(document);
      
      addContent(document, content);
      document.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  private static void addMetaData(Document document)
  {
    document.addTitle("Reporte_" + TIMESTAMP);
    document.addSubject("Reporte de proveedores");
    document.addKeywords("SCI, PDF, Reportes");
    document.addAuthor(System.getProperty("user.name"));
    document.addCreator("Sistema de control de inventario (SSDE, 2015)");
  }
  
  private static void addContent(Document document, List<Provider> content)
    throws DocumentException
  {
    Paragraph title = new Paragraph("Sistema de control de inventario - Reporte de proveedores", catFont);
    Paragraph subtitle = new Paragraph("Fecha: " + DATE + " - " + "Hora: " + TIME, catFont);
    subtitle.setAlignment(2);
    addEmptyLine(subtitle, 2);
    
    document.add(title);
    document.add(subtitle);
    
    createTable(document, content);
  }
  
  private static void createTable(Document document, List<Provider> content)
    throws DocumentException
  {
    PdfPTable table = new PdfPTable(6);
    table.setWidthPercentage(100);
    
    PdfPCell defCell = table.getDefaultCell();
    defCell.setBorder(0);
    defCell.setPadding(6.0F);
    
    PdfPCell c1 = new PdfPCell(new Phrase("Alias", headFont));
    c1.setHorizontalAlignment(0);
    c1.setBorder(2);
    c1.setPadding(8.0F);
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase("Nombre", headFont));
    c1.setHorizontalAlignment(0);
    c1.setBorder(2);
    c1.setPadding(8.0F);
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase("Dirección", headFont));
    c1.setHorizontalAlignment(0);
    c1.setBorder(2);
    c1.setPadding(8.0F);
    c1.setColspan(2);
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase("Teléfono", headFont));
    c1.setHorizontalAlignment(0);
    c1.setBorder(2);
    c1.setPadding(8.0F);
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase("R.F.C.", headFont));
    c1.setHorizontalAlignment(0);
    c1.setBorder(2);
    c1.setPadding(8.0F);
    table.addCell(c1);
    table.setHeaderRows(1);
    for (Provider p : content)
    {
      c1 = new PdfPCell(new Phrase(p.getAlias(), subFont));
      c1.setBorder(0);
      c1.setPadding(8.0F);
      table.addCell(c1);
      
      c1 = new PdfPCell(new Phrase(p.getNombre(), subFont));
      c1.setBorder(0);
      c1.setPadding(8.0F);
      table.addCell(c1);
      
      c1 = new PdfPCell(new Phrase(p.getDireccion(), subFont));
      c1.setBorder(0);
      c1.setColspan(2);
      c1.setPadding(8.0F);
      table.addCell(c1);
      
      c1 = new PdfPCell(new Phrase(p.getTelefono(), subFont));
      c1.setBorder(0);
      
      c1.setPadding(8.0F);
      table.addCell(c1);
      
      c1 = new PdfPCell(new Phrase(p.getRfc(), subFont));
      c1.setBorder(0);
      
      c1.setPadding(8.0F);
      table.addCell(c1);
    }
    document.add(table);
  }
  
  private static void addEmptyLine(Paragraph paragraph, int number)
  {
    for (int i = 0; i < number; i++) {
      paragraph.add(new Paragraph(" "));
    }
  }
  
  public static String round(double value, int places)
  {
    if (places < 0) {
      throw new IllegalArgumentException();
    }
    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    
    return String.valueOf(bd.doubleValue());
  }
}
