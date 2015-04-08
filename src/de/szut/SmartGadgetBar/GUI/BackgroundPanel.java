package de.szut.SmartGadgetBar.GUI;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class BackgroundPanel extends JPanel {
	int pX, pY;

	/**
	 * Create the panel.
	 */
	public BackgroundPanel() {
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new GridLayout(0, 1, 0, 0));
		new ClockView(this);
		new ClockView(this);
	}

}
