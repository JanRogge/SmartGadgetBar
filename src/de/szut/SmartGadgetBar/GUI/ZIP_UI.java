package de.szut.SmartGadgetBar.GUI;

import java.awt.Color;
import java.awt.Rectangle;
import java.io.File;
import java.time.temporal.JulianFields;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.szut.SmartGadgetBar.Model.WidgetInterface;
import de.szut.SmartGadgetBar.Widgets.ZIP.ZIP;

public class ZIP_UI extends AbstractWidgetPanel{

	private ZIP widget;
	private JFileChooser fileChoosertoZip;
	private JFileChooser fileChoosertoFile;
	public ZIP_UI(ZIP parent) {
		super(parent);
		widget = parent;
		initializePanel();
		System.out.println("ZIPPERINION");
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
			widget.comprimate(fileChoosertoZip.getSelectedFiles(),
					fileChoosertoFile.getSelectedFile().getAbsolutePath());
		});
		add(btnZip);

		JButton btnFile = new JButton("Select File");
		btnFile.setBounds(10, 11, 89, 23);
		btnFile.addActionListener(e -> {
			fileChoosertoZip = new JFileChooser();
			fileChoosertoZip.setDialogTitle("Please choose a file");
			int option = fileChoosertoZip.showOpenDialog(null);
			
		});
		add(btnFile);

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
