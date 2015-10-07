package com.ssde.desktop.sci.model;

/**
*
* @author Jorge Rivera Andrade (SSDE)
*/
public class Provider {
	
	private long id;
	private String alias;
	private String nombre;
	private String direccion;
	private String rfc;
	private String telefono;
	
	public Provider () {
		alias = "";
		nombre="";
		direccion="";
		telefono="";
		rfc="";
	}
	
	public Provider (String alias, String nombre, String direccion, String telefono, String rfc) {
		this.alias = alias;
		this.nombre = nombre;
		this.direccion = direccion;
		this.rfc = rfc;
		this.telefono = telefono;
	}
	
//	public String toString() {
//		return "Alias: "+alias+"\nNombre:"+nombre+"\nDireccion: "+direccion+"\nR.F.C.:"+rfc+"\nTelefono: "+telefono; 
//	}
	public String toString() {
		return alias + " - " + rfc;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
}