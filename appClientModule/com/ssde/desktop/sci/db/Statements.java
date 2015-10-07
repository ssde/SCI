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
import java.util.UUID;

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

	public Statements(String db) {
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
		String sql = "select * from minimos;";
		Connection conn = getConnector();

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				min = rs.getInt(2);
			}
			rs.close();
			stmt.close();
			// closeConnection();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			min = 0;
		} finally {
			closeConnection();
		}

		return min;
	}

	public void saveMinimo(int min) {
		String sql = "update minimos set min=" + min + " where id = 1;";
		Connection conn = getConnector();

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			conn.commit();

			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			closeConnection();
		}

		// infoMessage("Minimo guardado");
	}

	public String getMessage() {
		String message = "";
		String sql = "select * from messages;";
		Connection conn = getConnector();

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				message = rs.getString(2);
			}
			rs.close();
			stmt.close();
			closeConnection();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			closeConnection();
		}
		return message;
	}

	public void saveMessage(String message) {
		String sql = "update messages set message='" + message + "' where id = 1;";
		Connection conn = getConnector();

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			conn.commit();

			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			closeConnection();
		}
		// infoMessage("Mensaje guardado");
	}

	public List<Item> getAllItems() {
		List<Item> result = new ArrayList<Item>();
		String sql = "select * from items";
		Item temp = null;
		Connection conn = getConnector();

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				temp = new Item();
				temp.setID(rs.getString(1));
				temp.setNombre(rs.getString(2));
				temp.setDescripcion(rs.getString(3));
				temp.setCantidad(rs.getInt(4));
				temp.setPrecio(rs.getDouble(5));
				temp.setProveedor(rs.getString(6));
//				System.out.println(temp.toString());
				result.add(temp);
			}
			rs.close();
			stmt.close();
			// closeConnection();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			closeConnection();
		}

		return result;
	}
	
	public List<Provider> getAllProviders() {
		List<Provider> result = new ArrayList<Provider>();
		Provider tmp = null;
		
		String sql = "select * from proveedores;";
		
		Connection conn = getConnector();
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				tmp = new Provider();
				tmp.setId(rs.getLong(1));
				tmp.setAlias(rs.getString(2));
				tmp.setNombre(rs.getString(3));
				tmp.setDireccion(rs.getString(4));
				tmp.setTelefono(rs.getString(5));
				tmp.setRfc(rs.getString(6));
//				System.out.println(tmp.toString());
				result.add(tmp);
			}
			
		} catch (Exception e) {
			System.out.println("Statements - getAllProvider: "+e.getMessage());
		} finally {
			this.closeConnection();
		}
		
		return result;
	}
	
	public boolean saveProvider(String alias, String nombre, String direccion, String telefono, String rfc) {
		Provider p = new Provider(alias,nombre,direccion,telefono,rfc);
		
		return saveProvider(p);
	}
	
	public boolean saveProvider(Provider p) {
		boolean result = false;
		UUID uuid = UUID.randomUUID();
		
		String sql = "insert into proveedores (id,alias,nombre,direccion,telefono,rfc) values ('"+uuid.toString()+"','"+p.getAlias()+"','"+p.getNombre()+"','"+p.getDireccion()+"','"+p.getTelefono()+"','"+p.getRfc()+"');";
//		System.out.println(sql);
		Connection conn = getConnector();
		
		try {
			try {
				stmt = conn.createStatement();
			} catch (Exception e) {
				System.out.println("Statements - saveProvider: Connection null");
			}
			try {
				stmt.executeUpdate(sql);
			} catch (Exception e) {
				System.out.println("Statements - saveProvider: stmt null \n"+e.getClass().getName()+" - Message: "+e.getMessage());
			}
			try {
				conn.commit();
			} catch (Exception e) {
				System.out.println("commit exception: "+e.getClass().getName()+" - "+e.getMessage());
			}
//			stmt.close();
			result = true;
		} catch (Exception e) {
			System.out.println("Statements - saveProvider: "+e.getMessage());
		} finally {
			closeConnection();
		}
		
		return result;
	}

	public Item getItemById(String id) {
		String sql = "select * from items where id='" + id + "';";
		// System.out.println(sql);
		Item temp = null;
		Connection conn = getConnector();

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				temp = new Item();
				// System.out.println(rs.getString("id"));
				temp.setID(rs.getString(1));
				temp.setNombre(rs.getString(2));
				temp.setDescripcion(rs.getString(3));
				temp.setCantidad(rs.getInt(4));
				temp.setPrecio(rs.getDouble(5));
				temp.setProveedor(rs.getString(6));
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			closeConnection();
		}

		return temp;
	}
	// if(rs.getString(1).equals("")){
	// rs.close();
	// stmt.close();
	// closeConnection();
	// return null;
	// }

	public void updateItemName(String id, String nombre) {
		String sql = "update items set nombre='" + nombre + "' where id = '" + id + "';";
		Connection conn = getConnector();

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			conn.commit();

			stmt.close();
			closeConnection();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			closeConnection();
		}
	}

	public void updateItemDesc(String id, String desc) {
		String sql = "update items set desc='" + desc + "' where id = '" + id + "';";
		Connection conn = getConnector();

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			conn.commit();

			stmt.close();
			closeConnection();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			closeConnection();
		}
	}

	public void updateItemCant(String id, int cant) {
		String sql = "update items set cant=" + cant + " where id = '" + id + "';";
		Connection conn = getConnector();

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			conn.commit();

			stmt.close();
			closeConnection();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			closeConnection();
		}
	}

	public void updateItem(String id, String nombre, String desc, int cant, double precio, String proveedor) {
		String sql = "update items set nombre='" + nombre + "', desc='" + desc + "', cant=" + cant + ", precio="
				+ precio + ", proveedor='" + proveedor + "' where id = '" + id + "';";
		Connection conn = getConnector();

//		System.out.println(sql);
		
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			conn.commit();

			stmt.close();
			closeConnection();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			closeConnection();
		}
		// infoMessage("Elemento actualizado");
	}

	public void InsertItem(Item item) {
		String sql = "insert into items (id,nombre,desc,cant,precio,proveedor) values('" + item.getID() + "','" + item.getNombre()
				+ "','" + item.getDescripcion() + "'," + item.getCantidad() + "," + item.getPrecio() + ",'"+item.getProveedor()+"');";
//		System.out.println(sql);
		Connection conn = getConnector();

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			conn.commit();

			stmt.close();
			closeConnection();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			infoMessage("El código ya existe en la base de datos");
		} finally {
			closeConnection();
		}

		// infoMessage("Elemento guardado");
	}

	public void deleteItem(String id) {
		String sql = "delete from items where id = '" + id + "';";
		Connection conn = getConnector();

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			conn.commit();

			stmt.close();
			closeConnection();
		} catch (Exception e) {
			infoMessage("Error al borrar el registro");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			closeConnection();
		}

		// infoMessage("Elemento borrado");
	}
	
	public Provider getProvider(String id) {
		Provider result = new Provider();
		String tmp [] = id.split(" - ");
		String sql = "";
		
		if(tmp.length>1)
			sql = "select * from proveedores where alias='"+tmp[0]+"' and rfc='"+tmp[1]+"';";
		else return result;
		
		Connection conn = this.getConnector();
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				result.setAlias(rs.getString(2));
				result.setNombre(rs.getString(3));
				result.setDireccion(rs.getString(4));
				result.setTelefono(rs.getString(5));
				result.setRfc(rs.getString(6));
			}
			
			stmt.close();
		} catch (Exception e) {
			System.out.println("Statements - getProvider: "+e.getMessage());
		} finally {
			this.closeConnection();
		}
		
		return result;
	}

	public boolean isEqual(String password) {
		String sql = "select * from contrasena where pass='" + password + "'";
		String res = "";
		Connection conn = getConnector();

		// System.out.println(sql);

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// System.out.println(rs.getString(2));
				res = rs.getString(2);
			}
			rs.close();
			stmt.close();
			// closeConnection();
		} catch (Exception e) {
			// closeConnection();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			closeConnection();
		}

		if (res.equals(password))
			return true;

		return false;
	}

	public void updatePassword(String pass) {
		String sql = "update contrasena set pass='" + pass + "' where id = 1";

		Connection conn = getConnector();

		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			conn.commit();

			stmt.close();
			infoMessage("Contraseña actualizada");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			infoMessage("Hubo un error al actualizar la contraseña");
		} finally {
			closeConnection();
		}
	}

	public long getMaxId() {
		long result = 0;
		String sql = "select id from proveedores order by id desc limit 1;";
		Connection conn = getConnector();

		// System.out.println(sql);

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				// System.out.println(rs.getString(2));
				result = rs.getLong(1);
			}
			rs.close();
			stmt.close();
			// closeConnection();
		} catch (Exception e) {
			// closeConnection();
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			closeConnection();
		}

		result = result + 1;

		return result;
	}

	public void infoMessage(String message) {
		dialog = new InfoMessages(message);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

}
