package de.szut.SmartGadgetBar.GUI;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.szut.SmartGadgetBar.PGP.PGP;

public class PGP_UI extends JPanel{
	private JFileChooser fileChooser;
	private JFileChooser keyChooser;
	public PGP_UI(PGP pgp){
		setLayout(null);
		
		JButton btnEncrypt = new JButton("Encrypt");
		btnEncrypt.setBounds(10, 45, 89, 23);
		btnEncrypt.addActionListener(e -> {
			pgp.encryptFile(fileChooser.getSelectedFile().getAbsolutePath(), keyChooser.getSelectedFile().getAbsolutePath());
		});
		add(btnEncrypt);
		
		JButton btnFile = new JButton("Open File");
		btnFile.setBounds(10, 11, 89, 23);
		btnFile.addActionListener(e -> {
			fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Please choose a file");
			//fileChooser.setCurrentDirectory(new File("db"));
			int option = fileChooser.showOpenDialog(null);
			if (option == JFileChooser.APPROVE_OPTION) {	
			}
		});
		add(btnFile);
		
		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.setBounds(181, 45, 89, 23);
		btnDecrypt.addActionListener(e -> {
			pgp.decryptFile(fileChooser.getSelectedFile().getAbsolutePath(), "keys/private.skr");
		});
		add(btnDecrypt);
		
		JButton btnPublicKey = new JButton("Public Key");
		btnPublicKey.setBounds(181, 11, 89, 23);
		btnPublicKey.addActionListener(e -> {
			keyChooser = new JFileChooser();
			keyChooser.setDialogTitle("Please choose a Key file");
			keyChooser.setCurrentDirectory(new File("keys"));
			keyChooser.setFileFilter(new FileNameExtensionFilter(
					"Key File", "skr", "asc"));
			int option = keyChooser.showOpenDialog(null);
			if (option == JFileChooser.APPROVE_OPTION) {	
			}
		});
		add(btnPublicKey);
		
		JPopupMenu popup = new JPopupMenu();
		add(popup);
		JMenuItem subMenu = new JMenuItem("Options");
		subMenu.addActionListener(e-> {
			System.out.println("Insert Options here");
		});
		JMenuItem closeWidget = new JMenuItem("Delete");
		closeWidget.addActionListener(e-> {
			this.getParent().remove(this);
		});
		popup.add(closeWidget);
		popup.add(subMenu);
		setComponentPopupMenu(popup);	
		
		
	}
	
}
