package de.szut.SmartGadgetBar.GUI;

import java.awt.Color;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;

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

		

	}


	@Override
	public void pushFiles(File[] files) {
		// TODO Auto-generated method stub
		
	}

}
