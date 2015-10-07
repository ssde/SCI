package com.ssde.desktop.sci.model;

public class Item {
	private String ID;
  	private String Nombre;
  	private String Descripcion;
  	private int Cantidad;
  	private double precio;
  	private String proveedor;
  
  	public Item() {}
  
  	public Item(String id, String name, String desc, String cant, String precio, String proveedor) {
	    setID(id);
	    setNombre(name);
	    setDescripcion(desc);
	    setCantidad(Integer.parseInt(cant));
	    setPrecio(Double.parseDouble(precio));
	    setProveedor(proveedor);
  	}
  
  	public String getID() {
  		return this.ID;
  	}
  
  	public void setID(String ID) {
  		this.ID = ID;
  	}
  
  	public String getNombre() {
  		return this.Nombre;
  	}
  
  	public void setNombre(String Nombre) {
  		this.Nombre = Nombre;
  	}
  
  	public String getDescripcion() {
  		return this.Descripcion;
  	}
  
  public void setDescripcion(String Descripcion)
  {
    this.Descripcion = Descripcion;
  }
  
  public int getCantidad()
  {
    return this.Cantidad;
  }
  
  public void setCantidad(int Cantidad)
  {
    this.Cantidad = Cantidad;
  }
  
  public String toString()
  {
    return "(id: " + getID() + ", nombre: " + getNombre() + ", descripcion: " + getDescripcion() + ", cantidad: " + getCantidad() + ", precio: " + this.precio + ", proveedor: "+this.proveedor+")";
  }
  
  public double getPrecio()
  {
    return this.precio;
  }
  
  public void setPrecio(double precio)
  {
    this.precio = precio;
  }
  
  public String getProveedor()
  {
    return this.proveedor;
  }
  
  public void setProveedor(String proveedor)
  {
    this.proveedor = proveedor;
  }
}
