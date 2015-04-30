package de.szut.SmartGadgetBar.GUI;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.szut.SmartGadgetBar.Widgets.ZIP.ZIP;


 
public class ZIP_UI extends AbstractWidgetPanel{

	private ZIP widget;
	private JFileChooser fileChoosertoZip;
	private JFileChooser fileChoosertoFile;
	public ZIP_UI(ZIP parent) {
		super(parent);
		widget = parent;
		initializePanel();
		// TODO Auto-generated constructor stub
	}

	
	@Override
	void initializePanel() {
		
		setSize(280,80);
		setLayout(null);
		setBackground(Color.BLUE);

		JButton btnZip = new JButton("Zip");
		btnZip.setBounds(10, 45, 89, 23);
		btnZip.addActionListener(e -> {
			File[] choosen = fileChoosertoZip.getSelectedFiles();
			
			widget.comprimate(choosen, "out.zip");
			
			
			
		});
		add(btnZip);

		JButton btnInFile = new JButton("Select File");
		btnInFile.setBounds(10, 11, 89, 23);
		btnInFile.addActionListener(e -> {
			fileChoosertoZip = new JFileChooser();
			fileChoosertoZip.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			fileChoosertoZip.setMultiSelectionEnabled(true);
			fileChoosertoZip.setDialogTitle("Please choose a file");
			int option = fileChoosertoZip.showOpenDialog(null);
			
		});
		add(btnInFile);

		JButton btnDecrypt = new JButton("UnZip");
		btnDecrypt.setBounds(181, 45, 89, 23);
		btnDecrypt.addActionListener(e -> {
			widget.decomprimate(fileChoosertoZip.getSelectedFile().getAbsolutePath());
		});
		add(btnDecrypt);

		

		JPopupMenu popup = new JPopupMenu();
		add(popup);
		JMenuItem subMenu = new JMenuItem("Options");
		subMenu.addActionListener(e -> {
			System.out.println("Insert Options here");
		});
		JMenuItem closeWidget = new JMenuItem("Delete");
		closeWidget.addActionListener(e -> {
			this.getParent().remove(this);
		});
		popup.add(closeWidget);
		popup.add(subMenu);
		setComponentPopupMenu(popup);
		setVisible(true);
	}

}
