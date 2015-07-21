package com.ssde.desktop.sci;

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
	
	public boolean applySalida(String code, int exist, int cant) {
		Statements stmt = new Statements("inventario.db");
		
		if(exist<cant) {
			infoMessage("La cantidad en existencia es\nmenor de la que se quiere sacar");
			return false;
		} else {
			exist -= cant;
			stmt.updateItemCant(code, exist);
			if(exist<=getMinimo()){
				infoMessage(getMessage()+"\n\nQuedan "+exist+" elementos en almacen");
			} else {
				infoMessage("Quedan "+exist+" elementos en almacen");
			}
			return true;
		}
	}
	
	public boolean verifyCode(String code) {
		Item item = getItem(code);
		
		if(item!=null) {
			infoMessage("El código ya existe en la base de datos\n"+item.toString());
			return false;
		} else {
			infoMessage("Código nuevo");
			return true;
		}
	}
	
	public Item searchCode(String code) {
		Item item = getItem(code);
		
		if(item == null) {
			infoMessage("El código no existe en la base de datos");
			return null;
		}
		
		return item;
	}
	
	public void saveItem(Item item) {
		Statements stmt = new Statements("inventario.db");
		stmt.InsertItem(item);
	}
	
	public void updateItem(Item item) {
		Statements stmt = new Statements("inventario.db");
		stmt.updateItem(item.getID(), item.getNombre(), item.getDescripcion(), item.getCantidad());
	}
	
	public void deleteItem(String code, int exist) {
		Statements stmt = new Statements("inventario.db");
		if(exist==0)
			stmt.deleteItem(code);
		else
			infoMessage("Todavía hay elementos en existencia");
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
