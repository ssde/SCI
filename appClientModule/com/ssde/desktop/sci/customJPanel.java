package com.ssde.desktop.sci;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class customJPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4083958702989733656L;
	private Image bg; 
	
	public customJPanel() {
		try {
			bg = ImageIO.read(new File("bg.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg, 0, 0, null);
	}
}
