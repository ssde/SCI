package com.ssde.desktop.sci;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JDialog;

import com.ssde.desktop.sci.db.CreateTables;
import com.ssde.desktop.sci.db.Statements;
import com.ssde.desktop.sci.model.Item;

public class AppUtils {
	
	private InfoMessages dialog;
	
	public AppUtils() {
		
	}
	
	public void initializeDB() {
//		//System.out.println("Initializing database");
//		String DBPath = new File(SCI.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getPath();
//		String DBPath = getJarFolder();
//		if(DBPath.contains("SCI.jar"))
//			DBPath = DBPath.substring(0, DBPath.indexOf("SCI.jar"));
//		//System.out.println(DBPath);
		CreateTables ct = new CreateTables("inventario.db");
		ct.setTables();
//		//System.out.println("Database initialized");

	}
	
	public Item getItem(String code) {
		Statements stmt = new Statements("inventario.db");
		Item item = stmt.getItemById(code);
		
		return item;
	}
	
	public String round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    
	    return String.valueOf(bd.doubleValue());
	}
	
	public String addItemToSelected(String text, Item item, int salidas) {
//		return createTableRow(String.valueOf(salidas), item.getNombre(), "$"+round(item.getPrecio()*salidas, 2));
		return createTable(text, String.valueOf(salidas), item.getNombre(), String.valueOf(item.getPrecio()), "$"+round(item.getPrecio()*salidas, 2));
	}
	
	private String createTableRow(String col1, String col2, String col3, String col4) {
		String row = "<tr style=\"text-align: center;font-family:Verdana;font-size:12pt;\" >";
		
		row += "<td width=\"10%\">"+col1+"</td>";
		row += "<td width=\"60%\">"+col2+"</td>";
		row += "<td width=\"15%\">"+col3+"</td>";
		row += "<td width=\"15%\">"+col4+"</td>";
		row += "</tr>";
		
		return row;
	}
	
	private String createTable(String currentable, String col1, String col2, String col3, String col4) {
		if(currentable.equals("")){
			currentable = "<table width=\"390px\"></table>";
		}
		String table = currentable.replace("</table>", "");
		
		table += createTableRow(col1, col2, col3, col4);
		table += "</table>";
//		System.out.println(table);
		return table;
	}
	
	public boolean applySalida(String code, int exist, int cant) {
		Statements stmt = new Statements("inventario.db");
		
		exist -= cant;
		stmt.updateItemCant(code, exist);
//		if(exist<=getMinimo()){
//			infoMessage(getMessage()+"\n\nQuedan "+exist+" elementos en almacen");
//		} 
		
		return true;
	}
	
	public boolean comparePasswords(char [] password){
		String pass = "";
		Statements stmt = new Statements("inventario.db");
		
		for(char c : password) {
			pass += c;
		}
		
		return stmt.isEqual(pass);
//		if(stmt.isEqual(pass))
//			return true;
//		else
//			infoMessage("Contraseña incorrecta");
//		
//		return false;
	}
	
	public boolean comparePasswords(char [] psswd1, char [] psswd2){
		String pass1 = "";
		String pass2 = "";
		
//		System.out.println("Psswd1: "+psswd1+"\nPsswd2"+psswd2);
		
		
		for(char c : psswd1) {
			pass1 += c;
		}
		for(char c : psswd2) {
			pass2 += c;
		}
		
//		System.out.println("Strings:\n1->"+pass1+"\n2->"+pass2);
		
		if(pass1.equals(pass2))
			return true;
//		else
//			infoMessage("Las contraseñas no coinciden");
		
		return false;
	}
	
	public void updatePassword(char[] password) {
		String pass = "";
		Statements stmt = new Statements("inventario.db");
		
		for(char c : password) {
			pass += c;
		}
		
		stmt.updatePassword(pass);
	}
	
	public boolean verifyCode(String code) {
		Item item = getItem(code);
		
		if(item!=null) {
//			infoMessage("El código ya existe en la base de datos\n");
			return false;
		} else {
//			infoMessage("Código nuevo");
			return true;
		}
	}
	
	public Item searchCode(String code) {
		Item item = getItem(code);
		
		if(item == null) {
//			infoMessage("El código no existe en la base de datos");
			return null;
		}
		
		return item;
	}
	
	public void saveItem(Item item) {
		Statements stmt = new Statements("inventario.db");
		stmt.InsertItem(item);
	}
	
	public void updateItem(Item item, String cant) {
		Statements stmt = new Statements("inventario.db");
		int total=0;
		int cnt = Integer.parseInt(cant);
		if(cnt<0)
			total = item.getCantidad()-cnt;
		else
			total = item.getCantidad()+cnt;
		
		stmt.updateItem(item.getID(), item.getNombre(), item.getDescripcion(), total, item.getPrecio());
	}
	
	public boolean deleteItem(String code, int exist) {
		Statements stmt = new Statements("inventario.db");
		
		if(exist>0) {
			return false;//infoMessage("Todavía hay elementos en existencia");
		}
		
		stmt.deleteItem(code);
		return true;
	}
	
	public int getMinimo() {
		Statements stmt = new Statements("inventario.db");
		
		return stmt.getMinimo();
	}
	
	public String getMessage() {
		Statements stmt = new Statements("inventario.db");
		return stmt.getMessage();
	}
	
	public void saveMinimo(int min){
		Statements stmt = new Statements("inventario.db");
		stmt.saveMinimo(min);
	}
	
	public void saveMessage(String message) {
		Statements stmt = new Statements("inventario.db");
		stmt.saveMessage(message);
	}
	
	public void infoMessage(String message) {
		dialog = new InfoMessages(message);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
	
//	private String getJarFolder() {
//		// get name and path
//		String name = getClass().getName().replace('.', '/');
//		name = getClass().getResource("/" + name + ".class").toString();
//		// remove junk
//		name = name.substring(0, name.indexOf(".jar"));
//		name = name.substring(name.lastIndexOf(':')-1, name.lastIndexOf('/')+1).replace('%', ' ');
//		// remove escape characters
//		String s = "";
//		for (int k=0; k<name.length(); k++) {
//		  s += name.charAt(k);
//		  if (name.charAt(k) == ' ') k += 2;
//		}
//		// replace '/' with system separator char
//		return s.replace('/', File.separatorChar);
//	}
}
