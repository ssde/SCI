package com.ssde.desktop.sci;

import com.ssde.desktop.sci.db.CreateTables;
import com.ssde.desktop.sci.db.Statements;
import com.ssde.desktop.sci.model.Item;
import com.ssde.desktop.sci.model.Provider;
import com.ssde.desktop.sci.pdf.ReportItems;
import com.ssde.desktop.sci.pdf.ReportProviders;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class AppUtils
{
  private InfoMessages dialog;
  
  public void initializeDB()
  {
    CreateTables ct = new CreateTables("inventario.db");
    ct.setTables();
  }
  
  public Item getItem(String code)
  {
    Statements stmt = new Statements("inventario.db");
    Item item = stmt.getItemById(code);
    
    return item;
  }
  
  public String round(double value, int places)
  {
    if (places < 0) {
      throw new IllegalArgumentException();
    }
    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    
    return String.valueOf(bd.doubleValue());
  }
  
  public String addItemToSelected(String text, Item item, int salidas)
  {
    return createTable(text, String.valueOf(salidas), item.getNombre(), String.valueOf(item.getPrecio()), "$" + round(item.getPrecio() * salidas, 2));
  }
  
  private String createTableRow(String col1, String col2, String col3, String col4)
  {
    String row = "<tr style=\"text-align: center;font-family:Verdana;font-size:12pt;\" >";
    
    row = row + "<td width=\"10%\">" + col1 + "</td>";
    row = row + "<td width=\"60%\">" + col2 + "</td>";
    row = row + "<td width=\"15%\">" + col3 + "</td>";
    row = row + "<td width=\"15%\">" + col4 + "</td>";
    row = row + "</tr>";
    
    return row;
  }
  
  private String createTable(String currentable, String col1, String col2, String col3, String col4)
  {
    if (currentable.equals("")) {
      currentable = "<table width=\"390px\"></table>";
    }
    String table = currentable.replace("</table>", "");
    
    table = table + createTableRow(col1, col2, col3, col4);
    table = table + "</table>";
    
    return table;
  }
  
  public boolean applySalida(String code, int exist, int cant)
  {
    Statements stmt = new Statements("inventario.db");
    
    exist -= cant;
    stmt.updateItemCant(code, exist);
    
    return true;
  }
  
  public boolean comparePasswords(char[] password)
  {
    String pass = "";
    Statements stmt = new Statements("inventario.db");
    char[] arrayOfChar;
    int j = (arrayOfChar = password).length;
    for (int i = 0; i < j; i++)
    {
      char c = arrayOfChar[i];
      pass = pass + c;
    }
    return stmt.isEqual(pass);
  }
  
  public boolean comparePasswords(char[] psswd1, char[] psswd2)
  {
    String pass1 = "";
    String pass2 = "";
    char[] arrayOfChar;
    int j = (arrayOfChar = psswd1).length;
    for (int i = 0; i < j; i++)
    {
      char c = arrayOfChar[i];
      pass1 = pass1 + c;
    }
    j = (arrayOfChar = psswd2).length;
    for (int i = 0; i < j; i++)
    {
      char c = arrayOfChar[i];
      pass2 = pass2 + c;
    }
    if (pass1.equals(pass2)) {
      return true;
    }
    return false;
  }
  
  public void updatePassword(char[] password)
  {
    String pass = "";
    Statements stmt = new Statements("inventario.db");
    char[] arrayOfChar;
    int j = (arrayOfChar = password).length;
    for (int i = 0; i < j; i++)
    {
      char c = arrayOfChar[i];
      pass = pass + c;
    }
    stmt.updatePassword(pass);
  }
  
  public boolean verifyCode(String code)
  {
    Item item = getItem(code);
    if (item != null) {
      return false;
    }
    return true;
  }
  
  public Item searchCode(String code)
  {
    Item item = getItem(code);
    if (item == null) {
      return null;
    }
    return item;
  }
  
  public void saveItem(Item item)
  {
    Statements stmt = new Statements("inventario.db");
    stmt.InsertItem(item);
  }
  
  public void updateItem(Item item, String cant)
  {
    Statements stmt = new Statements("inventario.db");
    int total = 0;
    int cnt = Integer.parseInt(cant);
    if (cnt < 0) {
      total = item.getCantidad() - cnt;
    } else {
      total = item.getCantidad() + cnt;
    }
    stmt.updateItem(item.getID(), item.getNombre(), item.getDescripcion(), total, item.getPrecio(), item.getProveedor());
  }
  
  public boolean deleteItem(String code, int exist)
  {
    Statements stmt = new Statements("inventario.db");
    if (exist > 0) {
      return false;
    }
    stmt.deleteItem(code);
    return true;
  }
  
  public int getMinimo()
  {
    Statements stmt = new Statements("inventario.db");
    
    return stmt.getMinimo();
  }
  
  public String getMessage()
  {
    Statements stmt = new Statements("inventario.db");
    return stmt.getMessage();
  }
  
  public void saveMinimo(int min)
  {
    Statements stmt = new Statements("inventario.db");
    stmt.saveMinimo(min);
  }
  
  public void saveMessage(String message)
  {
    Statements stmt = new Statements("inventario.db");
    stmt.saveMessage(message);
  }
  
  public boolean saveProveedor(Provider p)
  {
    Statements stmt = new Statements("inventario.db");
    
    return stmt.saveProvider(p.getAlias(), p.getNombre(), p.getDireccion(), p.getTelefono(), p.getRfc());
  }
  
  public void PDFReporteProductos()
  {
    Statements stmt = new Statements("inventario.db");
    ReportItems.createReport(stmt.getAllItems());
  }
  
  public void PDFReporteProveedores()
  {
    Statements stmt = new Statements("inventario.db");
    ReportProviders.createReport(stmt.getAllProviders());
  }
  
  public List<Provider> getProviders()  {
    List<Provider> result = new ArrayList<Provider>();
    
    Statements stmt = new Statements("inventario.db");
    
    result = stmt.getAllProviders();
    
    return result;
  }
  
  public Provider getProvider(String id)
  {
    Provider result = new Provider();
    Statements stmt = new Statements("inventario.db");
    
    if(id.contains(" - "))
    	result = stmt.getProvider(id);
    
    return result;
  }
  
  public void infoMessage(String message)
  {
    this.dialog = new InfoMessages(message);
    this.dialog.setDefaultCloseOperation(2);
    this.dialog.setVisible(true);
  }
}
