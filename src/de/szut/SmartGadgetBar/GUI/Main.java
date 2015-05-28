package de.szut.SmartGadgetBar.GUI;

import java.awt.EventQueue;

/**
 * Die Main des Programms
 * 
 * @author Jan-Philipp Rogge
 *
 */
public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
