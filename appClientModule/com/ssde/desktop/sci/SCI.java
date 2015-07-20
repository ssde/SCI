package com.ssde.desktop.sci;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.ssde.desktop.sci.model.Item;

@SuppressWarnings("serial")
public class SCI extends JFrame{
	
	//Private fields
	private JPanel 		contentPane;
	private JTabbedPane tabbedPane;
	private JTextField 	fld_egresos_code;
	private JTextField 	fld_egresos_art;
	private JTextPane 	txt_egresos_desc;
	private JTextField 	fld_egresos_exist;
	private JSpinner 	spnr_egresos_cant;
	private JButton 	btn_egresos_apply;
	private JTextField 	fld_altas_code;
	private JTextField 	fld_altas_art;
	private JTextPane 	txt_altas_desc;
	private JSpinner 	spnr_altas_cant;
	private JButton 	btn_altas_apply;
	private JTextField 	fld_bajas_code;
	private JTextField 	fld_bajas_art;
	private JTextPane 	txt_bajas_desc;
	private JTextField 	fld_bajas_exist;
	private JButton 	btn_bajas_apply;
	private JTextField 	fld_cambios_code;
	private JTextField 	fld_cambios_art;
	private JTextPane 	txt_cambios_desc;
	private JSpinner 	spnr_cambios_cant;
	private JButton 	btn_cambios_apply;
	private JSpinner 	spnr_config_minimo;
	private JTextPane 	txt_config_desc;
	private AppUtils 	apputils;
	private Item 		item;
	
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
		apputils = new AppUtils();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				System.out.println(tabbedPane.getSelectedIndex());
				if(tabbedPane.getSelectedIndex() == 4) {					
					spnr_config_minimo.setValue(apputils.getMinimo());
					
				}
			}
		});
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel egresos = new JPanel();
		tabbedPane.addTab("Egresos", null, egresos, null);
		egresos.setBorder(new EmptyBorder(5,5,5,5));
		egresos.setLayout(null);
		
		JLabel lbl_egresos_code = new JLabel("Código:");
		lbl_egresos_code.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_egresos_code.setBounds(40, 22, 150, 16);
		egresos.add(lbl_egresos_code);
		
		fld_egresos_code = new JTextField();
		fld_egresos_code.setBounds(250, 10, 150, 40);
		egresos.add(fld_egresos_code);
		fld_egresos_code.setColumns(150);
		
		JLabel lbl_egresos_art = new JLabel("Artículo:");
		lbl_egresos_art.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_egresos_art.setBounds(40, 88, 150, 16);
		egresos.add(lbl_egresos_art);
		
		fld_egresos_art = new JTextField();
		fld_egresos_art.setEditable(false);
		fld_egresos_art.setBounds(250, 76, 300, 40);
		egresos.add(fld_egresos_art);
		fld_egresos_art.setColumns(150);
		
		JLabel lbl_egresos_desc = new JLabel("Descripción:");
		lbl_egresos_desc.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_egresos_desc.setBounds(40, 128, 150, 16);
		egresos.add(lbl_egresos_desc);
		
		txt_egresos_desc = new JTextPane();
		txt_egresos_desc.setEditable(false);
		txt_egresos_desc.setBounds(255, 128, 290, 100);
		egresos.add(txt_egresos_desc);
		
		JLabel lbl_egresos_exist = new JLabel("Existencias:");
		lbl_egresos_exist.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_egresos_exist.setBounds(40, 255, 150, 16);
		egresos.add(lbl_egresos_exist);
		
		fld_egresos_exist = new JTextField();
		fld_egresos_exist.setEditable(false);
		fld_egresos_exist.setBounds(250, 243, 150, 40);
		egresos.add(fld_egresos_exist);
		fld_egresos_exist.setColumns(10);
		
		JLabel lbl_egresos_cant = new JLabel("Cantidad a vender:");
		lbl_egresos_cant.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_egresos_cant.setBounds(40, 307, 150, 16);
		egresos.add(lbl_egresos_cant);
		
		spnr_egresos_cant = new JSpinner();
		spnr_egresos_cant.setBounds(250, 295, 100, 40);
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
					btn_egresos_apply.setEnabled(true);
				}
			}
		});
		btn_egresos_buscar.setBounds(425, 11, 125, 40);
		egresos.add(btn_egresos_buscar);
		
		btn_egresos_apply = new JButton("Aplicar");
		btn_egresos_apply.setEnabled(false);
		btn_egresos_apply.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				apputils.applySalida(fld_egresos_code.getText(), Integer.parseInt(fld_egresos_exist.getText()), Integer.parseInt(String.valueOf(spnr_egresos_cant.getValue())));
			}
		});
		btn_egresos_apply.setBounds(513, 376, 150, 40);
		egresos.add(btn_egresos_apply);
		
		JPanel altas = new JPanel();
		tabbedPane.addTab("Altas", null, altas, null);
		altas.setBorder(new EmptyBorder(5,5,5,5));
		altas.setLayout(null);
		
		JLabel lbl_altas_code = new JLabel("Código:");
		lbl_altas_code.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_altas_code.setBounds(40, 22, 150, 16);
		altas.add(lbl_altas_code);
		
		fld_altas_code = new JTextField();
		fld_altas_code.setBounds(250, 10, 150, 40);
		altas.add(fld_altas_code);
		fld_altas_code.setColumns(150);

		JLabel lbl_altas_art = new JLabel("Artículo:");
		lbl_altas_art.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_altas_art.setBounds(40, 88, 150, 16);
		altas.add(lbl_altas_art);
		
		fld_altas_art = new JTextField();
		fld_altas_art.setEditable(false);
		fld_altas_art.setBounds(250, 76, 300, 40);
		altas.add(fld_altas_art);
		fld_altas_art.setColumns(150);
		
		JLabel lbl_altas_desc = new JLabel("Descripción:");
		lbl_altas_desc.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_altas_desc.setBounds(40, 128, 150, 16);
		altas.add(lbl_altas_desc);
		
		txt_altas_desc = new JTextPane();
		txt_altas_desc.setEditable(false);
		txt_altas_desc.setBounds(255, 128, 290, 100);
		altas.add(txt_altas_desc);
		
		JLabel lbl_altas_exist = new JLabel("Existencias:");
		lbl_altas_exist.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_altas_exist.setBounds(40, 255, 150, 16);
		altas.add(lbl_altas_exist);
		
		spnr_altas_cant = new JSpinner();
		spnr_altas_cant.setEnabled(false);
		spnr_altas_cant.setBounds(250, 243, 100, 40);
		altas.add(spnr_altas_cant);
		
		JButton btn_altas_verify = new JButton("Verificar");
		btn_altas_verify.setBounds(425, 11, 125, 40);
		btn_altas_verify.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(apputils.verifyCode(fld_altas_code.getText())) {
					fld_altas_art.setEditable(true);
					txt_altas_desc.setEditable(true);
					spnr_altas_cant.setEnabled(true);
					btn_altas_apply.setEnabled(true);
				}
			}
		});
		altas.add(btn_altas_verify);
		btn_altas_verify.setBounds(425, 11, 125, 40);
		
		btn_altas_apply = new JButton("Guardar");
		btn_altas_apply.setEnabled(false);
		btn_altas_apply.setBounds(513, 376, 150, 40);
		btn_altas_apply.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				item = new Item(fld_altas_code.getText(), fld_altas_art.getText(), txt_altas_desc.getText(), (String)spnr_altas_cant.getValue());
				apputils.saveItem(item);
			}
		});
		altas.add(btn_altas_apply);
		
		JPanel bajas = new JPanel();
		tabbedPane.addTab("Bajas", null, bajas, null);
		bajas.setBorder(new EmptyBorder(5,5,5,5));
		bajas.setLayout(null);
		
		JLabel lbl_bajas_code = new JLabel("Código:");
		lbl_bajas_code.setHorizontalAlignment(SwingConstants.RIGHT);
		
		lbl_bajas_code.setBounds(40, 22, 150, 16);
		bajas.add(lbl_bajas_code);
		
		fld_bajas_code = new JTextField();
		fld_bajas_code.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(fld_bajas_code.getText().isEmpty()) {
					btn_bajas_apply.setEnabled(false);
				}
			}
		});
		fld_bajas_code.setBounds(250, 10, 150, 40);
		bajas.add(fld_bajas_code);
		fld_bajas_code.setColumns(150);
				
		JLabel lbl_bajas_art = new JLabel("Artículo:");
		lbl_bajas_art.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_bajas_art.setBounds(40, 88, 150, 16);
		bajas.add(lbl_bajas_art);
		
		fld_bajas_art = new JTextField();
		fld_bajas_art.setEditable(false);
		fld_bajas_art.setBounds(250, 76, 300, 40);
		bajas.add(fld_bajas_art);
		fld_bajas_art.setColumns(150);
		
		JLabel lbl_bajas_desc = new JLabel("Descripción:");
		lbl_bajas_desc.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_bajas_desc.setBounds(40, 128, 150, 16);
		bajas.add(lbl_bajas_desc);
		
		txt_bajas_desc = new JTextPane();
		txt_bajas_desc.setEditable(false);
		txt_bajas_desc.setBounds(255, 128, 290, 100);
		bajas.add(txt_bajas_desc);
		
		JLabel lbl_bajas_exist = new JLabel("Existencias:");
		lbl_bajas_exist.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_bajas_exist.setBounds(40, 255, 150, 16);
		bajas.add(lbl_bajas_exist);
		
		fld_bajas_exist = new JTextField();
		fld_bajas_exist.setEditable(false);
		fld_bajas_exist.setBounds(250, 243, 150, 40);
		bajas.add(fld_bajas_exist);
		fld_bajas_exist.setColumns(10);
		
		btn_bajas_apply = new JButton("Borrar");
		btn_bajas_apply.setEnabled(false);
		btn_bajas_apply.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				apputils.deleteItem(fld_bajas_code.getText());
			}
		});
		btn_bajas_apply.setBounds(513, 376, 150, 40);
		bajas.add(btn_bajas_apply);
		
		JButton btn_bajas_verify = new JButton("Buscar");
		btn_bajas_verify.setSize(125, 40);
		btn_bajas_verify.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				Item item = apputils.searchCode(fld_bajas_code.getText());
				if(item!=null) {
					fld_bajas_art.setText(item.getNombre());
					txt_bajas_desc.setText(item.getDescripcion());
					fld_bajas_exist.setText(String.valueOf(item.getCantidad()));
					btn_bajas_apply.setEnabled(true);
				}
			}
		});
		btn_bajas_verify.setLocation(425, 11);
		bajas.add(btn_bajas_verify);
		
		JPanel cambios = new JPanel();
		tabbedPane.addTab("Cambios", null, cambios, null);
		cambios.setBorder(new EmptyBorder(5,5,5,5));
		cambios.setLayout(null);
		
		JLabel lbl_cambios_code = new JLabel("Código:");
		lbl_cambios_code.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_cambios_code.setBounds(40, 22, 150, 16);
		cambios.add(lbl_cambios_code);
		
		fld_cambios_code = new JTextField();
		fld_cambios_code.setBounds(250, 10, 150, 40);
		cambios.add(fld_cambios_code);
		fld_cambios_code.setColumns(150);

		JLabel lbl_cambios_art = new JLabel("Artículo:");
		lbl_cambios_art.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_cambios_art.setBounds(40, 88, 150, 16);
		cambios.add(lbl_cambios_art);
		
		fld_cambios_art = new JTextField();
		fld_cambios_art.setBounds(250, 76, 300, 40);
		cambios.add(fld_cambios_art);
		fld_cambios_art.setColumns(150);
		
		JLabel lbl_cambios_desc = new JLabel("Descripción:");
		lbl_cambios_desc.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_cambios_desc.setBounds(40, 128, 150, 16);
		cambios.add(lbl_cambios_desc);
		
		txt_cambios_desc = new JTextPane();
		txt_cambios_desc.setBounds(255, 128, 290, 100);
		cambios.add(txt_cambios_desc);
		
		JLabel lbl_cambios_exist = new JLabel("Existencias:");
		lbl_cambios_exist.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_cambios_exist.setBounds(40, 255, 150, 16);
		cambios.add(lbl_cambios_exist);
		
		spnr_cambios_cant = new JSpinner();
		spnr_cambios_cant.setBounds(250, 243, 100, 40);
		cambios.add(spnr_cambios_cant);
		
		JButton btn_cambios_verify = new JButton("Buscar");
		btn_cambios_verify.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Item item = apputils.searchCode(fld_cambios_code.getText());
				if(item!=null) {
					fld_cambios_art.setText(item.getNombre());
					txt_cambios_desc.setText(item.getDescripcion());
					spnr_cambios_cant.setValue(String.valueOf(item.getCantidad()));
					btn_cambios_apply.setEnabled(true);
				}
			}
		});
		btn_cambios_verify.setBounds(425, 11, 125, 40);
		cambios.add(btn_cambios_verify);
		
		btn_cambios_apply = new JButton("Actualizar");
		btn_cambios_apply.setEnabled(false);
		btn_cambios_apply.setBounds(513, 376, 150, 40);
		cambios.add(btn_cambios_apply);
		
		JPanel config = new JPanel();
		tabbedPane.addTab("Configuración", null, config, null);
		config.setBorder(new EmptyBorder(5,5,5,5));
		config.setLayout(null);
		
		JLabel lbl_config_minimo = new JLabel("Minimo de productos:");
		lbl_config_minimo.setBounds(40, 28, 150, 16);
		config.add(lbl_config_minimo);
		
		spnr_config_minimo = new JSpinner();
		spnr_config_minimo.setBounds(40, 56, 250, 40);
		config.add(spnr_config_minimo);
		
		JLabel lbl_config_desc = new JLabel("Mensaje aviso minimo alcanzado:");
		lbl_config_desc.setHorizontalAlignment(SwingConstants.LEFT);
		lbl_config_desc.setBounds(40, 128, 400, 20);
		config.add(lbl_config_desc);
		
		txt_config_desc = new JTextPane();
		txt_config_desc.setBounds(40, 160, 290, 100);
		config.add(txt_config_desc);
		
		JButton btn_config_save = new JButton("Guardar");
		btn_config_save.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				apputils.saveMinimo(Integer.parseInt(String.valueOf(spnr_config_minimo.getValue())));
				apputils.saveMessage(txt_config_desc.getText());
			}
		});
		btn_config_save.setBounds(538, 376, 125, 40);
		config.add(btn_config_save);
		
		JButton btn_init_db = new JButton("Inicializar DB");
		btn_init_db.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				apputils.initializeDB();
			}
		});
		btn_init_db.setBounds(401, 376, 125, 40);
		config.add(btn_init_db);
	}
	
}