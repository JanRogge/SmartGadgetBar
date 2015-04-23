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
	public TestPanel(BackgroundPanel panel, int number){
		if(number == 0){
			setBounds(10, BackgroundPanel.GAP , 280, 70);
		} else{
			setBounds(10, (int) (panel.getComponent(number-1).getBounds().getY() + BackgroundPanel.GAP + panel.getComponent(number-1).getSize().getHeight()), 280, 70);
		}		
		setLayout(new GridLayout(0, 1, 0, 0));
		textField = new JTextField();
		add(textField);
		textField.setColumns(10);
		JPopupMenu popup = new JPopupMenu();
		textField.add(popup);
		JMenuItem subMenu = new JMenuItem("Options");
		subMenu.addActionListener(e-> {
			System.out.println("Insert Options here");
		});
		JMenuItem closeWidget = new JMenuItem("Delete");
		closeWidget.addActionListener(e-> {
			panel.remove(this);
			panel.rebuild();
		});
		popup.add(closeWidget);
		popup.add(subMenu);
		textField.setComponentPopupMenu(popup);
	}
}
	
