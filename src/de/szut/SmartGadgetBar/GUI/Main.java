package de.szut.SmartGadgetBar.GUI;

import java.awt.EventQueue;

public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PropLoader prop = new PropLoader();
					MainFrame frame = new MainFrame(prop);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
