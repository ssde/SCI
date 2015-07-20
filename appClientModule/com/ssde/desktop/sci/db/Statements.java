/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssde.desktop.sci.db;

import java.sql.*;

import com.ssde.desktop.sci.InfoMessages;
import com.ssde.desktop.sci.model.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;

/**
 *
 * @author Jorge Rivera Andrade (SSDE)
 */
public class Statements {
    
    private Connector c;
    private Statement stmt;
    private ResultSet rs;
    private InfoMessages dialog;
    
    public Statements (String db) {
        this.c = new Connector(db);
    }
    
    private Connection getConnector() {
    	return c.getConnection();
    }
    
    private void closeConnection() {
    	c.closeConnection();
    }
    
    public int getMinimo() {
    	int min = 0;
    	String sql = "select * from minimos";
        Connection conn = getConnector();

        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while( rs.next() ) {
                min = rs.getInt(2);
            }
            rs.close();
            stmt.close();
            closeConnection();           
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            return 0;
        }
        
        return min;        
    }
    
    public void saveMinimo(int min) {
        String sql = "update minimos set min="+min+" where id = 1";
        Connection conn = getConnector();

        try{
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.commit();
            
            stmt.close();
            closeConnection();            
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
        
        infoMessage("Minimo guardado");
    }
    
    public String getMessage() {
    	String message = "";
    	String sql = "select * from messages";
        Connection conn = getConnector();

        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while( rs.next() ) {
                message = rs.getString(2);
            }
            rs.close();
            stmt.close();
            closeConnection();           
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            return "";
        }
        
        return message;        
    }
    
    public void saveMessage(String message) {
        String sql = "update messages set message='"+message+"' where id = 1";
        Connection conn = getConnector();

        try{
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.commit();
            
            stmt.close();
            closeConnection();            
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
        infoMessage("Mensaje guardado");
    }

    public List<Item> getAllItems() {
        List<Item> result = new ArrayList<Item>();
        String sql = "select * from items";
        Item temp = new Item();
        Connection conn = getConnector();

        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while( rs.next() ) {
                temp.setID(rs.getString(1));
                temp.setNombre(rs.getString(2));
                temp.setDescripcion(rs.getString(3));
                temp.setCantidad(rs.getInt(4));
                result.add(temp);
            }
            rs.close();
            stmt.close();
            closeConnection();           
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
        
        return result;
    }
    
    public Item getItemById( String id ) {
        String sql = "select * from items where id='"+id+"'";
        Item temp = new Item();
        Connection conn = getConnector();

        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while( rs.next() ) {
                temp.setID(rs.getString(1));
                temp.setNombre(rs.getString(2));
                temp.setDescripcion(rs.getString(3));
                temp.setCantidad(rs.getInt(4));
            }
            rs.close();
            stmt.close();
            closeConnection();           
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
        
        return temp;
    }
    
    public void updateItemName(String id, String nombre) {
        String sql = "update items set nombre='"+nombre+"' where id = '"+id+"'";
        Connection conn = getConnector();

        try{
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.commit();
            
            stmt.close();
            closeConnection();            
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
    }
    
    public void updateItemDesc(String id, String desc) {
        String sql = "update items set desc='"+desc+"' where id = '"+id+"'";
        Connection conn = getConnector();

        try{
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.commit();
            
            stmt.close();
            closeConnection();            
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
    }
    
    public void updateItemCant(String id, int cant) {
        String sql = "update items set cant="+cant+" where id = '"+id+"'";
        Connection conn = getConnector();

        try{
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.commit();
            
            stmt.close();
            closeConnection();            
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
    }
    
    public void updateItem(String id, String nombre, String desc, int cant) {
    	String sql = "update items set nombre='"+nombre+"', desc='"+desc+"', cant="+cant+" where id = '"+id+"'";
        Connection conn = getConnector();

        try{
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.commit();
            
            stmt.close();
            closeConnection();            
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
    }
    
    public void InsertItem(Item item) {
    	String sql = "insert into items (id,nombre,desc,cant) values("+item.getID()+","+item.getNombre()+","+item.getDescripcion()+","+item.getCantidad()+")";
        Connection conn = getConnector();

        try{
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.commit();
            
            stmt.close();
            closeConnection();            
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            infoMessage("El código ya existe en la base de datos");
        }
        
		infoMessage("Elemento guardado");
    }
    
    public void deleteItem(String id) {
        String sql = "delete from items where id = '"+id+"'";
        Connection conn = getConnector();

        try{
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.commit();
            
            stmt.close();
            closeConnection();            
        } catch (Exception e) {
        	infoMessage("Error al borrar el registro");
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
        
		infoMessage("Elemento borrado");
    }
    
	public void infoMessage(String message) {
		dialog = new InfoMessages(message);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

}
