/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssde.desktop.sci.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Jorge Rivera Andrade (SSDE)
 */
public class Connector {
    
    private String forName = null;
    private String db = null;
    private Connection c = null;
    
    public Connector(String db) {
        this.forName = "org.sqlite.JDBC";
        this.db = "jdbc:sqlite:"+db;
    }
    
    public Connection getConnection() {
//    	System.out.println("forName: "+forName);
//    	System.out.println("db: "+db);

    	try{
    		if(c==null) {
	            Class.forName(forName);
	            c = DriverManager.getConnection(db);
	            c.setAutoCommit(false);
    		}
        } catch (Exception e) {
        	System.out.print("Connector: ");
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
        
        return c;
    }
    
    public void closeConnection() {
        try{
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
    }
}
