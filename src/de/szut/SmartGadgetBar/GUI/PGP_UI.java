package de.szut.SmartGadgetBar.GUI;

import javax.swing.JButton;
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
		setLayout(null);
		
		JButton btnEncrypt = new JButton("Encrypt");
		btnEncrypt.setBounds(10, 45, 89, 23);
		btnEncrypt.addActionListener(e -> {
			//pgp.
		});
		add(btnEncrypt);
		
		JButton btnFile = new JButton("Open File");
		btnFile.setBounds(10, 11, 89, 23);
		add(btnFile);
		
		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.setBounds(181, 45, 89, 23);
		add(btnDecrypt);
		
		JButton btnPublicKey = new JButton("Public Key");
		btnPublicKey.setBounds(181, 11, 89, 23);
		add(btnPublicKey);
		
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
