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
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new GridLayout(0, 1, 0, 0));
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				pX = me.getX();
				pY = me.getY();
			}
		});
		addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent me) {
				setLocation(getLocation().x + me.getX() - pX, getLocation().y
						+ me.getY() - pY);
			}
		});
	}

}
