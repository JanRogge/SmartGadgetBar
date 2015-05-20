package de.szut.SmartGadgetBar.GUI;

import java.awt.Rectangle;
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

public class Notes_UI extends AbstractWidgetPanel {
	
	private Notes parent;
	private JFileChooser fileChooser;
	
	public Notes_UI(WidgetInterface parent) {
		super(parent);
		this.parent = (Notes) parent;
		setBounds(new Rectangle(0, 0, 280, 80));
		initializePanel();
	}
	
	@Override
	void initializePanel() {
		setLayout(null);
		JTextArea textfield = new JTextArea();
		textfield.setBounds(0, 7, 280, 66);
		add(textfield);
		
		JPopupMenu popupMenu = getComponentPopupMenu();
		JMenuItem saveDatabase = new JMenuItem("In Datenbank Speichern");
		saveDatabase.addActionListener(e -> {
			String name = JOptionPane.showInputDialog(null,"Bei keiner Eingabe wird kein Name festgelegt",
                    "Geben Sie den Namen der Notiz an",
                    JOptionPane.PLAIN_MESSAGE);
			if (name == null) {
				parent.saveNote(textfield.getText());
			}
			else {
				parent.saveNote(textfield.getText(), name);
			}
		});
		
		JMenuItem saveTxt= new JMenuItem("Speichern als .txt");
		saveTxt.addActionListener(e -> {
			showFileChooser();
			if (fileChooser.getSelectedFile() != null && fileChooser.getSelectedFile().getName().endsWith(".txt")) {
				parent.saveTxt(fileChooser.getSelectedFile(), textfield.getText());
			}
		});
		
		JMenuItem loadDatabase= new JMenuItem("Aus Datenbank laden");
		loadDatabase.addActionListener(e -> {
			String[] options = parent.getDatesAndNames();
			String s = (String) JOptionPane.showInputDialog(null,"Wählen Sie eine Notiz",
					"Wähle eine Notiz", JOptionPane.PLAIN_MESSAGE, null, options, null);
			textfield.setText(parent.getNoteFromOption(s));
		});
		
		JMenuItem loadTxt = new JMenuItem("Aus .txt laden");
		loadTxt.addActionListener(e -> {
			showFileChooser();
			if (fileChooser.getSelectedFile() != null && fileChooser.getSelectedFile().getName().endsWith(".txt")) {
				try {
					BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
					boolean hasNext = true;
					String text = "";
					while (hasNext) {
						String line = reader.readLine();
						if (line != null) {
							text += line + "\n";
						}
						else {
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
		
		textfield.setComponentPopupMenu(popupMenu);;
		
		setVisible(true);
	}
	
	private void showFileChooser() {
		if (fileChooser == null) {
			fileChooser = new JFileChooser(parent.getProperties().getProperty("textfilepath"));
			fileChooser.removeChoosableFileFilter(fileChooser.getChoosableFileFilters()[0]);
			fileChooser.setFileFilter(new FileNameExtensionFilter("Text Datei","txt"));
		}
		fileChooser.showOpenDialog(null);
	}
	
	@Override
	public void pushFiles(File[] files) {
	}

	@Override
	public void optionClicked() {
		OptionPanelWidget opw= new OptionPanelWidget(widget);
		opw.addProperty(Notes.DBPath, "Databasepath:");
		opw.addProperty(Notes.TXTPATH, "Dafault .txt file path");
		opw.finish();
	}
}
