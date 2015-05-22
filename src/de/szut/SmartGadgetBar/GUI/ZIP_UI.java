package de.szut.SmartGadgetBar.GUI;

import java.awt.dnd.DropTarget;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import de.szut.SmartGadgetBar.Model.FileDropper;
import de.szut.SmartGadgetBar.Widgets.ZIP.ZIP;

/**
 * Das Panel fuer das Widget ZIP
 *
 */
public class ZIP_UI extends AbstractWidgetPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5921546125649895477L;
	private ZIP widget;
	private JFileChooser fileChoosertoZip;

	public ZIP_UI(ZIP parent) {
		super(parent);
		widget = parent;
		initializePanel();
		// TODO Auto-generated constructor stub
	}

	@Override
	void initializePanel() {
		setSize(280, 80);
		setLayout(null);
		setImage("graphics/zip.png");

		JButton btnZip = new JButton("Zip");
		btnZip.setBounds(10, 45, 89, 23);
		btnZip.addActionListener(e -> {
			try {
				boolean unable = false;
				File[] choosen = fileChoosertoZip.getSelectedFiles();
				for (int i = 0; i < choosen.length; i++) {
					if (choosen[i].toString().contains(".lnk")) {
						unable = true;
						JOptionPane.showMessageDialog(null,
								"Unable to ZIP .lnk");
						;
					}
				}
				if (!unable) {
					widget.comprimate(choosen);
				}
			} catch (NullPointerException k) {
				JOptionPane.showMessageDialog(null, "No File selected");
			}

		});
		add(btnZip);

		JButton btnInFile = new JButton("Select File");
		btnInFile.setBounds(10, 11, 89, 23);
		btnInFile.addActionListener(e -> {
			fileChoosertoZip = new JFileChooser();
			fileChoosertoZip
					.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			fileChoosertoZip.setMultiSelectionEnabled(true);
			fileChoosertoZip.setDialogTitle("Please choose a file");
			fileChoosertoZip.showOpenDialog(null);

		});
		add(btnInFile);

		JButton btnDecrypt = new JButton("UnZip");
		btnDecrypt.setBounds(181, 45, 89, 23);
		btnDecrypt.addActionListener(e -> {
			try {
				widget.decomprimate(fileChoosertoZip.getSelectedFile()
						.getAbsolutePath());
			} catch (NullPointerException k) {
				JOptionPane.showMessageDialog(null, "No File selected");
			}
		});
		add(btnDecrypt);

		new DropTarget(this, new FileDropper(this));

	}

	@Override
	public void pushFiles(File[] files) {
		boolean unZip = true;
		for (File file : files) {
			if (!file.getAbsolutePath().endsWith(".zip")) {
				unZip = false;
			}
		}
		if (unZip) {
			for (File file : files) {
				widget.decomprimate(file.getAbsolutePath());
			}
		} else {
			widget.comprimate(files);
		}
	}

	@Override
	public void optionClicked() {
		// TODO Auto-generated method stub
		OptionPanelWidget opw = new OptionPanelWidget(widget);
		opw.addProperty(ZIP.COMLOCATION, "Comprimate to:");
		opw.finish();
	}
}
