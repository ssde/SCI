package com.ssde.desktop.sci;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
//import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import com.ssde.desktop.sci.model.Item;
import com.ssde.desktop.sci.pdf.Receits;

@SuppressWarnings("serial")
public class SCI extends JFrame{
	
	private Font defaultFont = new Font("Verdana",	Font.PLAIN,	14);
	private Color defaultColor = Color.BLACK; //Black
	private Font messageFont = new Font("Verdana", 	Font.BOLD,	16);
	private Color messageColor = Color.RED; //Red
	
	private List<Item> ventaActual;
	private List<Integer> ventaSalidas;
	
	//Private fields
	private JPanel 		contentPane;
	private JPanel 		altas;
	private JPanel 		bajas;
	private JPanel 		cambios;
	private JTabbedPane tabbedPane;
	private JTextField 	fld_egresos_code;
	private JTextField 	fld_egresos_art;
	private JTextPane 	txt_egresos_desc;
	private JTextField 	fld_egresos_exist;
	private JSpinner 	spnr_egresos_cant;
	private JTextField	fld_egresos_precio;
	private JTextPane 	txtpnI_egresos_items;
	private JLabel 		lbl_egresos_total;
	private JButton 	btn_egresos_apply;
	private JButton 	btn_egresos_pdf;
	private JLabel 		lbl_egresos_info_messages;
	private JTextField 	fld_altas_code;
	private JTextField 	fld_altas_art;
	private JTextPane 	txt_altas_desc;
	private JSpinner 	spnr_altas_cant;
	private JTextField	fld_altas_precio;
	private JButton 	btn_altas_verify;
	private JPasswordField	psswd_altas_psswd;
	private JButton 	btn_altas_apply;
	private JLabel 		lbl_altas_info_messages;
	private JPasswordField	psswd_bajas_psswd;
	private JTextField 	fld_bajas_code;
	private JTextField 	fld_bajas_art;
	private JTextPane 	txt_bajas_desc;
	private JTextField 	fld_bajas_exist;
	private JButton 	btn_bajas_apply;
	private JButton 	btn_bajas_verify;
	private JLabel 		lbl_bajas_info_messages;
	private JTextField	fld_cambios_precio;
	private JTextField 	fld_cambios_code;
	private JTextField 	fld_cambios_art;
	private JPasswordField	psswd_cambios_psswd;
	private JTextPane 	txt_cambios_desc;
	private JTextField	fld_cambios_exist;
	private JSpinner 	spnr_cambios_cant;
	private JButton 	btn_cambios_apply;
	private JButton 	btn_cambios_verify;
	private JLabel 		lbl_cambios_info_messages;
	private JPasswordField 	psswd_config_old;
	private JPasswordField 	psswd_config_pss1;
	private JPasswordField 	psswd_config_pss2;
	private JPasswordField psswd_config_auth;
	private JSpinner 	spnr_config_minimo;
	private JTextPane 	txt_config_desc;
	private JButton 	btn_config_save;
	private JButton 	btn_config_psswd;
	private JLabel 		lbl_config_psswd_match;
	private JLabel 		lbl_config_info_messages;
	private SpinnerModel spinnermodel;
	private AppUtils 	apputils;
	private Item 		globalitem;
    private InfoMessages dialog;
	private double		total;
//	private Font 		sysMsg; 
	
	//Main triggers the view
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SCI frame = new SCI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/* (non-Java-doc)
	 * @see java.lang.Object#Object()
	 */
	public SCI() {
//		sysMsg = new Font();
		ventaActual = new ArrayList<Item>();
		ventaSalidas = new ArrayList<Integer>();
		apputils = new AppUtils();
		spinnermodel = new SpinnerNumberModel(0,0,10000,1); //init,min,max,step
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.setFont(defaultFont);
		setContentPane(contentPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(defaultFont);
		tabbedPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				//System.out.println(tabbedPane.getSelectedIndex());
//				tabbedPane.fo
				if(tabbedPane.getSelectedIndex() == 4) {					
					spnr_config_minimo.setValue(apputils.getMinimo());
					txt_config_desc.setText(apputils.getMessage());
					spnr_config_minimo.setEnabled(false);
					psswd_config_auth.setText("");
					psswd_config_old.setText("");
					psswd_config_old.setEditable(false);
					psswd_config_pss1.setText("");
					psswd_config_pss1.setEditable(false);
					psswd_config_pss2.setText("");
					psswd_config_pss2.setEditable(false);
					psswd_config_auth.setText("");
					btn_config_save.setEnabled(false);
					btn_config_psswd.setEnabled(false);
					lbl_config_psswd_match.setText("");
					lbl_config_info_messages.setText("");
					resetFields();
				} else {
					if(tabbedPane.getSelectedIndex()>0) {
						if(altas.isShowing()) {
							resetBajas();
							resetCambios();
						}
						
						if(bajas.isShowing()) {
							resetAltas();
							resetCambios();
						}
						if(cambios.isShowing()) {
							resetAltas();
							resetBajas();
						}
//						resetFields();
					} else {
						resetFields();
					}
				}
			}
		});
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		/*
		 * Panel Egresos
		 */
		JPanel egresos = new customJPanel();
		tabbedPane.addTab("Egresos", null, egresos, null);
		egresos.setBorder(new EmptyBorder(5,5,5,5));
		egresos.setFont(defaultFont);
		egresos.setForeground(defaultColor);
		egresos.setLayout(null);
		
		JLabel lbl_egresos_code = new JLabel("Código:");
		lbl_egresos_code.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_egresos_code.setBounds(40, 22, 150, 16);
		lbl_egresos_code.setFont(defaultFont);
		lbl_egresos_code.setForeground(defaultColor);
		egresos.add(lbl_egresos_code);
		
		fld_egresos_code = new JTextField();
		fld_egresos_code.setBounds(250, 10, 150, 40);
		fld_egresos_code.setFont(defaultFont);
		fld_egresos_code.setForeground(defaultColor);
		fld_egresos_code.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				lbl_egresos_info_messages.setText("");
			}
		});
		egresos.add(fld_egresos_code);
		fld_egresos_code.setColumns(150);
		
		JLabel lbl_egresos_art = new JLabel("Artículo:");
		lbl_egresos_art.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_egresos_art.setBounds(40, 67, 150, 16);
		lbl_egresos_art.setFont(defaultFont);
		lbl_egresos_art.setForeground(defaultColor);
		egresos.add(lbl_egresos_art);
		
		fld_egresos_art = new JTextField();
		fld_egresos_art.setEditable(false);
		fld_egresos_art.setBounds(250, 55, 300, 40);
		fld_egresos_art.setFont(defaultFont);
		fld_egresos_art.setForeground(defaultColor);
		egresos.add(fld_egresos_art);
		fld_egresos_art.setColumns(150);
		
		JLabel lbl_egresos_desc = new JLabel("Descripción:");
		lbl_egresos_desc.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_egresos_desc.setBounds(40, 100, 150, 16);
		lbl_egresos_desc.setFont(defaultFont);
		lbl_egresos_desc.setForeground(defaultColor);
		egresos.add(lbl_egresos_desc);
		
		txt_egresos_desc = new JTextPane();
		txt_egresos_desc.setEditable(false);
		txt_egresos_desc.setBounds(255, 100, 290, 100);
		txt_egresos_desc.setFont(defaultFont);
		txt_egresos_desc.setForeground(defaultColor);
		egresos.add(txt_egresos_desc);
		
		JLabel lbl_egresos_exist = new JLabel("Existencias:");
		lbl_egresos_exist.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_egresos_exist.setBounds(40, 217, 150, 16);
		lbl_egresos_exist.setFont(defaultFont);
		lbl_egresos_exist.setForeground(defaultColor);
		egresos.add(lbl_egresos_exist);
		
		fld_egresos_exist = new JTextField();
		fld_egresos_exist.setEditable(false);
		fld_egresos_exist.setBounds(250, 205, 100, 40);
		fld_egresos_exist.setFont(defaultFont);
		fld_egresos_exist.setForeground(defaultColor);
		egresos.add(fld_egresos_exist);
		fld_egresos_exist.setColumns(10);
		
		JLabel lbl_egresos_precio = new JLabel("Precio:");
		lbl_egresos_precio.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_egresos_precio.setBounds(360, 217, 70, 16);
		lbl_egresos_precio.setFont(defaultFont);
		lbl_egresos_precio.setForeground(defaultColor);
		egresos.add(lbl_egresos_precio);
		
		fld_egresos_precio = new JTextField();
		fld_egresos_precio.setEditable(false);
		fld_egresos_precio.setBounds(450, 205, 100, 40);
		fld_egresos_precio.setFont(defaultFont);
		fld_egresos_precio.setForeground(defaultColor);
		egresos.add(fld_egresos_precio);
		
		JLabel lbl_egresos_cant = new JLabel("Cantidad a vender:");
		lbl_egresos_cant.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_egresos_cant.setBounds(40, 257, 150, 16);
		lbl_egresos_cant.setFont(defaultFont);
		lbl_egresos_cant.setForeground(defaultColor);
		egresos.add(lbl_egresos_cant);
		
		spnr_egresos_cant = new JSpinner(spinnermodel);
		spnr_egresos_cant.setBounds(250, 245, 100, 40);
		spnr_egresos_cant.setFont(defaultFont);
		spnr_egresos_cant.setForeground(defaultColor);
		egresos.add(spnr_egresos_cant);
		
		JButton btn_egresos_buscar = new JButton("Buscar");
		btn_egresos_buscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Item item = apputils.searchCode(fld_egresos_code.getText());
				if(item!=null){
					fld_egresos_art.setText(item.getNombre());
					txt_egresos_desc.setText(item.getDescripcion());
					fld_egresos_exist.setText(String.valueOf(item.getCantidad()));
					fld_egresos_precio.setText(String.valueOf(item.getPrecio()));
					btn_egresos_apply.setEnabled(true);
					globalitem = item;
				} else {
					fld_egresos_art.setText("");
					txt_egresos_desc.setText("");
					fld_egresos_exist.setText("");
					fld_egresos_precio.setText("");
					lbl_egresos_info_messages.setText("Código no encontrado");
				}
			}
		});
		btn_egresos_buscar.setBounds(425, 11, 125, 40);
		btn_egresos_buscar.setFont(defaultFont);
		btn_egresos_buscar.setForeground(defaultColor);
		egresos.add(btn_egresos_buscar);
		
		
		JButton btnNewButton = new JButton("Nueva cuenta");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				txtpnI_egresos_items.setText("");
//				lbl_egresos_total.setText("");
//				total = 0.0;
				resetEgresos();
				ventaActual = new ArrayList<Item>();
				ventaSalidas = new ArrayList<Integer>();
			}
		});
		btnNewButton.setBounds(541, 370, 117, 40);
		btnNewButton.setFont(defaultFont);
		btnNewButton.setForeground(defaultColor);
		egresos.add(btnNewButton);
		
		btn_egresos_pdf = new JButton("Crear PDF");
		btn_egresos_pdf.setBounds(541, 418, 117, 40);
		btn_egresos_pdf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Receits.createReceit(ventaActual,ventaSalidas);
				lbl_egresos_info_messages.setText("Archivo PDF creado");
			}
		});
		btn_egresos_pdf.setEnabled(false);
		egresos.add(btn_egresos_pdf);
				
		JPanel panel_egresos_items = new JPanel();
		panel_egresos_items.setBounds(5, 297, 524, 290);
		egresos.add(panel_egresos_items);
		panel_egresos_items.setLayout(new BorderLayout(0, 0));
		
		txtpnI_egresos_items = new JTextPane();
		txtpnI_egresos_items.setContentType("text/html");
		txtpnI_egresos_items.setEditable(false);
		txtpnI_egresos_items.setFont(defaultFont);
		txtpnI_egresos_items.setForeground(defaultColor);
		txtpnI_egresos_items.setAutoscrolls(true);
		txtpnI_egresos_items.setText("<html><body width=\"490px\"><table width=\"100%\"></table></body></html>");
		panel_egresos_items.add(txtpnI_egresos_items, BorderLayout.CENTER);
		
		btn_egresos_apply = new JButton("Aplicar");
		btn_egresos_apply.setEnabled(false);
		btn_egresos_apply.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int existencias = Integer.parseInt(fld_egresos_exist.getText());
				int salidas = Integer.parseInt(String.valueOf(spnr_egresos_cant.getValue()));
				if(salidas<=0) {
					lbl_egresos_info_messages.setText("La cantidad a retirar es 0 o negativo, no se agregará a la lista");
				} else if(existencias<salidas) {
					lbl_egresos_info_messages.setText("La cantidad a retirar es mayor a las existencias");
				} else if(apputils.applySalida(fld_egresos_code.getText(), existencias, salidas)) {
					HTMLEditorKit kit = new HTMLEditorKit();
					HTMLDocument doc = (HTMLDocument) txtpnI_egresos_items.getStyledDocument();
					txtpnI_egresos_items.setEditorKit(kit);
					txtpnI_egresos_items.setDocument(doc);
					txtpnI_egresos_items.setFont(defaultFont);
					txtpnI_egresos_items.setForeground(Color.BLUE);

					try {
						kit.insertHTML(doc, doc.getLength(), apputils.addItemToSelected("", globalitem, salidas), 0, 0, null);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					total += salidas*globalitem.getPrecio();
//					System.out.println(txtpnI_egresos_items.getText());
					lbl_egresos_total.setText("$"+apputils.round(total, 2));
					
					existencias -= salidas;
					
					if(apputils.getMinimo()<=existencias)
						lbl_egresos_info_messages.setText("Quedan "+existencias+" elementos en existencia");
					ventaActual.add(globalitem);
					ventaSalidas.add(new Integer(salidas));
					btn_egresos_pdf.setEnabled(true);
					nuevaVenta();
				}
			}
		});
		btn_egresos_apply.setBounds(513, 245, 150, 40);
		btn_egresos_apply.setFont(defaultFont);
		btn_egresos_apply.setForeground(defaultColor);
		egresos.add(btn_egresos_apply);

		JLabel lblNewLabel = new JLabel("Productos seleccionados");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(defaultFont);
		lblNewLabel.setForeground(defaultColor);
		panel_egresos_items.add(lblNewLabel, BorderLayout.NORTH);

		JPanel panel_egresos_total = new JPanel();
		panel_egresos_total.setBounds(541, 297, 117, 61);
		panel_egresos_total.setFont(defaultFont);
		panel_egresos_total.setForeground(defaultColor);
		egresos.add(panel_egresos_total);
		panel_egresos_total.setLayout(new BorderLayout(0, 0));
		
		JLabel lbl_egresos_titulo = new JLabel("Total");
		lbl_egresos_titulo.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_egresos_titulo.setFont(defaultFont);
		lbl_egresos_titulo.setForeground(defaultColor);
		panel_egresos_total.add(lbl_egresos_titulo, BorderLayout.NORTH);
		
		lbl_egresos_total = new JLabel("$$$");
		lbl_egresos_total.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_egresos_total.setFont(messageFont);
		lbl_egresos_total.setForeground(messageColor);
		panel_egresos_total.add(lbl_egresos_total, BorderLayout.CENTER);

		lbl_egresos_info_messages = new JLabel("");
		lbl_egresos_info_messages.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_egresos_info_messages.setBounds(5, 595, 658, 20);
		lbl_egresos_info_messages.setForeground(messageColor);
		lbl_egresos_info_messages.setFont(messageFont);
		egresos.add(lbl_egresos_info_messages);
		
		/*
		 * Panel Altas
		 */				
		altas = new customJPanel();
		tabbedPane.addTab("Altas", null, altas, null);
		altas.setBorder(new EmptyBorder(5,5,5,5));
		altas.setLayout(null);
		
		JLabel lbl_altas_code = new JLabel("Código:");
		lbl_altas_code.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_altas_code.setBounds(40, 72, 150, 16);
		lbl_altas_code.setFont(defaultFont);
		lbl_altas_code.setForeground(defaultColor);
		altas.add(lbl_altas_code);
		
		fld_altas_code = new JTextField();
		fld_altas_code.setEditable(false);
		fld_altas_code.setBounds(250, 60, 150, 40);
		fld_altas_code.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				lbl_altas_info_messages.setText("");
			}
		});
		fld_altas_code.setColumns(150);
		fld_altas_code.setFont(defaultFont);
		fld_altas_code.setForeground(defaultColor);
		altas.add(fld_altas_code);

		JLabel lbl_altas_art = new JLabel("Artículo:");
		lbl_altas_art.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_altas_art.setBounds(40, 122, 150, 16);
		lbl_altas_art.setFont(defaultFont);
		lbl_altas_art.setForeground(defaultColor);
		altas.add(lbl_altas_art);
		
		fld_altas_art = new JTextField();
		fld_altas_art.setEditable(false);
		fld_altas_art.setBounds(250, 110, 300, 40);
		fld_altas_art.setFont(defaultFont);
		fld_altas_art.setForeground(defaultColor);
		altas.add(fld_altas_art);
		fld_altas_art.setColumns(150);
		
		JLabel lbl_altas_desc = new JLabel("Descripción:");
		lbl_altas_desc.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_altas_desc.setBounds(40, 165, 150, 16);
		lbl_altas_desc.setFont(defaultFont);
		lbl_altas_desc.setForeground(defaultColor);
		altas.add(lbl_altas_desc);
		
		txt_altas_desc = new JTextPane();
		txt_altas_desc.setEditable(false);
		txt_altas_desc.setBounds(255, 165, 290, 100);
		txt_altas_desc.setFont(defaultFont);
		txt_altas_desc.setForeground(defaultColor);
		altas.add(txt_altas_desc);
		
		JLabel lbl_altas_exist = new JLabel("Existencias:");
		lbl_altas_exist.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_altas_exist.setBounds(40, 292, 150, 16);
		lbl_altas_exist.setFont(defaultFont);
		lbl_altas_exist.setForeground(defaultColor);
		altas.add(lbl_altas_exist);
		
		spnr_altas_cant = new JSpinner(spinnermodel);
		spnr_altas_cant.setEnabled(false);
		spnr_altas_cant.setBounds(250, 280, 100, 40);
		spnr_altas_cant.setFont(defaultFont);
		spnr_altas_cant.setForeground(defaultColor);
		altas.add(spnr_altas_cant);
		
		btn_altas_verify = new JButton("Verificar");
		btn_altas_verify.setEnabled(false);
		btn_altas_verify.setBounds(425, 60, 125, 40);
		btn_altas_verify.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btn_altas_verify.isEnabled() && !fld_altas_code.getText().equals(""))
					if(apputils.verifyCode(fld_altas_code.getText())) {
						lbl_altas_info_messages.setText("Código nuevo");
						fld_altas_code.setEditable(false);
						fld_altas_art.setEditable(true);
						txt_altas_desc.setEditable(true);
						spnr_altas_cant.setEnabled(true);
						fld_altas_precio.setEditable(true);
						btn_altas_apply.setEnabled(true);
					} else {
						lbl_altas_info_messages.setText("El código ya existe en la base de datos");
					}
			}
		});
		btn_altas_verify.setFont(defaultFont);
		btn_altas_verify.setForeground(defaultColor);
		altas.add(btn_altas_verify);
		
		btn_altas_apply = new JButton("Guardar");
		btn_altas_apply.setEnabled(false);
		btn_altas_apply.setBounds(400, 385, 150, 40);
		btn_altas_apply.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!fld_altas_code.getText().equals("")) {
					String precio = NumUtils.makeToDouble(fld_altas_precio.getText());
					if(precio.equals("0") || precio.contains("-")){
//						infoMessage("El precio introducido no es válido");
						lbl_altas_info_messages.setText("El precio introducido no es válido");
						fld_altas_precio.setText(precio);
					} else {
						try {
							globalitem = new Item(fld_altas_code.getText(), fld_altas_art.getText(), txt_altas_desc.getText(), String.valueOf(spnr_altas_cant.getValue()), fld_altas_precio.getText());
							apputils.saveItem(globalitem);
							lbl_altas_info_messages.setText("Elemento guardado");
							clearFields();
						} catch (NumberFormatException ex) {
							lbl_altas_info_messages.setText("Precio no válido, se ha ajustado, verifíque que es el adecuado");
							fld_altas_precio.setText(precio);
						} catch (Exception ex1) {
							lbl_altas_info_messages.setText("Ocurrió un error. El elemento no fue guardado");
						}
					}
				}
			}
		});
		panel_egresos_total.setFont(defaultFont);
		panel_egresos_total.setForeground(defaultColor);
		altas.add(btn_altas_apply);
		
		JButton btn_altas_cancel = new JButton("Cancelar");
		btn_altas_cancel.setBounds(100, 385, 150, 40);
		btn_altas_cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clearFields();
			}
		});
		altas.add(btn_altas_cancel);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrecio.setBounds(40, 342, 150, 16);
		lblPrecio.setFont(defaultFont);
		lblPrecio.setForeground(defaultColor);
		altas.add(lblPrecio);
		
		fld_altas_precio = new JTextField();
		fld_altas_precio.setEditable(false);
		fld_altas_precio.setColumns(150);
		fld_altas_precio.setBounds(250, 330, 300, 40);
		fld_altas_precio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lbl_altas_info_messages.setText("");
			}
		});
		fld_altas_precio.setFont(defaultFont);
		fld_altas_precio.setForeground(defaultColor);
		altas.add(fld_altas_precio);
		
		psswd_altas_psswd = new JPasswordField();
		psswd_altas_psswd.setBounds(250, 10, 150, 40);
		psswd_altas_psswd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lbl_altas_info_messages.setText("");
			}
		});
		altas.add(psswd_altas_psswd);
		
		JLabel lblContrasea = new JLabel("Contraseña:");
		lblContrasea.setHorizontalAlignment(SwingConstants.RIGHT);
		lblContrasea.setBounds(65, 22, 125, 16);
		lblContrasea.setFont(defaultFont);
		lblContrasea.setForeground(defaultColor);
		altas.add(lblContrasea);
		
		JButton btn_altas_auth = new JButton("Autenticar");
		btn_altas_auth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(apputils.comparePasswords(psswd_altas_psswd.getPassword())) {
					fld_altas_code.setEditable(true);
					btn_altas_verify.setEnabled(true);
				} else {
					lbl_altas_info_messages.setText("Contraseña incorrecta");
				}
			}
		});
		btn_altas_auth.setBounds(425, 11, 125, 40);
		btn_altas_auth.setFont(defaultFont);
		btn_altas_auth.setForeground(defaultColor);
		altas.add(btn_altas_auth);

		lbl_altas_info_messages = new JLabel("");
		lbl_altas_info_messages.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_altas_info_messages.setBounds(5, 595, 658, 20);
		lbl_altas_info_messages.setForeground(messageColor);
		lbl_altas_info_messages.setFont(messageFont);
		altas.add(lbl_altas_info_messages);

		/*
		 * Panel Bajas
		 */
		bajas = new customJPanel();
		tabbedPane.addTab("Bajas", null, bajas, null);
		bajas.setBorder(new EmptyBorder(5,5,5,5));
		bajas.setLayout(null);
		bajas.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				resetFields();
			}
		});
		
		JLabel lbl_bajas_code = new JLabel("Código:");
		lbl_bajas_code.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_bajas_code.setBounds(40, 77, 150, 16);
		lbl_bajas_code.setFont(defaultFont);
		lbl_bajas_code.setForeground(defaultColor);
		bajas.add(lbl_bajas_code);
		
		fld_bajas_code = new JTextField();
		fld_bajas_code.setEditable(false);
		fld_bajas_code.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(fld_bajas_code.getText().isEmpty()) {
					btn_bajas_apply.setEnabled(false);
				}
			}
		});
		fld_bajas_code.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lbl_bajas_info_messages.setText("");
			}
		});
		fld_bajas_code.setBounds(250, 65, 150, 40);
		fld_bajas_code.setFont(defaultFont);
		fld_bajas_code.setForeground(defaultColor);
		bajas.add(fld_bajas_code);
		fld_bajas_code.setColumns(150);
				
		JLabel lbl_bajas_art = new JLabel("Artículo:");
		lbl_bajas_art.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_bajas_art.setBounds(40, 132, 150, 16);
		lbl_bajas_art.setFont(defaultFont);
		lbl_bajas_art.setForeground(defaultColor);
		bajas.add(lbl_bajas_art);
		
		fld_bajas_art = new JTextField();
		fld_bajas_art.setEditable(false);
		fld_bajas_art.setBounds(250, 120, 300, 40);
		fld_bajas_art.setFont(defaultFont);
		fld_bajas_art.setForeground(defaultColor);
		bajas.add(fld_bajas_art);
		fld_bajas_art.setColumns(150);
		
		JLabel lbl_bajas_desc = new JLabel("Descripción:");
		lbl_bajas_desc.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_bajas_desc.setBounds(40, 175, 150, 16);
		lbl_bajas_desc.setFont(defaultFont);
		lbl_bajas_desc.setForeground(defaultColor);
		bajas.add(lbl_bajas_desc);
		
		txt_bajas_desc = new JTextPane();
		txt_bajas_desc.setEditable(false);
		txt_bajas_desc.setBounds(255, 175, 290, 100);
		txt_bajas_desc.setFont(defaultFont);
		txt_bajas_desc.setForeground(defaultColor);
		bajas.add(txt_bajas_desc);
		
		JLabel lbl_bajas_exist = new JLabel("Existencias:");
		lbl_bajas_exist.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_bajas_exist.setBounds(40, 302, 150, 16);
		lbl_bajas_exist.setFont(defaultFont);
		lbl_bajas_exist.setForeground(defaultColor);
		bajas.add(lbl_bajas_exist);
		
		fld_bajas_exist = new JTextField();
		fld_bajas_exist.setEditable(false);
		fld_bajas_exist.setBounds(250, 290, 150, 40);
		fld_bajas_exist.setFont(defaultFont);
		fld_bajas_exist.setForeground(defaultColor);
		bajas.add(fld_bajas_exist);
		fld_bajas_exist.setColumns(10);
		
		btn_bajas_apply = new JButton("Borrar");
		btn_bajas_apply.setEnabled(false);
		btn_bajas_apply.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				int exist = Integer.parseInt(fld_bajas_exist.getText());
				if(btn_bajas_apply.isEnabled()) {
					if(apputils.deleteItem(fld_bajas_code.getText(), exist)) {
						lbl_bajas_info_messages.setText("Elemento borrado");
						clearFields();
					} else {
						lbl_bajas_info_messages.setText("Todavía hay elementos en existencia");
					}
//					if(exist==0) {
//						infoMessage("Elemento borrado");
//						clearFields();
//					}
				}
			}
		});
		btn_bajas_apply.setBounds(400, 385, 150, 40);
		btn_bajas_apply.setFont(defaultFont);
		btn_bajas_apply.setForeground(defaultColor);
		bajas.add(btn_bajas_apply);
		
		JButton btn_bajas_cancel = new JButton("Cancelar");
		btn_bajas_cancel.setBounds(100, 385, 150, 40);
		btn_bajas_cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clearFields();
			}
		});
		bajas.add(btn_bajas_cancel);

		btn_bajas_verify = new JButton("Buscar");
		btn_bajas_verify.setEnabled(false);
		btn_bajas_verify.setSize(125, 40);
		btn_bajas_verify.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btn_bajas_verify.isEnabled()) {
					Item item = apputils.searchCode(fld_bajas_code.getText());
					if(item!=null) {
						fld_bajas_art.setText(item.getNombre());
						txt_bajas_desc.setText(item.getDescripcion());
						fld_bajas_exist.setText(String.valueOf(item.getCantidad()));
						btn_bajas_apply.setEnabled(true);
					} else {
						lbl_bajas_info_messages.setText("El código no existe en la base de datos");
					}
				}
			}
		});
		btn_bajas_verify.setLocation(425, 65);
		btn_bajas_verify.setFont(defaultFont);
		btn_bajas_verify.setForeground(defaultColor);
		bajas.add(btn_bajas_verify);
		
		psswd_bajas_psswd = new JPasswordField();
		psswd_bajas_psswd.setBounds(250, 10, 150, 40);
		bajas.add(psswd_bajas_psswd);
		
		JLabel lblContrasea_1 = new JLabel("Contraseña:");
		lblContrasea_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblContrasea_1.setBounds(40, 22, 150, 16);
		lblContrasea_1.setFont(defaultFont);
		lblContrasea_1.setForeground(defaultColor);
		bajas.add(lblContrasea_1);
		
		JButton btn_bajas_auth = new JButton("Autenticar");
		btn_bajas_auth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(apputils.comparePasswords(psswd_bajas_psswd.getPassword())) {
					fld_bajas_code.setEditable(true);
					btn_bajas_verify.setEnabled(true);
				} else {
					lbl_bajas_info_messages.setText("Contraseña incorrecta");
				}
			}
		});
		btn_bajas_auth.setBounds(425, 10, 125, 40);
		btn_bajas_auth.setFont(defaultFont);
		btn_bajas_auth.setForeground(defaultColor);
		bajas.add(btn_bajas_auth);

		lbl_bajas_info_messages = new JLabel("");
		lbl_bajas_info_messages.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_bajas_info_messages.setBounds(5, 595, 658, 20);
		lbl_bajas_info_messages.setForeground(messageColor);
		lbl_bajas_info_messages.setFont(messageFont);
		bajas.add(lbl_bajas_info_messages);

		/*
		 * Panel Cambios
		 */
		cambios = new customJPanel();
		tabbedPane.addTab("Cambios", null, cambios, null);
		cambios.setBorder(new EmptyBorder(5,5,5,5));
		cambios.setLayout(null);
		cambios.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				resetFields();
			}
		});
		
		JLabel lbl_cambios_code = new JLabel("Código:");
		lbl_cambios_code.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_cambios_code.setBounds(40, 77, 150, 16);
		lbl_cambios_code.setFont(defaultFont);
		lbl_cambios_code.setForeground(defaultColor);
		cambios.add(lbl_cambios_code);
		
		fld_cambios_code = new JTextField();
		fld_cambios_code.setEditable(false);
		fld_cambios_code.setBounds(250, 60, 150, 40);
		fld_cambios_code.setFont(defaultFont);
		fld_cambios_code.setForeground(defaultColor);
		cambios.add(fld_cambios_code);
		fld_cambios_code.setColumns(150);

		JLabel lbl_cambios_art = new JLabel("Artículo:");
		lbl_cambios_art.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_cambios_art.setBounds(40, 122, 150, 16);
		lbl_cambios_art.setFont(defaultFont);
		lbl_cambios_art.setForeground(defaultColor);
		cambios.add(lbl_cambios_art);
		
		fld_cambios_art = new JTextField();
		fld_cambios_art.setEditable(false);
		fld_cambios_art.setBounds(250, 110, 300, 40);
		fld_cambios_art.setFont(defaultFont);
		fld_cambios_art.setForeground(defaultColor);
		cambios.add(fld_cambios_art);
		fld_cambios_art.setColumns(150);
		
		JLabel lbl_cambios_desc = new JLabel("Descripción:");
		lbl_cambios_desc.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_cambios_desc.setBounds(40, 165, 150, 16);
		lbl_cambios_desc.setFont(defaultFont);
		lbl_cambios_desc.setForeground(defaultColor);
		cambios.add(lbl_cambios_desc);
		
		txt_cambios_desc = new JTextPane();
		txt_cambios_desc.setEditable(false);
		txt_cambios_desc.setBounds(255, 165, 290, 100);
		txt_cambios_desc.setFont(defaultFont);
		txt_cambios_desc.setForeground(defaultColor);
		cambios.add(txt_cambios_desc);
		
		JLabel lbl_cambios_exist = new JLabel("Existencias:");
		lbl_cambios_exist.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_cambios_exist.setBounds(40, 292, 150, 16);
		lbl_cambios_exist.setFont(defaultFont);
		lbl_cambios_exist.setForeground(defaultColor);
		cambios.add(lbl_cambios_exist);
		
		fld_cambios_exist = new JTextField();
		fld_cambios_exist.setBounds(250, 280, 100, 40);
		fld_cambios_exist.setEditable(false);
		fld_cambios_exist.setFont(defaultFont);
		fld_cambios_exist.setForeground(defaultColor);
		cambios.add(fld_cambios_exist);
		
		JLabel lbl_cambios_cant = new JLabel("Agregar:");
		lbl_cambios_cant.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_cambios_cant.setBounds(370, 292, 100, 16);
		lbl_cambios_cant.setFont(defaultFont);
		lbl_cambios_cant.setForeground(defaultColor);
		cambios.add(lbl_cambios_cant);
		
		spnr_cambios_cant = new JSpinner(spinnermodel);
		spnr_cambios_cant.setEnabled(false);
		spnr_cambios_cant.setBounds(490, 280, 70, 40);
		spnr_cambios_cant.setFont(defaultFont);
		spnr_cambios_cant.setForeground(defaultColor);
		cambios.add(spnr_cambios_cant);
		
		btn_cambios_verify = new JButton("Buscar");
		btn_cambios_verify.setEnabled(false);
		btn_cambios_verify.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btn_cambios_verify.isEnabled()) {
					Item item = apputils.searchCode(fld_cambios_code.getText());
					if(item!=null && btn_cambios_verify.isEnabled()) {
						fld_cambios_art.setText(item.getNombre());
						txt_cambios_desc.setText(item.getDescripcion());
						fld_cambios_exist.setText(String.valueOf(item.getCantidad()));
						spnr_cambios_cant.setValue(0);
						fld_cambios_precio.setText(String.valueOf(item.getPrecio()));
						fld_cambios_code.setEditable(false);
						fld_cambios_art.setEditable(true);
						txt_cambios_desc.setEditable(true);
						spnr_cambios_cant.setEnabled(true);
						fld_cambios_precio.setEditable(true);
						btn_cambios_apply.setEnabled(true);
					} else if(item==null) {
						lbl_cambios_info_messages.setText("Elcódigo no existe en la base de datos");
					}
				}
			}
		});
		btn_cambios_verify.setBounds(425, 60, 125, 40);
		btn_cambios_verify.setFont(defaultFont);
		btn_cambios_verify.setForeground(defaultColor);
		cambios.add(btn_cambios_verify);
		
		btn_cambios_apply = new JButton("Actualizar");
		btn_cambios_apply.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btn_cambios_verify.isEnabled()) {
					String precio = NumUtils.makeToDouble(fld_cambios_precio.getText());
					if(precio.equals("0")||precio.contains("-")) {
						lbl_cambios_info_messages.setText("El precio introducido no es válido");
						fld_cambios_precio.setText(precio);
					} else {
						try {
							globalitem = new Item(fld_altas_code.getText(), fld_altas_art.getText(), txt_altas_desc.getText(), String.valueOf(spnr_altas_cant.getValue()), fld_altas_precio.getText());
							apputils.updateItem(globalitem,fld_cambios_exist.getText());
							lbl_cambios_info_messages.setText("Articulo actualizado");
							clearFields();
						} catch (NumberFormatException ex) {
							lbl_altas_info_messages.setText("Precio no válido, se ha ajustado, verifíque que es el adecuado");
							fld_altas_precio.setText(precio);
						} catch (Exception ex1) {
							lbl_altas_info_messages.setText("Ocurrió un error. El elemento no fue guardado");
						}
					}
				}
			}
		});
		btn_cambios_apply.setEnabled(false);
		btn_cambios_apply.setBounds(400, 385, 150, 40);
		btn_cambios_apply.setFont(defaultFont);
		btn_cambios_apply.setForeground(defaultColor);
		cambios.add(btn_cambios_apply);
		
		JButton btn_cambios_cancel = new JButton("Cancelar");
		btn_cambios_cancel.setBounds(100, 385, 150, 40);
		btn_cambios_cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clearFields();
			}
		});
		cambios.add(btn_cambios_cancel);

		fld_cambios_precio = new JTextField();
		fld_cambios_precio.setEditable(false);
		fld_cambios_precio.setBounds(250, 330, 200, 40);
		fld_cambios_precio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lbl_cambios_info_messages.setText("");
			}
		});
		fld_cambios_precio.setFont(defaultFont);
		fld_cambios_precio.setForeground(defaultColor);
		cambios.add(fld_cambios_precio);
		
		JLabel lblPrecio_1 = new JLabel("Precio:");
		lblPrecio_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrecio_1.setBounds(129, 342, 61, 16);
		lblPrecio_1.setFont(defaultFont);
		lblPrecio_1.setForeground(defaultColor);
		cambios.add(lblPrecio_1);
		
		JLabel lbl_cambios_pass = new JLabel("Contraseña:");
		lbl_cambios_pass.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_cambios_pass.setBounds(65, 22, 125, 16);
		lbl_cambios_pass.setFont(defaultFont);
		lbl_cambios_pass.setForeground(defaultColor);
		cambios.add(lbl_cambios_pass);
		
		psswd_cambios_psswd = new JPasswordField();
		psswd_cambios_psswd.setBounds(250, 10, 150, 40);
		psswd_cambios_psswd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lbl_cambios_info_messages.setText("");
			}
		});
		cambios.add(psswd_cambios_psswd);
		
		JButton btn_cambios_auth = new JButton("Autenticar");
		btn_cambios_auth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(apputils.comparePasswords(psswd_cambios_psswd.getPassword())) {
					fld_cambios_code.setEditable(true);
					btn_cambios_verify.setEnabled(true);
				} else{
					lbl_cambios_info_messages.setText("La contraseña es incorrecta");
				}
			}
		});
		btn_cambios_auth.setBounds(425, 10, 125, 40);
		btn_cambios_auth.setFont(defaultFont);
		btn_cambios_auth.setForeground(defaultColor);
		cambios.add(btn_cambios_auth);

		lbl_cambios_info_messages = new JLabel("");
		lbl_cambios_info_messages.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_cambios_info_messages.setBounds(5, 595, 658, 20);
		cambios.add(lbl_cambios_info_messages);

		/*
		 * Panel Configuracion
		 */
		JPanel config = new customJPanel();
		tabbedPane.addTab("Configuración", null, config, null);
		config.setBorder(new EmptyBorder(5,5,5,5));
		config.setLayout(null);
		config.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
//				resetFields();
			}
			@Override
			public void focusGained(FocusEvent e) {
				spnr_config_minimo.setValue(apputils.getMinimo());
				txt_config_desc.setText(apputils.getMessage());
				psswd_config_auth.setText("");
				psswd_config_old.setText("");
				psswd_config_old.setEditable(false);
				psswd_config_pss1.setText("");
				psswd_config_pss1.setEditable(false);
				psswd_config_pss2.setText("");
				psswd_config_pss2.setEditable(false);
				psswd_config_auth.setText("");
				btn_config_save.setEnabled(false);
				btn_config_psswd.setEnabled(false);
				lbl_config_psswd_match.setText("");
			}
		});
		
		JLabel lbl_config_minimo = new JLabel("Minimo de productos:");
		lbl_config_minimo.setBounds(40, 106, 250, 16);
		lbl_config_minimo.setFont(defaultFont);
		lbl_config_minimo.setForeground(defaultColor);
		config.add(lbl_config_minimo);
		
		spnr_config_minimo = new JSpinner(spinnermodel);
		spnr_config_minimo.setEnabled(false);
		spnr_config_minimo.setBounds(40, 134, 250, 40);
		spnr_config_minimo.setFont(defaultFont);
		spnr_config_minimo.setForeground(defaultColor);
		config.add(spnr_config_minimo);
		
		JLabel lbl_config_desc = new JLabel("Mensaje aviso minimo alcanzado:");
		lbl_config_desc.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_config_desc.setBounds(40, 186, 290, 20);
		lbl_config_desc.setFont(defaultFont);
		lbl_config_desc.setForeground(defaultColor);
		config.add(lbl_config_desc);
		
		txt_config_desc = new JTextPane();
		txt_config_desc.setEditable(false);
		txt_config_desc.setBounds(40, 226, 290, 100);
		txt_config_desc.setFont(defaultFont);
		txt_config_desc.setForeground(defaultColor);
		config.add(txt_config_desc);
		
		btn_config_save = new JButton("Guardar");
		btn_config_save.setEnabled(false);
		btn_config_save.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btn_config_save.isEnabled()) {
					apputils.saveMinimo(Integer.parseInt(String.valueOf(spnr_config_minimo.getValue())));
					apputils.saveMessage(txt_config_desc.getText());
					lbl_config_info_messages.setText("Número minimo y mensaje guardados");
				} else {
					lbl_config_info_messages.setText("Un error ha ocurrido, no se guardaron los datos");
				}
			}
		});
		btn_config_save.setBounds(40, 338, 125, 40);
		btn_config_save.setFont(defaultFont);
		btn_config_save.setForeground(defaultColor);
		config.add(btn_config_save);
		
		JLabel lblContrase = new JLabel("Contraseña anterior:");
		lblContrase.setBounds(401, 106, 262, 16);
		lblContrase.setFont(defaultFont);
		lblContrase.setForeground(defaultColor);
		config.add(lblContrase);
		
		psswd_config_old = new JPasswordField();
		psswd_config_old.setEditable(false);
		psswd_config_old.setBounds(401, 134, 200, 40);
		config.add(psswd_config_old);
		
		JLabel lblNewLabel_1 = new JLabel("Contraseña nueva:");
		lblNewLabel_1.setBounds(401, 178, 262, 16);
		lblNewLabel_1.setFont(defaultFont);
		lblNewLabel_1.setForeground(defaultColor);
		config.add(lblNewLabel_1);
		
		psswd_config_pss1 = new JPasswordField();
		psswd_config_pss1.setEditable(false);
		psswd_config_pss1.setBounds(401, 206, 200, 40);
		config.add(psswd_config_pss1);
		
		JLabel lblCorroborarContrasea = new JLabel("Corroborar contraseña:");
		lblCorroborarContrasea.setBounds(401, 258, 262, 16);
		lblCorroborarContrasea.setFont(defaultFont);
		lblCorroborarContrasea.setForeground(defaultColor);
		config.add(lblCorroborarContrasea);
		
		psswd_config_pss2 = new JPasswordField();
		psswd_config_pss2.setEditable(false);
		psswd_config_pss2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				JPasswordField psswd = (JPasswordField)e.getSource();
//				if(psswd.getPassword().equals(psswd_config_pss1.getPassword())){
//				if(psswdCompare(psswd.getPassword(),psswd_config_pss1.getPassword())){
				if(apputils.comparePasswords(psswd.getPassword(), psswd_config_pss1.getPassword())) {
					lbl_config_psswd_match.setText("Las contraseñas coinciden");
				} else {
					lbl_config_psswd_match.setText("Las contraseñas no coinciden");
				}
			}
		});
		psswd_config_pss2.setBounds(400, 286, 200, 40);
		psswd_config_pss2.setFont(defaultFont);
		psswd_config_pss2.setForeground(defaultColor);
		config.add(psswd_config_pss2);
		
		btn_config_psswd = new JButton("Cambiar contraseña");
		btn_config_psswd.setEnabled(false);
		btn_config_psswd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(btn_config_psswd.isEnabled()) {
					if(apputils.comparePasswords(psswd_config_auth.getPassword())) {
						if(apputils.comparePasswords(psswd_config_pss1.getPassword(),psswd_config_pss2.getPassword()))
							apputils.updatePassword(psswd_config_pss1.getPassword());
					}
				}
			}
		});
		btn_config_psswd.setBounds(400, 360, 200, 40);
		btn_config_psswd.setFont(defaultFont);
		btn_config_psswd.setForeground(defaultColor);
		config.add(btn_config_psswd);
		
		JButton btn_config_cancel = new JButton("Cancelar");
		btn_config_cancel.setBounds(275, 450, 150, 40);
		btn_config_cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				resetConfig();
			}
		});
		config.add(btn_config_cancel);

		lbl_config_psswd_match = new JLabel("");
		lbl_config_psswd_match.setBounds(400, 330, 200, 20);
		config.add(lbl_config_psswd_match);
		
		JLabel lblContrasea_3 = new JLabel("Contraseña:");
		lblContrasea_3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblContrasea_3.setBounds(73, 22, 150, 16);
		lblContrasea_3.setFont(defaultFont);
		lblContrasea_3.setForeground(defaultColor);
		config.add(lblContrasea_3);
		
		JButton btn_config_auth = new JButton("Autenticar");
		btn_config_auth.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(apputils.comparePasswords(psswd_config_auth.getPassword())) {
					psswd_config_old.setEditable(true);
					psswd_config_pss1.setEditable(true);
					psswd_config_pss2.setEditable(true);
					spnr_config_minimo.setEnabled(true);
					txt_config_desc.setEditable(true);
					btn_config_save.setEnabled(true);
					btn_config_psswd.setEnabled(true);
				}
			}
		});
		btn_config_auth.setBounds(425, 10, 125, 40);
		btn_config_auth.setFont(defaultFont);
		btn_config_auth.setForeground(defaultColor);
		config.add(btn_config_auth);
		
		psswd_config_auth = new JPasswordField();
		psswd_config_auth.setBounds(250, 10, 150, 40);
		config.add(psswd_config_auth);
		
		lbl_config_info_messages = new JLabel("");
		lbl_config_info_messages.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_config_info_messages.setBounds(5, 595, 658, 20);
		lbl_config_info_messages.setFont(messageFont);
		lbl_config_info_messages.setForeground(messageColor);
		config.add(lbl_config_info_messages);
	}
	
	
	/*
	 * Private methods
	 */
	private void nuevaVenta() {
		fld_egresos_code.setText("");
		fld_egresos_art.setText("");
		txt_egresos_desc.setText("");
		fld_egresos_exist.setText("");
		fld_egresos_precio.setText("");
		spnr_egresos_cant.setValue(0);	
//		resetEgresos();
	}
	
	private void resetFields() {
		psswd_altas_psswd.setText("");
		fld_altas_code.setText("");
		fld_altas_art.setText("");
		fld_altas_art.setEditable(false);
		txt_altas_desc.setText("");
		txt_altas_desc.setEditable(false);
		spnr_altas_cant.setValue(0);
		spnr_altas_cant.setEnabled(false);
		fld_altas_precio.setText("");
		fld_altas_precio.setEditable(false);
		btn_altas_verify.setEnabled(false);
		btn_altas_apply.setEnabled(false);
		psswd_bajas_psswd.setText("");
		fld_bajas_code.setText("");
		fld_bajas_code.setEditable(false);
		btn_bajas_verify.setEnabled(false);
		fld_bajas_art.setText("");
		txt_bajas_desc.setText("");
		fld_bajas_exist.setText("");
		btn_bajas_apply.setEnabled(false);
		psswd_cambios_psswd.setText("");
		fld_cambios_code.setText("");
		fld_cambios_code.setEditable(false);
		btn_cambios_verify.setEnabled(false);
		fld_cambios_art.setText("");
		fld_cambios_art.setEditable(false);
		txt_cambios_desc.setText("");
		txt_cambios_desc.setEditable(false);
		fld_cambios_exist.setText("");
		fld_cambios_exist.setEditable(false);
		fld_cambios_precio.setText("");
		fld_cambios_precio.setEditable(false);
		spnr_cambios_cant.setValue(0);
		spnr_cambios_cant.setEnabled(false);
		btn_cambios_apply.setEnabled(false);
//		lbl_altas_info_messages.setText("");
//		lbl_bajas_info_messages.setText("");
//		lbl_cambios_info_messages.setText("");
//		lbl_egresos_info_messages.setText("");
	}
	
	private void resetAltas( ) {
		psswd_altas_psswd.setText("");
		fld_altas_code.setText("");
		fld_altas_art.setText("");
		fld_altas_art.setEditable(false);
		txt_altas_desc.setText("");
		txt_altas_desc.setEditable(false);
		spnr_altas_cant.setValue(0);
		spnr_altas_cant.setEnabled(false);
		fld_altas_precio.setText("");
		fld_altas_precio.setEditable(false);
		btn_altas_verify.setEnabled(false);
		btn_altas_apply.setEnabled(false);
		lbl_altas_info_messages.setText("");
	}
	
	private void clearFields() {
		fld_altas_code.setText("");
		fld_altas_code.setEditable(true);
		fld_altas_art.setText("");
		fld_altas_art.setEditable(false);
		txt_altas_desc.setText("");
		txt_altas_desc.setEditable(false);
		spnr_altas_cant.setValue(0);
		spnr_altas_cant.setEnabled(false);
		fld_altas_precio.setText("");
		fld_altas_precio.setEditable(false);
//		btn_altas_verify.setEnabled(false);
		btn_altas_apply.setEnabled(false);
		fld_bajas_code.setText("");
		fld_bajas_code.setEditable(false);
//		btn_bajas_verify.setEnabled(false);
		fld_bajas_art.setText("");
		txt_bajas_desc.setText("");
		fld_bajas_exist.setText("");
		btn_bajas_apply.setEnabled(false);
		fld_cambios_code.setText("");
		fld_cambios_code.setEditable(true);
//		btn_cambios_verify.setEnabled(false);
		fld_cambios_art.setText("");
		fld_cambios_art.setEditable(false);
		txt_cambios_desc.setText("");
		txt_cambios_desc.setEditable(false);
		fld_cambios_exist.setText("");
		fld_cambios_exist.setEditable(false);
		fld_cambios_precio.setText("");
		fld_cambios_precio.setEditable(false);
		spnr_cambios_cant.setValue(0);
		spnr_cambios_cant.setEnabled(false);
		btn_cambios_apply.setEnabled(false);
		lbl_altas_info_messages.setText("");
		lbl_bajas_info_messages.setText("");
		lbl_cambios_info_messages.setText("");
		lbl_egresos_info_messages.setText("");
	}
	
	private void resetBajas() {
		psswd_bajas_psswd.setText("");
		fld_bajas_code.setText("");
		fld_bajas_code.setEditable(false);
		btn_bajas_verify.setEnabled(false);
		fld_bajas_art.setText("");
		txt_bajas_desc.setText("");
		fld_bajas_exist.setText("");
		btn_bajas_apply.setEnabled(false);
		lbl_bajas_info_messages.setText("");
	}
	
	private void resetCambios() {
		psswd_cambios_psswd.setText("");
		fld_cambios_code.setText("");
		fld_cambios_code.setEditable(false);
		btn_cambios_verify.setEnabled(false);
		fld_cambios_art.setText("");
		fld_cambios_art.setEditable(false);
		txt_cambios_desc.setText("");
		txt_cambios_desc.setEditable(false);
		fld_cambios_exist.setText("");
		fld_cambios_exist.setEditable(false);
		fld_cambios_precio.setText("");
		fld_cambios_precio.setEditable(false);
		spnr_cambios_cant.setValue(0);
		spnr_cambios_cant.setEnabled(false);
		btn_cambios_apply.setEnabled(false);
		lbl_cambios_info_messages.setText("");
	}
	
	private void resetConfig() {
		spnr_config_minimo.setValue(apputils.getMinimo());
		txt_config_desc.setText(apputils.getMessage());
		spnr_config_minimo.setEnabled(false);
		psswd_config_auth.setText("");
		psswd_config_old.setText("");
		psswd_config_old.setEditable(false);
		psswd_config_pss1.setText("");
		psswd_config_pss1.setEditable(false);
		psswd_config_pss2.setText("");
		psswd_config_pss2.setEditable(false);
		psswd_config_auth.setText("");
		btn_config_save.setEnabled(false);
		btn_config_psswd.setEnabled(false);
		lbl_config_psswd_match.setText("");
		lbl_config_info_messages.setText("");
//		resetFields();
	}
	
	private void resetEgresos () {
		fld_egresos_code.setText("");
		fld_egresos_art.setText("");
		txt_egresos_desc.setText("");
		fld_egresos_exist.setText("");
		spnr_egresos_cant.setValue(0);
		fld_egresos_precio.setText("");
		txtpnI_egresos_items.setText("<html><body width=\"490px\"><table width=\"100%\"></table></body></html>");
		lbl_egresos_total.setText("");
		btn_egresos_apply.setEnabled(false);
		btn_egresos_pdf.setEnabled(false);
		total = 0.0;
		lbl_egresos_info_messages.setText("");
	}
	
	public void infoMessage(String message) {
		dialog = new InfoMessages(message);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

}