package com.ssde.desktop.sci;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class InfoMessages extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lbl_message;
	private final String html1 = "<html><body style='width: 250px; text-align: center;'>";
	private final String html2 = "</body></html>";

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			InfoMessages dialog = new InfoMessages();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public InfoMessages(String message) {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		lbl_message = new JLabel(html1+message+html2);
		lbl_message.setBounds(10, 10, 430, 280);
		lbl_message.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(lbl_message);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton cancelButton = new JButton("Cerrar");
		cancelButton.setActionCommand("Close");
		cancelButton.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
//		buttonPane.add(cancelButton);

	}

}
