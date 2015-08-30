/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssde.desktop.sci.db;

import java.sql.*;

/**
 *
 * @author @author Jorge Rivera Andrade (SSDE)
 */
public class CreateTables {
    private Connector c;
    private Statement stmt;
    
    public CreateTables(String db) {
    	this.c = new Connector(db);
    }
    
    private Connection getConnector() {
    	return c.getConnection();
    }
    
    private void closeConnection() {
    	c.closeConnection();
    }
    public void setTables() {
        String sql = "";
        Connection conn = getConnector();

        try{
            stmt = conn.createStatement();
            sql = "create table IF NOT EXISTS items (id text primary key not null, nombre text not null, desc text not null, cant int, precio real);";
            stmt.executeUpdate(sql);
            sql = "create table IF NOT EXISTS minimos (id int primary key not null, min int not null);";
            stmt.executeUpdate(sql);
            sql = "create table IF NOT EXISTS messages (id int primary key not null, message text not null);";
            stmt.executeUpdate(sql);
            sql = "create table IF NOT EXISTS contrasena (id int primary key not null, pass text not null);";
            stmt.executeUpdate(sql);
            sql = "insert into minimos(id,min) values(1,0);";
            stmt.executeUpdate(sql);
            sql = "insert into messages(id,message) values(1,'El numero de articulos ha\nalcanzado el minimo configurado');";
            stmt.executeUpdate(sql);
            sql = "insert into contrasena(id,pass) values(1,'SCIpass');";
            stmt.executeUpdate(sql);
            conn.commit();
            closeConnection();
            stmt.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
        }
    }
}
