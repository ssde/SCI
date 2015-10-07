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
import com.ssde.desktop.sci.AppUtils;
import com.ssde.desktop.sci.model.Item;
import com.ssde.desktop.sci.model.Provider;

public class ReportItems
{
  static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
  static SimpleDateFormat sdf_date = new SimpleDateFormat("dd/MM/yyyy");
  static SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm");
  private static AppUtils apputils = new AppUtils();
  private static double total;
  private static String TIMESTAMP = "";
  private static String DATE = "";
  private static String TIME = "";
  private static String FILE = "PDF/Reportes/Productos/Reporte_";
  private static Font catFont =  new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL, BaseColor.BLACK);
  private static Font subFont =  new Font(Font.FontFamily.HELVETICA,  8, Font.NORMAL, BaseColor.BLACK);
  private static Font headFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
  private static Font redFont =  new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, BaseColor.RED);
  
  public static void createReport(List<Item> content)
  {
    try
    {
      Document document = new Document();
      Date tmp = new Date();
      total = 0;
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
    document.addSubject("Reporte de productos");
    document.addKeywords("SCI, PDF, Reportes");
    document.addAuthor(System.getProperty("user.name"));
    document.addCreator("Sistema de control de inventario (SSDE, 2015)");
  }
  
  private static void addContent(Document document, List<Item> content)
    throws DocumentException
  {
    Paragraph title = new Paragraph("Sistema de control de inventario - Reporte de productos", catFont);
    Paragraph subtitle = new Paragraph("Fecha: " + DATE + " - " + "Hora: " + TIME, catFont);
    subtitle.setAlignment(2);
    addEmptyLine(subtitle, 2);
    
    document.add(title);
    document.add(subtitle);
    
    createTable(document, content);
    createTotals(document);
  }
  
  private static void createTotals(Document document)
    throws DocumentException
  {
    PdfPTable table = new PdfPTable(7);
    table.setWidthPercentage(100);
    
    PdfPCell c1 = new PdfPCell(new Phrase("Total: ", headFont));
    c1.setHorizontalAlignment(2);
    c1.setBorder(2);
    c1.setPadding(8.0F);
    c1.setColspan(5);
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase("$" + round(total, 2), redFont));
    c1.setHorizontalAlignment(2);
    c1.setBorder(2);
    c1.setPadding(8.0F);
    c1.setColspan(2);
    table.addCell(c1);
    
    document.add(table);
  }
  
  private static void createTable(Document document, List<Item> content)
    throws DocumentException
  {
    PdfPTable table = new PdfPTable(7);
    table.setWidthPercentage(100);
    
    Provider tmp = new Provider();
    
    PdfPCell defCell = table.getDefaultCell();
    defCell.setBorder(0);
    defCell.setPadding(6.0F);
    
    PdfPCell c1 = new PdfPCell(new Phrase("Código", headFont));
    c1.setHorizontalAlignment(0);
    c1.setBorder(2);
    c1.setPadding(8.0F);
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase("Nombre", headFont));
    c1.setHorizontalAlignment(0);
    c1.setBorder(2);
    c1.setPadding(8.0F);
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase("Descripción", headFont));
    c1.setHorizontalAlignment(0);
    c1.setBorder(2);
    c1.setPadding(8.0F);
    c1.setColspan(2);
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase("Proveedor", headFont));
    c1.setHorizontalAlignment(0);
    c1.setBorder(2);
    c1.setPadding(8.0F);
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase("Precio", headFont));
    c1.setHorizontalAlignment(0);
    c1.setBorder(2);
    c1.setPadding(8.0F);
    table.addCell(c1);
    
    c1 = new PdfPCell(new Phrase("Existencias", headFont));
    c1.setHorizontalAlignment(0);
    c1.setBorder(2);
    c1.setPadding(8.0F);
    table.addCell(c1);
    table.setHeaderRows(1);
    for (Item p : content)
    {
      c1 = new PdfPCell(new Phrase(p.getID(), subFont));
      c1.setBorder(0);
      c1.setPadding(8.0F);
      table.addCell(c1);
      
      c1 = new PdfPCell(new Phrase(p.getNombre(), subFont));
      c1.setBorder(0);
      c1.setPadding(8.0F);
      table.addCell(c1);
      
      c1 = new PdfPCell(new Phrase(p.getDescripcion(), subFont));
      c1.setBorder(0);
      c1.setColspan(2);
      c1.setPadding(8.0F);
      table.addCell(c1);
      
//      String id = "";
      try {
//    	  id = p.getProveedor();
          tmp = apputils.getProvider(p.getProveedor());
      } catch (NumberFormatException nfe) {
    	  System.out.println("no provider for item: "+p.getNombre());
    	  tmp = new Provider();
      } catch (Exception e) {
    	  tmp = new Provider();
      }
      
      c1 = new PdfPCell(new Phrase(tmp.getAlias(), subFont));
      c1.setBorder(0);
      
      c1.setPadding(8.0F);
      table.addCell(c1);
      
      c1 = new PdfPCell(new Phrase(String.valueOf(p.getPrecio()), subFont));
      c1.setBorder(0);
      
      c1.setPadding(8.0F);
      table.addCell(c1);
      
      c1 = new PdfPCell(new Phrase(String.valueOf(p.getCantidad()), subFont));
      c1.setBorder(0);
      
      c1.setPadding(8.0F);
      table.addCell(c1);
      
      total += p.getPrecio() * p.getCantidad();
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
