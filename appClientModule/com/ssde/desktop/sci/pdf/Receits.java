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
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ssde.desktop.sci.model.Item;

public class Receits {

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
	static SimpleDateFormat sdf_date = new SimpleDateFormat("dd/MM/yyyy");
	static SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm");
	

	private static String TIMESTAMP = "";
	private static String DATE = "";
	private static String TIME = "";
	private static String FILE 	= "PDF/Notas/Nota_";
	private static Font catFont 	= new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
	private static Font redFont 	= new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.RED);
	private static Font subFont 	= new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
	private static Font headFont 	= new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
	private static Font cellFont 	= new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLUE);

	public static void createReceit(List<Item> content, List<Integer> quantities) {
		try {
			Document document = new Document();
			Date tmp = new Date();
			TIMESTAMP = sdf.format(tmp);
			DATE = sdf_date.format(tmp);
			TIME = sdf_time.format(tmp);
			PdfWriter.getInstance(document, new FileOutputStream(FILE+TIMESTAMP+".pdf"));
			document.open();
			addMetaData(document);
			//addTitlePage(document);
			addContent(document, content, quantities);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	// iText allows to add metadata to the PDF which can be viewed in your Adobe
	// Reader
	// under File -> Properties
	private static void addMetaData(Document document) {
		document.addTitle("Nota_"+TIMESTAMP);
		document.addSubject("Nota de compra");
		document.addKeywords("SCI, PDF, Notas");
		document.addAuthor(System.getProperty("user.name"));
		document.addCreator("Sistema de control de inventario (SSDE, 2015)");
	}
		
	private static void addContent(Document document, List<Item> content, List<Integer> quantities) throws DocumentException {
		Paragraph title = new Paragraph("Sistema de controlde inventario - Nota de compra", catFont);
		Paragraph subtitle = new Paragraph("Fecha: "+DATE+" - "+"Hora: "+TIME, subFont);
		subtitle.setAlignment(Element.ALIGN_RIGHT);
		addEmptyLine(subtitle,2);
		
		document.add(title);
		document.add(subtitle);

		// add a table
		createTable(document, content, quantities);
		createTotals(document, content, quantities);
	}
	
	private static void createTotals(Document document, List<Item> content, List<Integer> q) throws DocumentException {
		int i = 0;
		double total = 0.0;
	    PdfPTable table = new PdfPTable(5);
	    table.setWidthPercentage(90);
		
		PdfPCell c1 = new PdfPCell(new Phrase("Total: ",headFont));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		c1.setBorder(PdfPCell.BOTTOM);
		c1.setPadding(8);
		c1.setColspan(4);
		table.addCell(c1);
		
		for(Item item : content) {
			total += item.getPrecio()*q.get(i);
			i++;
		}
		
		c1 = new PdfPCell(new Phrase("$"+round(total,2),redFont));
		c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		c1.setBorder(PdfPCell.BOTTOM);
		c1.setPadding(8);
		c1.setColspan(2);
		table.addCell(c1);
		
		document.add(table);
	}
		
	private static void createTable(Document document, List<Item> content, List<Integer> quantities) throws DocumentException {
		int i = 0;
	    PdfPTable table = new PdfPTable(5);
	    table.setWidthPercentage(90);
	    
	    PdfPCell defCell = table.getDefaultCell();
	    defCell.setBorder(PdfPCell.NO_BORDER);
	    defCell.setPadding(5);
		
		PdfPCell c1 = new PdfPCell(new Phrase("Unidades",headFont));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setBorder(PdfPCell.BOTTOM);
		c1.setPadding(8);
		table.addCell(c1);
		
		c1 = new PdfPCell(new Phrase("Descripción",headFont));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setBorder(PdfPCell.BOTTOM);
		c1.setPadding(8);
		c1.setColspan(2);
		table.addCell(c1);
		
		c1 = new PdfPCell(new Phrase("Precio Unitario",headFont));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setBorder(PdfPCell.BOTTOM);
		c1.setPadding(8);
		table.addCell(c1);
		table.setHeaderRows(1);

		c1 = new PdfPCell(new Phrase("Subtotal",headFont));
		c1.setHorizontalAlignment(Element.ALIGN_LEFT);
		c1.setBorder(PdfPCell.BOTTOM);
		c1.setPadding(8);
		table.addCell(c1);
		table.setHeaderRows(1);
		
		for(Item j : content) {
	    	c1 = new PdfPCell(new Phrase(String.valueOf(quantities.get(i)),cellFont));
			c1.setBorder(PdfPCell.NO_BORDER);
			c1.setPadding(8);
			table.addCell(c1);
			
			c1 = new PdfPCell(new Phrase(j.getDescripcion(),cellFont));
			c1.setColspan(2);
			c1.setBorder(PdfPCell.NO_BORDER);
			c1.setPadding(8);
			table.addCell(c1);

	    	c1 = new PdfPCell(new Phrase(String.valueOf(j.getPrecio()),cellFont));
			c1.setBorder(PdfPCell.NO_BORDER);
			c1.setPadding(8);
			table.addCell(c1);
			
	    	c1 = new PdfPCell(new Phrase(String.valueOf(String.valueOf("$"+round(j.getPrecio()*quantities.get(i), 2))),cellFont));
			c1.setBorder(PdfPCell.NO_BORDER);
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			c1.setPadding(8);
			table.addCell(c1);
			
			i++;
		}
		document.add(table);	
	}
		
	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
	
	public static String round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    
	    return String.valueOf(bd.doubleValue());
	}

}
