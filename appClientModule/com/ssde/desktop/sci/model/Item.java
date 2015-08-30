/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssde.desktop.sci.model;

/**
 *
 * @author Jorge Rivera Andrade (SSDE)
 */
public class Item {
    private String ID;
    private String Nombre;
    private String Descripcion;
    private int Cantidad;
    private double precio;
    
    public Item() {
    	
    }

    public Item(String id, String name, String desc, String cant, String precio) throws NumberFormatException{
    	setID(id);
    	setNombre(name);
    	setDescripcion(desc);
    	setCantidad(Integer.parseInt(cant));
    	setPrecio(Double.parseDouble(precio));
    }
    /**
     * @return the ID
     */
    public String getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * @return the Nombre
     */
    public String getNombre() {
        return Nombre;
    }

    /**
     * @param Nombre the Nombre to set
     */
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    /**
     * @return the Descripcion
     */
    public String getDescripcion() {
        return Descripcion;
    }

    /**
     * @param Descripcion the Descripcion to set
     */
    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    /**
     * @return the Cantidad
     */
    public int getCantidad() {
        return Cantidad;
    }

    /**
     * @param Cantidad the Cantidad to set
     */
    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }
    
    public String toString() {
    	return "(id: "+getID()+", nombre: "+getNombre()+", descripcion: "+getDescripcion()+", cantidad: "+getCantidad()+")";
    }

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
}
