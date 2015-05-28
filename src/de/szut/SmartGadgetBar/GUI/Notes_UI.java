package de.szut.SmartGadgetBar.GUI;

import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.szut.SmartGadgetBar.Model.WidgetInterface;
import de.szut.SmartGadgetBar.Widgets.Notes.Notes;

/**
 * Die grafische Oberfl�che des Notizfeldes
 * 
 * @author Simeon Kublenz
 *
 */
public class Notes_UI extends AbstractWidgetPanel {

	private static final long serialVersionUID = -212490706203365585L;
	private Notes parent;
	private JFileChooser fileChooser;
	private JTextArea textfield;

	/**
	 * Erzeugt ein neues Objekt der Notes_UI mit einem zugeh�rigen Widget
	 * 
	 * @param parent
	 *            Das Widget welches zu diesem Panel geh�ren soll
	 */
	public Notes_UI(WidgetInterface parent) {
		super(parent);
		this.parent = (Notes) parent;
		setSize(280, 80);
		initializePanel();
	}

	@Override
	void initializePanel() {
		setLayout(new GridLayout(1, 1, 10, 10));
		textfield = new JTextArea();
		add(textfield);

		JPopupMenu popupMenu = getComponentPopupMenu();
		JMenuItem saveDatabase = new JMenuItem("In Datenbank Speichern");
		saveDatabase.addActionListener(e -> {
			String name = JOptionPane.showInputDialog(null,
					"Bei keiner Eingabe wird kein Name festgelegt",
					"Geben Sie den Namen der Notiz an",
					JOptionPane.PLAIN_MESSAGE);
			if (name == null) {
				parent.saveNote(textfield.getText());
			} else {
				parent.saveNote(textfield.getText(), name);
			}
		});

		JMenuItem saveTxt = new JMenuItem("Speichern als .txt");
		saveTxt.addActionListener(e -> {
			showFileChooser();
			if (fileChooser.getSelectedFile() != null) {
				String path = fileChooser.getSelectedFile().getAbsolutePath();
				if (!path.endsWith(".txt")) {
					path += ".txt";
				}
				parent.saveTxt(new File(path), textfield.getText());
			}
		});

		JMenuItem loadDatabase = new JMenuItem("Aus Datenbank laden");
		loadDatabase.addActionListener(e -> {
			String[] options = parent.getDatesAndNames();
			String s = (String) JOptionPane.showInputDialog(null,
					"W�hlen Sie eine Notiz", "W�hle eine Notiz",
					JOptionPane.PLAIN_MESSAGE, null, options, null);
			textfield.setText(parent.getNoteFromOption(s));
		});

		JMenuItem loadTxt = new JMenuItem("Aus .txt laden");
		loadTxt.addActionListener(e -> {
			showFileChooser();
			if (fileChooser.getSelectedFile() != null
					&& fileChooser.getSelectedFile().getName().endsWith(".txt")) {
				try {
					BufferedReader reader = new BufferedReader(new FileReader(
							fileChooser.getSelectedFile()));
					boolean hasNext = true;
					String text = "";
					while (hasNext) {
						String line = reader.readLine();
						if (line != null) {
							text += line + "\n";
						} else {
							hasNext = false;
						}
					}
					textfield.setText(text);
					reader.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		popupMenu.addSeparator();
		popupMenu.add(loadTxt);
		popupMenu.add(loadDatabase);
		popupMenu.add(saveTxt);
		popupMenu.add(saveDatabase);

		textfield.setComponentPopupMenu(popupMenu);

		setVisible(true);
	}

	/**
	 * �ffnet ein Dateiauswahlfenster
	 */
	private void showFileChooser() {
		if (fileChooser == null) {
			fileChooser = new JFileChooser(parent.getProperties().getProperty(
					"textfilepath"));
			fileChooser.removeChoosableFileFilter(fileChooser
					.getChoosableFileFilters()[0]);
			fileChooser.setFileFilter(new FileNameExtensionFilter("Text Datei",
					"txt"));
		}
		fileChooser.showOpenDialog(null);
	}

	/**
	 * Nicht in Gebrauch. Tut nichts
	 */
	@Override
	public void pushFiles(File[] files) {
	}

	@Override
	public void optionClicked() {
		OptionPanelWidget opw = new OptionPanelWidget(widget);
		opw.addProperty(Notes.DBPath, "Databasepath:");
		opw.finish();
	}

	/**
	 * Gibt dem Notizfeld einen neuen Text
	 * 
	 * @param text
	 *            Der neue Text
	 */
	public void setText(String text) {
		textfield.setText(text);
	}

	/**
	 * Gibt den aktuellen Text des Notizfeldes zur�ck
	 * 
	 * @return Der Text des Notizfeldes
	 */
	public String getText() {
		return textfield.getText();
	}
}