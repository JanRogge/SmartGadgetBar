package de.szut.SmartGadgetBar.GUI;

import java.awt.GridLayout;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class PGP_UI extends JPanel{
	public PGP_UI(BackgroundPanel panel, int number){
		if(number == 0){
			setBounds(10, BackgroundPanel.GAP , 280, 70);
		} else{
			setBounds(10, (int) (panel.getComponent(number-1).getBounds().getY() + BackgroundPanel.GAP + panel.getComponent(number-1).getSize().getHeight()), 280, 70);
		}		
		setLayout(new GridLayout(0, 1, 0, 0));
		JPopupMenu popup = new JPopupMenu();
		add(popup);
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
		setComponentPopupMenu(popup);	
	}
	
}
