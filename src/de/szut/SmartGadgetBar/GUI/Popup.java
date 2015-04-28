package de.szut.SmartGadgetBar.GUI;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class Popup extends JPopupMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7579506352881070567L;

	public Popup(){
		JMenuItem klienciMenuItem = new JMenuItem("Close");
		klienciMenuItem.addActionListener(e-> {
			
		});
		add(klienciMenuItem);
	}
}
