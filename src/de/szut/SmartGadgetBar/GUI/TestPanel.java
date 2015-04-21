package de.szut.SmartGadgetBar.GUI;

import java.awt.GridLayout;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

public class TestPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1133994788622482486L;
	private JTextField textField;
	public TestPanel(){
	setBounds(10, 155, 280, 70);
	setLayout(new GridLayout(0, 1, 0, 0));
	textField = new JTextField();
	add(textField);
	textField.setColumns(10);
	JPopupMenu popup = new JPopupMenu();
	textField.add(popup);
	JMenuItem subMenu = new JMenuItem("Options");
	popup.add(subMenu);
	textField.setComponentPopupMenu(popup);
	}
}
	
