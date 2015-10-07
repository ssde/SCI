package com.ssde.desktop.sci;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.ssde.desktop.sci.model.Provider;

public class Providers
  extends JFrame
{
  private static final long serialVersionUID = 1L;
  private Font defaultFont = new Font("Verdana", 0, 14);
  private Color defaultColor = Color.BLACK;
  private Font messageFont = new Font("Verdana", 1, 16);
  private Color messageColor = Color.RED;
  private JPanel contentPane;
  private JTextField fld_alias;
  private JTextField fld_nombre;
  private JTextField fld_direccion;
  private JTextField fld_tel;
  private JTextField fld_rfc;
  private JLabel lbl_info_messages;
  private Provider provider;
  private AppUtils apputils;
  
  public Providers()
  {
//    setType(Window.Type.POPUP);
    
    setTitle("Sistema de Control de Inventario, SSDE - Alta de Proveedores");
    this.apputils = new AppUtils();
    
    setBounds(100, 100, 650, 650);
    this.contentPane = new JPanel();
    this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    this.contentPane.setLayout(new BorderLayout(0, 0));
    this.contentPane.setFont(this.defaultFont);
    setContentPane(this.contentPane);
    
    JPanel proveedor = new customJPanel();
    proveedor.setBorder(new EmptyBorder(5, 5, 5, 5));
    proveedor.setFont(this.defaultFont);
    proveedor.setForeground(this.defaultColor);
    proveedor.setLayout(null);
    this.contentPane.add(proveedor);
    
    JLabel lbl_tel = new JLabel("Telefono: ");
    lbl_tel.setHorizontalAlignment(4);
    lbl_tel.setBounds(40, 272, 150, 16);
    lbl_tel.setFont(this.defaultFont);
    lbl_tel.setForeground(this.defaultColor);
    proveedor.add(lbl_tel);
    
    this.fld_tel = new JTextField();
    this.fld_tel.setBounds(250, 260, 300, 40);
    this.fld_tel.setFont(this.defaultFont);
    this.fld_tel.setForeground(this.defaultColor);
    this.fld_tel.setColumns(150);
    this.fld_tel.addMouseListener(new MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        Providers.this.lbl_info_messages.setText("");
      }
    });
    proveedor.add(this.fld_tel);
    
    JLabel lbl_alias = new JLabel("Alias: ");
    lbl_alias.setHorizontalAlignment(4);
    lbl_alias.setFont(this.defaultFont);
    lbl_alias.setForeground(this.defaultColor);
    lbl_alias.setBounds(40, 72, 150, 16);
    proveedor.add(lbl_alias);
    
    this.fld_alias = new JTextField();
    this.fld_alias.setBounds(250, 60, 300, 40);
    this.fld_alias.setFont(this.defaultFont);
    this.fld_alias.setForeground(this.defaultColor);
    this.fld_alias.addMouseListener(new MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        Providers.this.lbl_info_messages.setText("");
      }
    });
    proveedor.add(this.fld_alias);
    
    JLabel lbl_nombre = new JLabel("Nombre: ");
    lbl_nombre.setHorizontalAlignment(4);
    lbl_nombre.setFont(this.defaultFont);
    lbl_nombre.setForeground(this.defaultColor);
    lbl_nombre.setBounds(40, 122, 150, 16);
    proveedor.add(lbl_nombre);
    
    this.fld_nombre = new JTextField();
    this.fld_nombre.setFont(this.defaultFont);
    this.fld_nombre.setForeground(this.defaultColor);
    this.fld_nombre.setBounds(250, 110, 300, 40);
    proveedor.add(this.fld_nombre);
    
    JLabel lbl_direccion = new JLabel("Dirección: ");
    lbl_direccion.setHorizontalAlignment(4);
    lbl_direccion.setFont(this.defaultFont);
    lbl_direccion.setForeground(this.defaultColor);
    lbl_direccion.setBounds(40, 172, 150, 16);
    proveedor.add(lbl_direccion);
    
    this.fld_direccion = new JTextField();
    this.fld_direccion.setFont(this.defaultFont);
    this.fld_direccion.setForeground(this.defaultColor);
    this.fld_direccion.setBounds(250, 160, 300, 40);
    proveedor.add(this.fld_direccion);
    
    JLabel lbl_rfc = new JLabel("R.F.C.: ");
    lbl_rfc.setHorizontalAlignment(4);
    lbl_rfc.setFont(this.defaultFont);
    lbl_rfc.setForeground(this.defaultColor);
    lbl_rfc.setBounds(40, 222, 150, 16);
    proveedor.add(lbl_rfc);
    
    this.fld_rfc = new JTextField();
    this.fld_rfc.setFont(this.defaultFont);
    this.fld_rfc.setForeground(this.defaultColor);
    this.fld_rfc.setBounds(250, 210, 300, 40);
    proveedor.add(this.fld_rfc);
    
    JButton btn_save = new JButton("Guardar");
    btn_save.setFont(this.defaultFont);
    btn_save.setForeground(this.defaultColor);
    btn_save.setBounds(120, 360, 150, 40);
    btn_save.addMouseListener(new MouseAdapter() {
    	public void mouseClicked(MouseEvent e) {
	        if (Providers.this.validateFields()) {
	        	provider = new Provider(fld_alias.getText(), fld_nombre.getText(), fld_direccion.getText(), fld_tel.getText(), fld_rfc.getText());
	        	if (apputils.saveProveedor(Providers.this.provider)) {
		            lbl_info_messages.setText("El proveedor ha sido guardado exitosamente");
		            clearFields();
	        	} else {
	        		lbl_info_messages.setText("Hubo un problema al guardar el proveedor");
	        	}
	        }
    	}
    });
    proveedor.add(btn_save);
    
    JButton btn_cancel = new JButton("Cancelar");
    btn_cancel.setFont(this.defaultFont);
    btn_cancel.setForeground(this.defaultColor);
    btn_cancel.setBounds(380, 360, 150, 40);
    btn_cancel.addMouseListener(new MouseAdapter()
    {
      public void mouseClicked(MouseEvent e)
      {
        Providers.this.fld_alias.setText("");
        Providers.this.fld_nombre.setText("");
        Providers.this.fld_direccion.setText("");
        Providers.this.fld_tel.setText("");
        Providers.this.fld_rfc.setText("");
      }
    });
    proveedor.add(btn_cancel);
    
    JButton btn_close = new JButton("Cerrar");
    btn_close.setFont(this.defaultFont);
    btn_close.setForeground(this.defaultColor);
    btn_close.setBounds(480, 360, 120, 40);
    btn_close.setActionCommand("Close");
    
    this.lbl_info_messages = new JLabel();
    this.lbl_info_messages.setHorizontalAlignment(0);
    this.lbl_info_messages.setFont(this.messageFont);
    this.lbl_info_messages.setForeground(this.messageColor);
    this.lbl_info_messages.setBounds(5, 595, 658, 20);
    proveedor.add(this.lbl_info_messages);
  }
  
  private boolean validateFields()
  {
    if (this.fld_alias.getText().equals(""))
    {
      this.lbl_info_messages.setText("El campo alias es obligatorio");
      return false;
    }
    if (this.fld_nombre.getText().equals(""))
    {
      this.lbl_info_messages.setText("El campo nombre es obligatorio");
      return false;
    }
    if (this.fld_direccion.getText().equals(""))
    {
      this.lbl_info_messages.setText("El campo dirección es obligatorio");
      return false;
    }
    if (this.fld_tel.getText().equals(""))
    {
      this.lbl_info_messages.setText("El campo teléfono es obligatorio");
      return false;
    }
    if (this.fld_rfc.getText().equals(""))
    {
      this.lbl_info_messages.setText("El campo R.F.C. es obligatorio");
      return false;
    }
    return true;
  }
  
  private void clearFields()
  {
    this.fld_alias.setText("");
    this.fld_nombre.setText("");
    this.fld_direccion.setText("");
    this.fld_tel.setText("");
    this.fld_rfc.setText("");
  }
}
