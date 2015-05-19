package de.szut.SmartGadgetBar.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PopoutFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3257957647667454108L;
	private int pX, pY;
	public PopoutFrame(AbstractWidgetPanel abstractWidgetPanel) {
		// TODO Auto-generated constructor stub
		System.out.println("test");
		setContentPane(abstractWidgetPanel);
		setBounds(10,10,100,100);
		System.out.println(abstractWidgetPanel.getSize().getWidth());
		System.out.println(abstractWidgetPanel.getSize().getHeight());
		setSize((int)abstractWidgetPanel.getSize().getWidth(), (int)abstractWidgetPanel.getSize().getHeight());
		setUndecorated(true);
		setVisible(true);
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				pX = me.getX();
				pY = me.getY();
			}
		});
		addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent me) {
				setLocation(getParent().getParent().getParent().getLocation().x + me.getX() - pX, getLocation().y
						+ me.getY() - pY);
			}
		});
	}

}
